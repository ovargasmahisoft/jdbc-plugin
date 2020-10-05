package com.github.ovargasmahisoft.jdbcplugin.utils

import com.intellij.database.model.DasColumn
import com.intellij.database.psi.DbColumn

fun DasColumn.javaType(): String {
    return when (this.dataType.typeName) {
        "bigint" -> if (this.isNotNull) "long" else "Long"
        "integer", "int" -> if (this.isNotNull) "int" else "Integer"
        "bit" -> if (this.isNotNull) "boolean" else "Boolean"
        "varchar", "char", "text" -> "String"
        "timestamp" -> "Instant"
        else -> "Object"
    }
}

fun DasColumn.javaDefinition(): String {
    return "    ${this.javaType()} ${this.camelCaseName()};\n"
}