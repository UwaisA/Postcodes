package com.laundrapp.postcodes

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class FormatterNonAlphanumericRemovalsTest {

    private val formatterUS = Formatter(Validator(Locale.US))

    @Test
    fun `Leading non-alphanumeric characters removed from formatted postcode with separator`() {
        assertEquals(CursoredString("12345 1234", 0), formatterUS.format(CursoredString("12345 1234", 0)))
        assertEquals(CursoredString("12345 1234", 0), formatterUS.format(CursoredString("-12345 1234", 0)))
        assertEquals(CursoredString("12345 1234", 0), formatterUS.format(CursoredString("+12345 1234", 0)))
        assertEquals(CursoredString("12345 1234", 0), formatterUS.format(CursoredString("..12345 1234", 0)))
        assertEquals(CursoredString("12345-1234", 0), formatterUS.format(CursoredString(".#.12345-1234", 0)))
    }

    @Test
    fun `Trailing non-alphanumeric characters removed from formatted postcode with separator`() {
        assertEquals(CursoredString("12345 1234", 0), formatterUS.format(CursoredString("12345 1234$", 0)))
        assertEquals(CursoredString("12345 1234", 0), formatterUS.format(CursoredString("12345 1234$%", 0)))
        assertEquals(CursoredString("12345 1234", 0), formatterUS.format(CursoredString("12345 1234][", 0)))
        assertEquals(CursoredString("12345 1234", 0), formatterUS.format(CursoredString("12345 1234;.", 0)))
        assertEquals(CursoredString("12345-1234", 0), formatterUS.format(CursoredString("12345-1234.;.", 0)))
    }

    @Test
    fun `Trailing + leading non-alphanumeric characters removed from formatted postcode with separator`() {
        assertEquals(CursoredString("12345 1234", 0), formatterUS.format(CursoredString("^12345 1234$", 0)))
        assertEquals(CursoredString("12345 1234", 0), formatterUS.format(CursoredString("&12345 1234$%", 0)))
        assertEquals(CursoredString("12345 1234", 0), formatterUS.format(CursoredString("((12345 1234][", 0)))
        assertEquals(CursoredString("12345 1234", 0), formatterUS.format(CursoredString(":>12345 1234;.", 0)))
        assertEquals(CursoredString("12345-1234", 0), formatterUS.format(CursoredString(",<,12345-1234.;.", 0)))
    }

    @Test
    fun `Trailing non-alphanumeric characters are removed`() {
        assertEquals(CursoredString("12345", 0), formatterUS.format(CursoredString("12345.", 0)))
        assertEquals(CursoredString("12345", 0), formatterUS.format(CursoredString("12345..", 0)))
        assertEquals(CursoredString("12345", 0), formatterUS.format(CursoredString("12345#.", 0)))
        assertEquals(CursoredString("12345", 0), formatterUS.format(CursoredString("12345#", 0)))
        assertEquals(CursoredString("12345", 0), formatterUS.format(CursoredString("12345~", 0)))
        assertEquals(CursoredString("12345", 0), formatterUS.format(CursoredString("12345!", 0)))
        assertEquals(CursoredString("12345", 0), formatterUS.format(CursoredString("12345\\", 0)))
    }

    @Test
    fun `Leading non-alphanumeric characters are removed`() {
        assertEquals(CursoredString("12345", 0), formatterUS.format(CursoredString(".12345", 0)))
        assertEquals(CursoredString("12345", 0), formatterUS.format(CursoredString("..12345", 0)))
        assertEquals(CursoredString("12345", 0), formatterUS.format(CursoredString("#.12345", 0)))
        assertEquals(CursoredString("12345", 0), formatterUS.format(CursoredString("#12345", 0)))
        assertEquals(CursoredString("12345", 0), formatterUS.format(CursoredString("~12345", 0)))
        assertEquals(CursoredString("12345", 0), formatterUS.format(CursoredString("!12345", 0)))
        assertEquals(CursoredString("12345", 0), formatterUS.format(CursoredString("\\12345", 0)))
    }

    @Test
    fun `Contained non-alphanumeric characters are removed`() {
        assertEquals(CursoredString("12345", 0), formatterUS.format(CursoredString("1.2345", 0)))
        assertEquals(CursoredString("12345", 0), formatterUS.format(CursoredString("1.2.345", 0)))
        assertEquals(CursoredString("12345", 0), formatterUS.format(CursoredString("123#.45", 0)))
        assertEquals(CursoredString("12345", 0), formatterUS.format(CursoredString("1234#5", 0)))
        assertEquals(CursoredString("12345", 0), formatterUS.format(CursoredString("12~345", 0)))
        assertEquals(CursoredString("12345", 0), formatterUS.format(CursoredString("123!45", 0)))
        assertEquals(CursoredString("12345", 0), formatterUS.format(CursoredString("12\\345", 0)))
    }

    @Test
    fun `All non-alphanumeric characters are removed`() {
        assertEquals(CursoredString("12345", 0), formatterUS.format(CursoredString(".1.2345.", 0)))
        assertEquals(CursoredString("12345", 0), formatterUS.format(CursoredString("1.2.345.", 0)))
        assertEquals(CursoredString("12345", 0), formatterUS.format(CursoredString("#123#.45", 0)))
        assertEquals(CursoredString("12345", 0), formatterUS.format(CursoredString("~1234#5", 0)))
        assertEquals(CursoredString("12345", 0), formatterUS.format(CursoredString("12~345#", 0)))
        assertEquals(CursoredString("12345", 0), formatterUS.format(CursoredString("~12345!", 0)))
        assertEquals(CursoredString("12345", 0), formatterUS.format(CursoredString("\\1\\2\\345", 0)))
    }
}