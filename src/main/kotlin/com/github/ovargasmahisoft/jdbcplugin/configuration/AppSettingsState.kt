package com.github.ovargasmahisoft.jdbcplugin.configuration

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil

@State(
    name = "com.github.ovargasmahisoft.jdbcplugin.AppSettingsState",
    storages = [Storage("JdbcSettingsPlugin.xml")]
)
class AppSettingsState : PersistentStateComponent<AppSettingsState> {

    companion object {

        fun getInstance(): AppSettingsState {
            return ServiceManager.getService(AppSettingsState::class.java)
        }
    }


    /*
        Naming convention settings
    */
    var doPrefix: String = ""
    var doSuffix: String = "Do"
    var repoPrefix: String = ""
    var repoSuffix: String = "Repository"
    var implPrefix: String = "Jdbc"
    var implSuffix: String = "Repository"

    /*
        Template Settings
    */

    var useDefaultDoTemplate: Boolean = true
    var useDefaultRepoTemplate: Boolean = true
    var useDefaultImplTemplate: Boolean = true

    var doTemplate: String = ""
    var repoTemplate: String = ""
    var implTemplate: String = ""

    /*
        Packages
    */

    var doPackage: String = "domain"
    var repoPackage: String = "repository"
    var implPackage: String = "repository.jdbc"

    override fun getState(): AppSettingsState? {
        return this
    }

    override fun loadState(state: AppSettingsState) {
        XmlSerializerUtil.copyBean(state, this)
    }
}