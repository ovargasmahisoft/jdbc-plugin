package com.github.ovargasmahisoft.jdbcplugin.ui

import com.intellij.openapi.ui.DialogWrapper
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel


class OptionsDialogWrapper : DialogWrapper {

    constructor() : super(true) {
        init()
        title = "Generate Options"
    }

    override fun createCenterPanel(): JComponent? {
        return OptionsForm().mainPanel
    }
}