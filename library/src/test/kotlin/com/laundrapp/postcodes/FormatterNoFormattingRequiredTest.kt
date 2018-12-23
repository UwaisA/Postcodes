package com.laundrapp.postcodes

import org.junit.Assert.*
import org.junit.Test
import java.util.*

class FormatterNoFormattingRequiredTest {

    private val formatterUS = Formatter(Validator(Locale.US))

    @Test
    fun `Empty string formats`() {
        assertEquals(CursoredString("", 0), formatterUS.format(CursoredString("", 0)))
    }

    @Test
    fun `Valid partial postcode is returned unchanged`() {
        assertEquals(CursoredString("12", 0), formatterUS.format(CursoredString("12", 0)))
        assertEquals(CursoredString("12345-12", 0), formatterUS.format(CursoredString("12345-12", 0)))
        assertEquals(CursoredString("12345 1", 0), formatterUS.format(CursoredString("12345 1", 0)))
    }

    @Test
    fun `Valid full postcode is returned unchanged`() {
        assertEquals(CursoredString("12345", 0), formatterUS.format(CursoredString("12345", 0)))
        assertEquals(CursoredString("12345-1234", 0), formatterUS.format(CursoredString("12345-1234", 0)))
        assertEquals(CursoredString("12345 1234", 0), formatterUS.format(CursoredString("12345 1234", 0)))
    }
}