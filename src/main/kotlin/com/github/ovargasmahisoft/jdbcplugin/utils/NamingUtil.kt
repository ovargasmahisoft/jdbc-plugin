package com.github.ovargasmahisoft.jdbcplugin.utils

import com.intellij.database.model.DasColumn
import com.intellij.database.psi.DbColumn


private val snakeRegex = "_[a-zA-Z]".toRegex()

fun DasColumn.camelCaseName(): String = this.name.snakeToLowerCamelCase().decapitalize()

fun String.snakeToLowerCamelCase(): String = this.snakeToCamel().decapitalize()

fun String.snakeToUpperCamelCase(): String = this.snakeToCamel().capitalize()

private fun String.snakeToCamel(): String  {
    return snakeRegex.replace(this) {
        it.value.replace("_", "")
            .toUpperCase()
    }
}