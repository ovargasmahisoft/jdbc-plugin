package com.github.ovargasmahisoft.jdbcplugin.actions

import com.github.ovargasmahisoft.jdbcplugin.ui.OptionsDialogWrapper
import com.github.ovargasmahisoft.jdbcplugin.utils.snakeToUpperCamelCase
import com.intellij.database.psi.DbTable
import com.intellij.lang.java.JavaLanguage
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.LangDataKeys
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.PsiFileFactory


class GenerateCodeAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val elements = e.getData(LangDataKeys.PSI_ELEMENT_ARRAY)
        if (elements.isNullOrEmpty()) {
            return
        }

        val sb = StringBuilder()

        val dialog = OptionsDialogWrapper(e.project!!)
        dialog.show()

        if (dialog.isOK) {
            for (element in elements) {
                if (element !is DbTable) {
                    continue
                }

                sb.append("${element.name}\r\n")

                if (dialog.data().generateDAO && dialog.data().daoPackage != null) {

                    val className = element.name.snakeToUpperCamelCase()
                    val psiFile = PsiFileFactory.getInstance(e.project)
                        .createFileFromText("${className}.java", JavaLanguage.INSTANCE,
                            "package ${dialog.data().daoPackage.qualifiedName};\n\n" +
                                "import lombok.Builder;\n" +
                                "import lombok.Value;\n\n" +
                                "public class $className {\n" +
                                "}"
                        )

                    dialog.data().daoPackage.directories[0].add(psiFile)

                }
            }

        }
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