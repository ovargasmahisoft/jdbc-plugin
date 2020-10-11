package com.github.ovargasmahisoft.jdbcplugin.utils

import com.intellij.database.model.DasColumn

fun DasColumn.daoJavaType(): String {
    return when (this.dataType.typeName) {
        "bigint" -> if (this.isNotNull) "long" else "Long"
        "integer", "int" -> if (this.isNotNull) "int" else "Integer"
        "float" -> if (this.isNotNull) "float" else "Float"
        "double" -> if (this.isNotNull) "double" else "Double"
        "bit" -> if (this.isNotNull) "boolean" else "Boolean"
        "varchar", "char", "text", "json" -> "String"
        "timestamp" -> "Instant"
        else -> "Object"
    }
}

fun DasColumn.nullableJavaType(): String {
    return when (this.dataType.typeName) {
        "bigint" -> "Long"
        "integer", "int" -> "Integer"
        "float" -> "Float"
        "double" -> "Double"
        "bit" -> "Boolean"
        "varchar", "char", "text", "json" -> "String"
        "timestamp" -> "Instant"
        else -> "Object"
    }
}

fun DasColumn.nonNullResultSetMap(): String {
    return when (this.dataType.typeName) {
        "bigint" -> "rs.getLong("
        "integer", "int" -> "rs.getInt("
        "float" -> "rs.getFloat("
        "double" -> "rs.getDouble("
        "bit" -> "rs.getBoolean("
        "varchar", "char", "text", "json" -> "rs.getString("
        "timestamp" -> "JdbcUtil.instantOrNull(rs, "
        else -> "rs.getObject("
    }
}

fun DasColumn.nullableResultSetMap(): String {
    return when (this.dataType.typeName) {
        "bigint" -> "JdbcUtil.longOrNull(rs, "
        "integer", "int" -> "JdbcUtil.intOrNull(rs, "
        "float" -> "JdbcUtil.floatOrNull(rs, "
        "double" -> "JdbcUtil.doubleOrNull(rs, "
        "bit" -> "JdbcUtil.booleanOrNull(rs, "
        "varchar", "char", "text", "json" -> "rs.getString("
        "timestamp" -> "JdbcUtil.instantOrNull(rs, "
        else -> "rs.getObject("
    }
}
