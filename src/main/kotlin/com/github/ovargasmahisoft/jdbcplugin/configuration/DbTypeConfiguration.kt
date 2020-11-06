package com.github.ovargasmahisoft.jdbcplugin.configuration

import com.github.ovargasmahisoft.jdbcplugin.data.DbType
import com.github.ovargasmahisoft.jdbcplugin.data.DbTypeMapping
import com.intellij.openapi.components.ServiceManager
import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.constructor.Constructor


class DbTypeConfiguration {

    companion object {

        fun getInstance(): DbTypeConfiguration {
            return ServiceManager.getService(DbTypeConfiguration::class.java)
        }
    }

    private val configuration: DbTypeMapping

    init {
        val yaml = Yaml(Constructor(DbTypeMapping::class.java))

        configuration = yaml.load(
            DbTypeConfiguration::class.java.getResourceAsStream("data-type-configuration.yaml")
        )
    }

    fun getConfiguration(dataSourceType: String, language: String): Map<String, DbType>? {
        return configuration.dataSource?.get(dataSourceType)?.get(language)
    }
}