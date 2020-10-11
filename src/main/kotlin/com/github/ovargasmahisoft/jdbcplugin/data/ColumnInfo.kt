package com.github.ovargasmahisoft.jdbcplugin.data

import com.github.ovargasmahisoft.jdbcplugin.utils.daoJavaType
import com.github.ovargasmahisoft.jdbcplugin.utils.nonNullResultSetMap
import com.github.ovargasmahisoft.jdbcplugin.utils.nullableJavaType
import com.github.ovargasmahisoft.jdbcplugin.utils.nullableResultSetMap
import com.github.ovargasmahisoft.jdbcplugin.utils.snakeToLowerCamelCase
import com.intellij.database.model.DasColumn
import com.intellij.database.util.DasUtil

class ColumnInfo {

    private val column: DasColumn

    constructor(column: DasColumn) {
        this.column = column
        primaryKey = DasUtil.isPrimary(column)
        constantName = "${column.name.toUpperCase()}_COLUMN_NAME"
        dataType = column.daoJavaType()
        fieldName = if (primaryKey) "id" else column.name.snakeToLowerCamelCase()
        columnName = column.name
        nullableDataType = column.nullableJavaType()
        resultSetMapper = (if (column.isNotNull) column.nonNullResultSetMap() else column.nullableResultSetMap()) + constantName + ")"
        required = column.isNotNull
    }

    val constantName: String
    val dataType: String
    val fieldName: String
    val columnName: String
    val nullableDataType: String
    val primaryKey: Boolean
    val resultSetMapper: String
    val required: Boolean
}