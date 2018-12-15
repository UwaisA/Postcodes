package com.laundrapp.postcodes

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class FormatterTest {
    private val formatterLV: Formatter = Formatter(Locale.forLanguageTag("en-LV"))
    private val formatterUS: Formatter = Formatter(Locale.US)

    @Test
    fun `'-' added to postcode where needed`() {
        assertEquals("LV-1234", formatterLV.format("LV1234"))
        assertEquals("LV-12", formatterLV.format("LV12"))
    }

    @Test
    fun `'-' not inserted when it is final character of a postcode`() {
        assertEquals("LV", formatterLV.format("LV"))
        assertEquals("LV", formatterLV.format("LV-"))
    }

    @Test
    fun `Leading separators removed from correctly formatted postcode`() {
        assertEquals("12345 1234", formatterUS.format("12345 1234"))
        assertEquals("12345 1234", formatterUS.format("-12345 1234"))
        assertEquals("12345 1234", formatterUS.format("+12345 1234"))
        assertEquals("12345 1234", formatterUS.format("..12345 1234"))
        assertEquals("12345-1234", formatterUS.format(".#.12345-1234"))
    }

    @Test
    fun `Trailing separators removed from correctly formatted postcode`() {
        assertEquals("12345 1234", formatterUS.format("12345 1234$"))
        assertEquals("12345 1234", formatterUS.format("12345 1234$%"))
        assertEquals("12345 1234", formatterUS.format("12345 1234]["))
        assertEquals("12345 1234", formatterUS.format("12345 1234;."))
        assertEquals("12345-1234", formatterUS.format("12345-1234.;."))
    }

    @Test
    fun `Trailing + leading separators removed from correctly formatted postcode`() {
        assertEquals("12345 1234", formatterUS.format("^12345 1234$"))
        assertEquals("12345 1234", formatterUS.format("&12345 1234$%"))
        assertEquals("12345 1234", formatterUS.format("((12345 1234]["))
        assertEquals("12345 1234", formatterUS.format(":>12345 1234;."))
        assertEquals("12345-1234", formatterUS.format(",<,12345-1234.;."))
    }
}