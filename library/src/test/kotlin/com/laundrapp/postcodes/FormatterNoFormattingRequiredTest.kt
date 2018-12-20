package com.laundrapp.postcodes

import org.junit.Assert.*
import org.junit.Test
import java.util.*

class FormatterNoFormattingRequiredTest {

    private val formatterUS = Formatter(Validator(Locale.US))

    @Test
    fun `Empty string formats`() {
        assertEquals("", formatterUS.format(""))
    }

    @Test
    fun `Valid partial postcode is returned unchanged`() {
        assertEquals("12", formatterUS.format("12"))
        assertEquals("12345-12", formatterUS.format("12345-12"))
        assertEquals("12345 1", formatterUS.format("12345 1"))
    }

    @Test
    fun `Valid full postcode is returned unchanged`() {
        assertEquals("12345", formatterUS.format("12345"))
        assertEquals("12345-1234", formatterUS.format("12345-1234"))
        assertEquals("12345 1234", formatterUS.format("12345 1234"))
    }
}