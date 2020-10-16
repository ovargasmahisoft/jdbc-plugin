package com.github.ovargasmahisoft.jdbcplugin.actions

import com.github.ovargasmahisoft.jdbcplugin.data.TableInfo
import com.github.ovargasmahisoft.jdbcplugin.ui.OptionsDialogWrapper
import com.intellij.database.psi.DbTable
import com.intellij.ide.projectView.ProjectView
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.LangDataKeys
import com.intellij.psi.PsiPackage
import groovy.text.GStringTemplateEngine
import java.io.FileWriter
import java.nio.file.Files
import java.nio.file.Paths


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
            .map { tbl -> TableInfo(tbl, dialog.data().basePackage.qualifiedName) }
            .forEach { tbl ->
                buildDaoFile(dialog.data().basePackage, tbl)
                buildInterfaceRepository(dialog.data().basePackage, tbl)
                buildClassRepository(dialog.data().basePackage, tbl)
                buildTestClassRepository(dialog.data().basePackage, tbl)
            }

        ProjectView.getInstance(e.project).refresh()
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

    private fun buildDaoFile(psiPackage: PsiPackage, table: TableInfo) {
        val templateText = GenerateCodeAction::class.java.getResource("/template/dao.tpl").readText()

        val engine = GStringTemplateEngine()
        val template = engine
            .createTemplate(templateText)
            .make(mapOf(Pair("table", table)))
        val value = template.toString()
        val path = psiPackage.directories[0].virtualFile.canonicalPath
        Files.createDirectories(Paths.get("${path}/domain"))
        val writer = FileWriter("${path}/domain/${table.daoClassName}.java")
        writer.write(value)
        writer.flush()
        writer.close()
    }

    private fun buildInterfaceRepository(psiPackage: PsiPackage, table: TableInfo) {
        val templateText = GenerateCodeAction::class.java.getResource("/template/repository-interface.tpl").readText()

        val engine = GStringTemplateEngine()
        val template = engine
            .createTemplate(templateText)
            .make(mapOf(Pair("table", table)))
        val value = template.toString()

        val path = "${psiPackage.directories[0].virtualFile.canonicalPath}/repository"

        Files.createDirectories(Paths.get(path))
        val writer = FileWriter("${path}/${table.repositoryInterfaceName}.java")
        writer.write(value)
        writer.flush()
        writer.close()
    }

    private fun buildClassRepository(psiPackage: PsiPackage, table: TableInfo) {
        val templateText = GenerateCodeAction::class.java.getResource("/template/jdbc-repository.tpl").readText()

        val engine = GStringTemplateEngine()
        val template = engine
            .createTemplate(templateText)
            .make(mapOf(Pair("table", table)))
        val value = template.toString()

        val path = "${psiPackage.directories[0].virtualFile.canonicalPath}/repository/jdbc"

        Files.createDirectories(Paths.get(path))
        val writer = FileWriter("${path}/${table.repositoryClassName}.java")
        writer.write(value)
        writer.flush()
        writer.close()

    }

    private fun buildTestClassRepository(psiPackage: PsiPackage, table: TableInfo) {
        val templateText = GenerateCodeAction::class.java.getResource("/template/repository-test.tpl").readText()

        val engine = GStringTemplateEngine()
        val template = engine
            .createTemplate(templateText)
            .make(mapOf(Pair("table", table)))
        val value = template.toString()

        val path = "${psiPackage.directories[0].virtualFile.canonicalPath}/repository/jdbc"
            .replace("main/java", "test/java")

        Files.createDirectories(Paths.get(path))
        val writer = FileWriter("${path}/${table.repositoryClassName}Test.java")
        writer.write(value)
        writer.flush()
        writer.close()

    }
}