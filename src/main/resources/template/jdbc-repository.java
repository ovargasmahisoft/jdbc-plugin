package ${table.basePackage}.repository.jdbc;

import com.akorda.commons.jdbc.JdbcBaseRepository;
import com.akorda.commons.jdbc.JdbcUtil;
import ${table.basePackage}.domain.${table.daoClassName};
import ${table.basePackage}.repository.${table.repositoryInterfaceName};
import java.util.Collections;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

@Component
public class ${table.repositoryClassName} extends JdbcBaseRepository implements ${table.repositoryInterfaceName} {
    <% table.columns.each { %>
    public static final String ${it.constantName} = "${it.columnName}";<% } %>

    public ${table.repositoryClassName}(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public ${table.daoClassName} get(${table.primaryKey.dataType} id) {
        return getMany(Collections.singletonList(id))
            .stream().findFirst().orElse(null);
    }

    @Override
    public List<${table.daoClassName}> getMany(List<${table.primaryKey.nullableDataType}> ids) {
        Map<String, Object> parameters = Map.of("ids", ids);

        return namedParameterJdbcTemplate().query(
                "SELECT * FROM ${table.tableName} WHERE ${table.primaryKey.columnName} in (:ids)",
                parameters,
                this::mapRow);
    }

    @Override
    public ${table.daoClassName} create(${table.daoClassName} dao) {
        <%
            def columns = table.columns.findAll { !it.primaryKey }.collect { it.columnName}.join(", ")
            def values = table.columns.findAll { !it.primaryKey }.collect { ":" + it.columnName}.join(", ")
        %>
        String sql = "INSERT INTO ${table.tableName} (${columns})" +
            "VALUES (${values})";

        MapSqlParameterSource parameters = new MapSqlParameterSource(
            buildParameters(dao)
        );
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate().update(sql, parameters, keyHolder);
        return get(keyHolder.getKey().longValue());
    }

    @Override
    public ${table.daoClassName} update(${table.daoClassName} dao) {
        return updateMany(Collections.singletonList(dao))
                .stream().findFirst().orElse(null);
    }

    @Override
    public List<${table.daoClassName}> updateMany(List<${table.daoClassName}> daoList) {
        String sql = "UPDATE ${table.tableName} SET " +<%
        table.columns.findAll{ !it.primaryKey }.each {
        %>
                "${it.columnName} = :${it.columnName}<%
                if(it != table.columns.last()) { %>,<% } %> " +<% } %>
                "WHERE ${table.primaryKey.columnName} = :${table.primaryKey.columnName}";

        namedParameterJdbcTemplate().batchUpdate(sql,
                (Map<String, Object>[]) daoList.stream().map(this::buildParameters).toArray());

        return getMany(daoList.stream().map(${table.daoClassName}::getId).collect(Collectors.toList()));
    }

    @Override
    public void delete(${table.primaryKey.dataType} id) {
        deleteMany(Collections.singletonList(id));
    }

    @Override
    public void deleteMany(List<${table.primaryKey.nullableDataType}> ids) {
        Map<String, Object> parameters = Map.of("ids", ids);

        namedParameterJdbcTemplate().update(
            "DELETE FROM ${table.tableName} WHERE ${table.primaryKey.columnName} IN (:ids)",
            parameters);
    }


    private Map<String, Object> buildParameters(${table.daoClassName} dao) {
        return Map.ofEntries(<% table.columns.each {
                def daoToJdbcMap = "dao.get" + it.fieldName.capitalize() + "()"
                if(it.dataType == "boolean") {
                    daoToJdbcMap = "dao.is" + it.fieldName.capitalize() + "()"
                }
                if(it.dataType == "Instant") {
                    daoToJdbcMap = "JdbcUtil.timestampOrNull(dao.get"  + it.fieldName.capitalize() + "())"
                }%>
                Map.entry(${it.constantName}, ${daoToJdbcMap})<%
            if(it != table.columns.last()){%>,<% }%><% } %>
        );
    }

    private ${table.daoClassName} mapRow(ResultSet rs, int rowNum) throws SQLException {
        return ${table.daoClassName}.builder()<% table.columns.each { %>
                .${it.fieldName}(${it.resultSetMapper})<% } %>
            .build();
    }

}