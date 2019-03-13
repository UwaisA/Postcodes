package com.laundrapp.postcodes

import org.junit.Assert.*
import org.junit.Test
import java.util.*

class FormatterNoFormattingRequiredTest {

    private val formatterUS = Formatter.create(Validator.create(Locale.US))

    @Test
    fun `Empty string formats`() {
        assertEquals("", formatterUS.format(CursoredString("", 0)).string)
    }

    @Test
    fun `Valid partial postcode is returned unchanged`() {
        assertEquals("12", formatterUS.format(CursoredString("12", 0)).string)
        assertEquals("12345-12", formatterUS.format(CursoredString("12345-12", 0)).string)
        assertEquals("12345 1", formatterUS.format(CursoredString("12345 1", 0)).string)
    }

    @Test
    fun `Valid full postcode is returned unchanged`() {
        assertEquals("12345", formatterUS.format(CursoredString("12345", 0)).string)
        assertEquals("12345-1234", formatterUS.format(CursoredString("12345-1234", 0)).string)
        assertEquals("12345 1234", formatterUS.format(CursoredString("12345 1234", 0)).string)
    }
}