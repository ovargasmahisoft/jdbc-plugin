package com.github.ovargasmahisoft.jdbcplugin.utils

import com.github.ovargasmahisoft.jdbcplugin.actions.GenerateCodeAction
import groovy.text.GStringTemplateEngine
import org.junit.Assert
import org.junit.Test
import java.util.Collections

class TemplateTest {

    class Dummy(val item: String, val items: List<Dummy> = Collections.emptyList())

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