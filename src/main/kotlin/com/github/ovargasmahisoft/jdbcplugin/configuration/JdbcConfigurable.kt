package com.github.ovargasmahisoft.jdbcplugin.configuration

import com.github.ovargasmahisoft.jdbcplugin.ui.JdbcConfigurationComponent
import com.intellij.openapi.options.Configurable
import javax.swing.JComponent

open class JdbcConfigurable : Configurable {

    private lateinit var configurationComponent: JdbcConfigurationComponent

    override fun getDisplayName(): String {
        return "JDBC-Plugin Configuration"
    }

    override fun getPreferredFocusedComponent(): JComponent {
        return configurationComponent.preferredFocusedComponent
    }

    override fun createComponent(): JComponent? {
        configurationComponent = JdbcConfigurationComponent()
        return configurationComponent.panel
    }

    override fun isModified(): Boolean {
        val settings = AppSettingsState.getInstance()
        return configurationComponent.doPrefix != settings.doPrefix ||
            configurationComponent.doSuffix != settings.doSuffix ||
            configurationComponent.repoPrefix != settings.repoPrefix ||
            configurationComponent.repoSuffix != settings.repoSuffix ||
            configurationComponent.implPrefix != settings.implPrefix ||
            configurationComponent.implSuffix != settings.implSuffix ||

            configurationComponent.useDefaultDoTemplate != settings.useDefaultDoTemplate ||
            configurationComponent.useDefaultRepoTemplate != settings.useDefaultRepoTemplate ||
            configurationComponent.useDefaultImplTemplate != settings.useDefaultImplTemplate ||
            configurationComponent.doTemplate != settings.doTemplate ||
            configurationComponent.repoTemplate != settings.repoTemplate ||
            configurationComponent.implTemplate != settings.implTemplate ||

            configurationComponent.doPackage != settings.doPackage ||
            configurationComponent.repoPackage != settings.repoPackage ||
            configurationComponent.implPackage != settings.implPackage
    }

    override fun apply() {
        val settings = AppSettingsState.getInstance()

        settings.doPrefix = configurationComponent.doPrefix
        settings.doSuffix = configurationComponent.doSuffix
        settings.repoPrefix = configurationComponent.repoPrefix
        settings.repoSuffix = configurationComponent.repoSuffix
        settings.implPrefix = configurationComponent.implPrefix
        settings.implSuffix = configurationComponent.implSuffix

        settings.useDefaultDoTemplate = configurationComponent.useDefaultDoTemplate
        settings.useDefaultRepoTemplate = configurationComponent.useDefaultRepoTemplate
        settings.useDefaultImplTemplate = configurationComponent.useDefaultImplTemplate
        settings.doTemplate = configurationComponent.doTemplate
        settings.repoTemplate = configurationComponent.repoTemplate
        settings.implTemplate = configurationComponent.implTemplate

        settings.doPackage = configurationComponent.doPackage
        settings.repoPackage = configurationComponent.repoPackage
        settings.implTemplate = configurationComponent.implPackage
    }

    override fun reset() {
        val settings = AppSettingsState.getInstance()

        configurationComponent.doPrefix = settings.doPrefix
        configurationComponent.doSuffix = settings.doSuffix
        configurationComponent.repoPrefix = settings.repoPrefix
        configurationComponent.repoSuffix = settings.repoSuffix
        configurationComponent.implPrefix = settings.implPrefix
        configurationComponent.implSuffix = settings.implSuffix

        configurationComponent.useDefaultDoTemplate = settings.useDefaultDoTemplate
        configurationComponent.useDefaultRepoTemplate = settings.useDefaultRepoTemplate
        configurationComponent.useDefaultImplTemplate = settings.useDefaultImplTemplate
        configurationComponent.doTemplate = settings.doTemplate
        configurationComponent.repoTemplate = settings.repoTemplate
        configurationComponent.implTemplate = settings.implTemplate

        configurationComponent.doPackage = settings.doPackage
        configurationComponent.repoPackage = settings.repoPackage
        configurationComponent.implPackage = settings.implPackage
    }
}
