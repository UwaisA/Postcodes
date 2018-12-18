package com.laundrapp.postcodes

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class FormatterNonAlphanumericRemovalsTest {

    private val formatterUS = Formatter(Validator(Locale.US))

    @Test
    fun `Leading non-alphanumeric characters removed from formatted postcode with separator`() {
        assertEquals("12345 1234", formatterUS.format("12345 1234"))
        assertEquals("12345 1234", formatterUS.format("-12345 1234"))
        assertEquals("12345 1234", formatterUS.format("+12345 1234"))
        assertEquals("12345 1234", formatterUS.format("..12345 1234"))
        assertEquals("12345-1234", formatterUS.format(".#.12345-1234"))
    }

    @Test
    fun `Trailing non-alphanumeric characters removed from formatted postcode with separator`() {
        assertEquals("12345 1234", formatterUS.format("12345 1234$"))
        assertEquals("12345 1234", formatterUS.format("12345 1234$%"))
        assertEquals("12345 1234", formatterUS.format("12345 1234]["))
        assertEquals("12345 1234", formatterUS.format("12345 1234;."))
        assertEquals("12345-1234", formatterUS.format("12345-1234.;."))
    }

    @Test
    fun `Trailing + leading non-alphanumeric characters removed from formatted postcode with separator`() {
        assertEquals("12345 1234", formatterUS.format("^12345 1234$"))
        assertEquals("12345 1234", formatterUS.format("&12345 1234$%"))
        assertEquals("12345 1234", formatterUS.format("((12345 1234]["))
        assertEquals("12345 1234", formatterUS.format(":>12345 1234;."))
        assertEquals("12345-1234", formatterUS.format(",<,12345-1234.;."))
    }

    @Test
    fun `Trailing non-alphanumeric characters are removed`() {
        assertEquals("12345", formatterUS.format("12345."))
        assertEquals("12345", formatterUS.format("12345.."))
        assertEquals("12345", formatterUS.format("12345#."))
        assertEquals("12345", formatterUS.format("12345#"))
        assertEquals("12345", formatterUS.format("12345~"))
        assertEquals("12345", formatterUS.format("12345!"))
        assertEquals("12345", formatterUS.format("12345\\"))
    }

    @Test
    fun `Leading non-alphanumeric characters are removed`() {
        assertEquals("12345", formatterUS.format(".12345"))
        assertEquals("12345", formatterUS.format("..12345"))
        assertEquals("12345", formatterUS.format("#.12345"))
        assertEquals("12345", formatterUS.format("#12345"))
        assertEquals("12345", formatterUS.format("~12345"))
        assertEquals("12345", formatterUS.format("!12345"))
        assertEquals("12345", formatterUS.format("\\12345"))
    }

    @Test
    fun `Contained non-alphanumeric characters are removed`() {
        assertEquals("12345", formatterUS.format("1.2345"))
        assertEquals("12345", formatterUS.format("1.2.345"))
        assertEquals("12345", formatterUS.format("123#.45"))
        assertEquals("12345", formatterUS.format("1234#5"))
        assertEquals("12345", formatterUS.format("12~345"))
        assertEquals("12345", formatterUS.format("123!45"))
        assertEquals("12345", formatterUS.format("12\\345"))
    }

    @Test
    fun `All non-alphanumeric characters are removed`() {
        assertEquals("12345", formatterUS.format(".1.2345."))
        assertEquals("12345", formatterUS.format("1.2.345."))
        assertEquals("12345", formatterUS.format("#123#.45"))
        assertEquals("12345", formatterUS.format("~1234#5"))
        assertEquals("12345", formatterUS.format("12~345#"))
        assertEquals("12345", formatterUS.format("~12345!"))
        assertEquals("12345", formatterUS.format("\\1\\2\\345"))
    }
}