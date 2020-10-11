package ${table.basePackage}.repository;

import java.util.List;
import ${table.basePackage}.domain.${table.daoClassName};

public interface ${table.repositoryInterfaceName} {

    ${table.daoClassName} get(${table.primaryKey.dataType} id);

    List<${table.daoClassName}> getMany(List<${table.primaryKey.nullableDataType}> ids);

    ${table.daoClassName} create(${table.daoClassName} dao);

    List<${table.daoClassName}> createMany(List<${table.daoClassName}> daoList);

    ${table.daoClassName} update(${table.daoClassName} dao);

    List<${table.daoClassName}> updateMany(List<${table.daoClassName}> daoList);

}