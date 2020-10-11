package com.github.ovargasmahisoft.jdbcplugin.actions

import com.github.mustachejava.DefaultMustacheFactory
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
import java.io.StringReader
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
            .map { tbl -> TableInfo(tbl, dialog.data().daoPackage.qualifiedName) }
            .forEach { tbl ->
                buildDaoFile(dialog.data().daoPackage, tbl)
                buildInterfaceRepository(dialog.data().daoPackage, tbl)
                buildClassRepository(dialog.data().daoPackage, tbl)
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
        val templateText = GenerateCodeAction::class.java.getResource("/template/dao.g").readText()

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

    private fun buildDaoFileMustache(psiPackage: PsiPackage, table: TableInfo) {
        val templateText = GenerateCodeAction::class.java.getResource("/template/Dto.java").readText()

        val mustache = DefaultMustacheFactory().compile(StringReader(templateText), "dao")

        val path = psiPackage.directories[0].virtualFile.canonicalPath

        Files.createDirectories(Paths.get("${path}/domain"))
        val writer = FileWriter("${path}/domain/${table.daoClassName}.java")
        mustache.execute(writer, table)
        writer.flush()
        writer.close()
    }

    private fun buildInterfaceRepository(psiPackage: PsiPackage, table: TableInfo) {
        val templateText = GenerateCodeAction::class.java.getResource("/template/repository-interface.g").readText()

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

    private fun buildInterfaceRepositoryMustache(psiPackage: PsiPackage, table: TableInfo) {
        val templateText = GenerateCodeAction::class.java.getResource("/template/RepositoryInterface.java").readText()

        val mustache = DefaultMustacheFactory().compile(StringReader(templateText), "interface")

        val path = "${psiPackage.directories[0].virtualFile.canonicalPath}/repository"

        Files.createDirectories(Paths.get(path))
        val writer = FileWriter("${path}/${table.repositoryInterfaceName}.java")
        mustache.execute(writer, table)
        writer.flush()
        writer.close()
    }

    private fun buildClassRepository(psiPackage: PsiPackage, table: TableInfo) {
        val templateText = GenerateCodeAction::class.java.getResource("/template/jdbc-repository.g").readText()

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

    private fun buildClassRepositoryMustache(psiPackage: PsiPackage, table: TableInfo) {
        val templateText = GenerateCodeAction::class.java.getResource("/template/JdbcRepository.java").readText()

        val mustache = DefaultMustacheFactory().compile(StringReader(templateText), "jdbc")

        val path = "${psiPackage.directories[0].virtualFile.canonicalPath}/repository/jdbc"

        Files.createDirectories(Paths.get(path))
        val writer = FileWriter("${path}/${table.repositoryClassName}.java")
        mustache.execute(writer, table)
        writer.flush()
        writer.close()
    }
}