package com.github.ovargasmahisoft.jdbcplugin.actions

import com.github.ovargasmahisoft.jdbcplugin.ui.OptionsDialogWrapper
import com.intellij.database.psi.DbTable
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.LangDataKeys


class GenerateCodeAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val elements = e.getData(LangDataKeys.PSI_ELEMENT_ARRAY)
        if (elements.isNullOrEmpty()) {
            return
        }

        val sb = StringBuilder()
        for (element in elements) {
            if (element !is DbTable) {
                continue
            }

            sb.append("${element.name}\r\n")
        }

        // https://www.javatips.net/api/com.intellij.ide.util.treefilechooserfactory

//        val fileChooser = TreeFileChooserFactory.getInstance(e.project)
//
//            .createFileChooser(
//                "Select File", null, StdFileTypes.JAVA,
//                { true }, false)
//
//        fileChooser.showDialog()

//        val dialog = PackageChooserDialog("Select Package", e.project)
//        dialog.show()

        OptionsDialogWrapper().show()
    }


    override fun update(e: AnActionEvent) {
        val elements = e.getData(LangDataKeys.PSI_ELEMENT_ARRAY)
        if (elements.isNullOrEmpty()) {
            e.presentation.isEnabled = false
        }
        e.presentation.isEnabled = !elements.isNullOrEmpty() &&
            elements.any { el -> el is DbTable }
        super.update(e)
    }
}