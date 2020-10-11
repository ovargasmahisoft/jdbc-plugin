package com.github.ovargasmahisoft.jdbcplugin.utils

import com.intellij.database.model.DasColumn
import com.intellij.database.psi.DbTable
import com.intellij.database.util.DasUtil

fun DasColumn.daoJavaType(): String {
    return when (this.dataType.typeName) {
        "bigint" -> if (this.isNotNull) "long" else "Long"
        "integer", "int" -> if (this.isNotNull) "int" else "Integer"
        "float" -> if (this.isNotNull) "float" else "Float"
        "double" -> if (this.isNotNull) "double" else "Double"
        "bit" -> if (this.isNotNull) "boolean" else "Boolean"
        "varchar", "char", "text" -> "String"
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
        "varchar", "char", "text" -> "String"
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
        "varchar", "char", "text" -> "rs.getString("
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
        "varchar", "char", "text" -> "rs.getString("
        "timestamp" -> "JdbcUtil.instantOrNull(rs, "
        else -> "rs.getObject("
    }
}

fun DasColumn.javaDefinition(): String {
    return "    ${this.daoJavaType()} ${this.camelCaseName()};\n"
}

fun DasColumn.javaColumnMapper(): String {
    return when (this.dataType.typeName) {
        "bigint" -> if (this.isNotNull) "rs.getLong(\"${this.name}\")" else "(Long) Optional.ofNullable(rs.getObject(\"${this.name}\")).orElse(null)"
        "integer", "int" -> if (this.isNotNull) "rs.getInt(\"${this.name}\")" else "(Integer) Optional.ofNullable(rs.getObject(\"${this.name}\")).orElse(null)"
        "float" -> if (this.isNotNull) "rs.getFloat(\"${this.name}\")" else "(Float) Optional.ofNullable(rs.getObject(\"${this.name}\")).orElse(null)"
        "double" -> if (this.isNotNull) "rs.getDouble(\"${this.name}\")" else "(Double) Optional.ofNullable(rs.getObject(\"${this.name}\")).orElse(null)"
        "bit" -> if (this.isNotNull) "rs.getBoolean(\"${this.name}\")" else "(Boolean) Optional.ofNullable(rs.getObject(\"${this.name}\")).orElse(null)"
        "varchar", "char", "text" -> "rs.getString(\"${this.name}\")"
        "timestamp" -> "rs.getTimestamp(\"${this.name}\").toInstant()" //TODO: Support nullable timestamp
        else -> "rs.getObject(\"${this.name}\")"
    }
}

fun DbTable.javaRowMapper(): String {
    val daoName = this.name.snakeToUpperCamelCase()
    val sb = StringBuilder("" +
        "    private $daoName mapRow(ResultSet rs, int rowNum) throws SQLException {\n" +
        "        return ${daoName}.builder()\n")

    for (column in DasUtil.getColumns(this)) {
        sb.append("${" ".repeat(16)}.${column.name.snakeToLowerCamelCase()}(${column.javaColumnMapper()})\n")
    }
    sb.append("${" ".repeat(16)}.build();\n" +
        "    }")
    return sb.toString()
}