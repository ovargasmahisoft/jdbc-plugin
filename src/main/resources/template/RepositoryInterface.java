package {{basePackage}}.repository;

import java.util.List;
import {{basePackage}}.domain.{{daoClassName}};

public interface {{repositoryInterfaceName}} {

    {{daoClassName}} get({{#primaryKey}}{{dataType}}{{/primaryKey}} id);

    List<{{daoClassName}}> getMany(List<{{#primaryKey}}{{nullableDataType}}{{/primaryKey}}> ids);

    {{daoClassName}} create({{daoClassName}} dao);

    List<{{daoClassName}}> createMany(List<{{daoClassName}}> daoList);

    {{daoClassName}} update({{daoClassName}} dao);

    List<{{daoClassName}}> updateMany(List<{{daoClassName}}> daoList);

}