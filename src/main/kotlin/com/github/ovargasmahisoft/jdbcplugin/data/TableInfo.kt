package com.github.ovargasmahisoft.jdbcplugin.data

import com.github.ovargasmahisoft.jdbcplugin.utils.snakeToUpperCamelCase
import com.intellij.database.psi.DbTable
import com.intellij.database.util.DasUtil

class TableInfo(table: DbTable, val basePackage: String) {

    val tableName: String
    val constantName: String
    val className: String
    val daoClassName: String
    val repositoryClassName: String
    val repositoryInterfaceName: String
    val columns: List<ColumnInfo>
    val primaryKey: ColumnInfo

    init {
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