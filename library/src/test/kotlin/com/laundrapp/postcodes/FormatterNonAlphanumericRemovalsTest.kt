package com.laundrapp.postcodes

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class FormatterNonAlphanumericRemovalsTest {

    private val formatterUS = Formatter(Validator.create(Locale.US))

    @Test
    fun `Leading non-alphanumeric characters removed from formatted postcode with separator`() {
        assertEquals("12345 1234", formatterUS.format(CursoredString("12345 1234", 0)).string)
        assertEquals("12345 1234", formatterUS.format(CursoredString("-12345 1234", 0)).string)
        assertEquals("12345 1234", formatterUS.format(CursoredString("+12345 1234", 0)).string)
        assertEquals("12345 1234", formatterUS.format(CursoredString("..12345 1234", 0)).string)
        assertEquals("12345-1234", formatterUS.format(CursoredString(".#.12345-1234", 0)).string)
    }

    @Test
    fun `Trailing non-alphanumeric characters removed from formatted postcode with separator`() {
        assertEquals("12345 1234", formatterUS.format(CursoredString("12345 1234$", 0)).string)
        assertEquals("12345 1234", formatterUS.format(CursoredString("12345 1234$%", 0)).string)
        assertEquals("12345 1234", formatterUS.format(CursoredString("12345 1234][", 0)).string)
        assertEquals("12345 1234", formatterUS.format(CursoredString("12345 1234;.", 0)).string)
        assertEquals("12345-1234", formatterUS.format(CursoredString("12345-1234.;.", 0)).string)
    }

    @Test
    fun `Trailing + leading non-alphanumeric characters removed from formatted postcode with separator`() {
        assertEquals("12345 1234", formatterUS.format(CursoredString("^12345 1234$", 0)).string)
        assertEquals("12345 1234", formatterUS.format(CursoredString("&12345 1234$%", 0)).string)
        assertEquals("12345 1234", formatterUS.format(CursoredString("((12345 1234][", 0)).string)
        assertEquals("12345 1234", formatterUS.format(CursoredString(":>12345 1234;.", 0)).string)
        assertEquals("12345-1234", formatterUS.format(CursoredString(",<,12345-1234.;.", 0)).string)
    }

    @Test
    fun `Trailing non-alphanumeric characters are removed`() {
        assertEquals("12345", formatterUS.format(CursoredString("12345.", 0)).string)
        assertEquals("12345", formatterUS.format(CursoredString("12345..", 0)).string)
        assertEquals("12345", formatterUS.format(CursoredString("12345#.", 0)).string)
        assertEquals("12345", formatterUS.format(CursoredString("12345#", 0)).string)
        assertEquals("12345", formatterUS.format(CursoredString("12345~", 0)).string)
        assertEquals("12345", formatterUS.format(CursoredString("12345!", 0)).string)
        assertEquals("12345", formatterUS.format(CursoredString("12345\\", 0)).string)
    }

    @Test
    fun `Leading non-alphanumeric characters are removed`() {
        assertEquals("12345", formatterUS.format(CursoredString(".12345", 0)).string)
        assertEquals("12345", formatterUS.format(CursoredString("..12345", 0)).string)
        assertEquals("12345", formatterUS.format(CursoredString("#.12345", 0)).string)
        assertEquals("12345", formatterUS.format(CursoredString("#12345", 0)).string)
        assertEquals("12345", formatterUS.format(CursoredString("~12345", 0)).string)
        assertEquals("12345", formatterUS.format(CursoredString("!12345", 0)).string)
        assertEquals("12345", formatterUS.format(CursoredString("\\12345", 0)).string)
    }

    @Test
    fun `Contained non-alphanumeric characters are removed`() {
        assertEquals("12345", formatterUS.format(CursoredString("1.2345", 0)).string)
        assertEquals("12345", formatterUS.format(CursoredString("1.2.345", 0)).string)
        assertEquals("12345", formatterUS.format(CursoredString("123#.45", 0)).string)
        assertEquals("12345", formatterUS.format(CursoredString("1234#5", 0)).string)
        assertEquals("12345", formatterUS.format(CursoredString("12~345", 0)).string)
        assertEquals("12345", formatterUS.format(CursoredString("123!45", 0)).string)
        assertEquals("12345", formatterUS.format(CursoredString("12\\345", 0)).string)
    }

    @Test
    fun `All non-alphanumeric characters are removed`() {
        assertEquals("12345", formatterUS.format(CursoredString(".1.2345.", 0)).string)
        assertEquals("12345", formatterUS.format(CursoredString("1.2.345.", 0)).string)
        assertEquals("12345", formatterUS.format(CursoredString("#123#.45", 0)).string)
        assertEquals("12345", formatterUS.format(CursoredString("~1234#5", 0)).string)
        assertEquals("12345", formatterUS.format(CursoredString("12~345#", 0)).string)
        assertEquals("12345", formatterUS.format(CursoredString("~12345!", 0)).string)
        assertEquals("12345", formatterUS.format(CursoredString("\\1\\2\\345", 0)).string)
    }
}