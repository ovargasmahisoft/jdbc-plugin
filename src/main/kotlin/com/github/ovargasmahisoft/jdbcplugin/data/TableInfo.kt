package com.github.ovargasmahisoft.jdbcplugin.data

import com.github.ovargasmahisoft.jdbcplugin.utils.snakeToUpperCamelCase
import com.intellij.database.psi.DbDataSource
import com.intellij.database.psi.DbTable
import com.intellij.database.util.DasUtil
import java.util.Locale

class TableInfo(table: DbTable, val basePackage: String) {

    val dataSourceType: String
    val tableName: String
    val constantName: String
    val className: String
    val daoClassName: String
    val repositoryClassName: String
    val repositoryInterfaceName: String
    val columns: List<ColumnInfo>
    val primaryKey: ColumnInfo

    init {
        this.dataSourceType = (table.dataSource.dasObject as DbDataSource).dataSource.dbms.name.toLowerCase(Locale.ENGLISH)
        this.tableName = table.name
        this.constantName = "${table.name}_COLUMN_NAME"
        this.className = table.name.snakeToUpperCamelCase()
        this.daoClassName = "${className}Dao"
        this.repositoryInterfaceName = "${className}Repository"
        this.repositoryClassName = "Jdbc${className}Repository"
        this.columns = DasUtil.getColumns(table).map { ColumnInfo(it) }.toList()
        this.primaryKey = columns.first { it.primaryKey }
    }

}