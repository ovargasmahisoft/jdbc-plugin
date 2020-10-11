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

public class ${table.repositoryClassName} extends JdbcBaseRepository implements ${table.repositoryInterfaceName} {
    <% table.columns.each { %>
    public static final String ${it.constantName} = "${it.columnName}";<% } %>

    protected ${table.repositoryClassName}(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public ${table.daoClassName} get(${table.primaryKey.dataType} id) {
        return getMany(Collections.singletonList(id))
            .stream().findFirst().orElse(null);
    }

    @Override
    public List<${table.daoClassName}> getMany(List<${table.primaryKey.nullableDataType}> ids) {
        Map<String, Object> parameters = Map.of(
            "ids", ids
        );
        return namedParameterJdbcTemplate().query(
                "SELECT * FROM ${table.tableName} WHERE ${table.primaryKey.columnName} in (:ids)",
                parameters,
                this::mapRow);
    }

    @Override
    public ${table.daoClassName} create(${table.daoClassName} dao) {
        return null;
    }

    @Override
    public List<${table.daoClassName}> createMany(List<${table.daoClassName}> daoList) {
        return null;
    }

    @Override
    public ${table.daoClassName} update(${table.daoClassName} dao) {
        return null;
    }

    @Override
    public List<${table.daoClassName}> updateMany(List<${table.daoClassName}> daoList) {
        return null;
    }

    private Map<String, Object> buildProperties(${table.daoClassName} dao) {
        return Map.ofEntries(<% table.columns.each { %>
            Map.entry(${it.constantName}, dao.get${it.fieldName.capitalize()}())<%
            if(it != table.columns.last()){%>,<% }%><% } %>
        );
    }

    private ${table.daoClassName} mapRow(ResultSet rs, int rowNum) throws SQLException {
        return ${table.daoClassName}.builder()<% table.columns.each { %>
            .${it.fieldName}(${it.resultSetMapper})<% } %>
            .build();
    }

}