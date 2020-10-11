package com.github.ovargasmahisoft.jdbcplugin.utils

import com.github.mustachejava.DefaultMustacheFactory
import com.github.ovargasmahisoft.jdbcplugin.actions.GenerateCodeAction
import groovy.text.GStringTemplateEngine
import org.junit.Assert
import org.junit.Test
import java.io.StringReader
import java.io.StringWriter
import java.util.Collections

class TemplateTest {

    class Dummy(val item: String, val items: List<Dummy> = Collections.emptyList())

    @Test
    fun testTemplate() {
        val templateText = GenerateCodeAction::class.java.getResource("/template/template.txt").readText()
        val mustache = DefaultMustacheFactory().compile(StringReader(templateText), "template")
        val sw = StringWriter()
        mustache.execute(sw, Dummy("value item", listOf(
            Dummy("1"),
            Dummy("2"),
            Dummy("3")
        )))

        val value = sw.toString()

        Assert.assertEquals("", value)
    }

    @Test
    fun testGroovyTemplate() {
        val templateText = GenerateCodeAction::class.java.getResource("/template/template.grv").readText()
        val engine = GStringTemplateEngine()
        val template = engine
            .createTemplate(templateText)
            .make(
                mapOf(
                    Pair("table", Dummy("value item", listOf(
                        Dummy("1"),
                        Dummy("2"),
                        Dummy("3")
                    )))
                )
            )

        val value = template.toString()

        Assert.assertEquals("", value)
    }
}