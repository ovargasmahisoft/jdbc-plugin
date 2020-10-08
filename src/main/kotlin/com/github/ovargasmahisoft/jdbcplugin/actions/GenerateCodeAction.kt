package com.github.ovargasmahisoft.jdbcplugin.actions

import com.github.ovargasmahisoft.jdbcplugin.ui.OptionsDialogWrapper
import com.github.ovargasmahisoft.jdbcplugin.utils.javaDefinition
import com.github.ovargasmahisoft.jdbcplugin.utils.snakeToUpperCamelCase
import com.intellij.database.psi.DbTable
import com.intellij.database.util.DasUtil
import com.intellij.ide.projectView.ProjectView
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.LangDataKeys
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.VirtualFileManager
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.PsiManager
import com.intellij.psi.PsiPackage
import java.io.FileWriter


class GenerateCodeAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val elements = e.getData(LangDataKeys.PSI_ELEMENT_ARRAY)
        if (elements.isNullOrEmpty()) {
            return
        }

        val dialog = OptionsDialogWrapper(e.project!!)
        dialog.show()

        if (!dialog.isOK) return

        elements
            .filterIsInstance<DbTable>()
            .forEach { tbl ->
                buildDaoFile(dialog.data().daoPackage, tbl)
            }

        ProjectView.getInstance(e.project).refresh()
    }

    private fun buildDaoFile(psiPackage: PsiPackage, element: DbTable) {
        val sb = StringBuilder()
        for (column in DasUtil.getColumns(element)) {
            sb.append(column.javaDefinition())
        }

        val className = element.name.snakeToUpperCamelCase()

        val writer = FileWriter("${psiPackage.directories[0].virtualFile.canonicalPath}/${className}.java")
        writer.write("package ${psiPackage.qualifiedName};\n\n" +
            "import lombok.Builder;\n" +
            "import lombok.Value;\n\n" +
            "@Value\n" +
            "@Builder\n" +
            "public class $className {\n" +
            "$sb" +
            "}")
        writer.flush()
        writer.close()

        ProjectView.getInstance(psiPackage.project).refresh()
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