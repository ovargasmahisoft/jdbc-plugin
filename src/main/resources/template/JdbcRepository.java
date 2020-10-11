package {{basePackage}}.repository.jdbc;

import com.akorda.commons.jdbc.JdbcBaseRepository;
import com.akorda.commons.jdbc.JdbcUtil;
import {{basePackage}}.domain.{{daoClassName}};
import {{basePackage}}.repository.{{repositoryInterfaceName}};
import java.util.Collections;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Map;

public class {{repositoryClassName}} extends JdbcBaseRepository implements {{repositoryInterfaceName}} {

    {{#columns}}
    public static final String {{constantName}} = "{{columnName}}";
    {{/columns}}

    protected {{repositoryClassName}}(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public {{daoClassName}} get({{#primaryKey}}{{dataType}}{{/primaryKey}} id) {
        return getMany(Collections.singletonList(id))
            .stream().findFirst().orElse(null);
    }

    @Override
    public List<{{daoClassName}}> getMany(List<{{#primaryKey}}{{nullableDataType}}{{/primaryKey}}> ids) {
        Map<String, Object> parameters = Map.of(
            "ids", ids
        );
        return namedParameterJdbcTemplate().query(
                "SELECT * FROM {{tableName}} WHERE {{#primaryKey}}{{columnName}}{{/primaryKey}} in (:ids)",
                parameters,
                this::mapRow);
    }

    @Override
    public {{daoClassName}} create({{daoClassName}} dao) {
        return null;
    }

    @Override
    public List<{{daoClassName}}> createMany(List<{{daoClassName}}> daoList) {
        return null;
    }

    @Override
    public {{daoClassName}} update({{daoClassName}} dao) {
        return null;
    }

    @Override
    public List<{{daoClassName}}> updateMany(List<{{daoClassName}}> daoList) {
        return null;
    }

    private {{daoClassName}} mapRow(ResultSet rs, int rowNum) throws SQLException {
        return {{daoClassName}}.builder()
            {{#columns}}
            .{{fieldName}}({{resultSetMapper}})
            {{/columns}}
            .build();
    }

}