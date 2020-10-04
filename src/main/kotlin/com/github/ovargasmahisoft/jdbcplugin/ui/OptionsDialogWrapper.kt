package com.github.ovargasmahisoft.jdbcplugin.ui

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import javax.swing.JComponent


class OptionsDialogWrapper : DialogWrapper {

    private val project: Project

    private lateinit var formData : IOptionsForm

    constructor(project: Project) : super(true) {
        this.project = project
        init()
        title = "Generate Options"
    }

    override fun createCenterPanel(): JComponent? {
        val optionsForm = OptionsForm(project)
        formData = optionsForm
        return optionsForm.mainPanel
    }

    fun data(): IOptionsForm = formData
}