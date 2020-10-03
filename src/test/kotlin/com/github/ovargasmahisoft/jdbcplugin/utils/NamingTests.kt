package com.github.ovargasmahisoft.jdbcplugin.utils

import org.junit.Assert
import org.junit.Test

class NamingTests {

    @Test
    fun testSnakeCaseToLowerCamel() {
        val input = "hello_world"
        val expected = "helloWorld"
        Assert.assertEquals(expected, input.snakeToLowerCamelCase())
    }

    @Test
    fun testSnakeCaseToUpperCamel() {
        val input = "hello_world"
        val expected = "HelloWorld"
        Assert.assertEquals(expected, input.snakeToUpperCamelCase())
    }

    @Test
    fun testLowerCamelToLowerCamel() {
        val input = "helloWorld"
        val expected = "helloWorld"
        Assert.assertEquals(expected, input.snakeToLowerCamelCase())
    }

    @Test
    fun testUpperCamelToLowerCamel() {
        val input = "HelloWorld"
        val expected = "helloWorld"
        Assert.assertEquals(expected, input.snakeToLowerCamelCase())
    }
}