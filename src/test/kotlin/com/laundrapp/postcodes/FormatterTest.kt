package com.laundrapp.postcodes

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class FormatterTest {
    private val formatterLV = Formatter(Locale.forLanguageTag("en-LV"))
    private val formatterUS = Formatter(Locale.US)

    @Test
    fun `'-' added to postcode where needed`() {
        assertEquals("LV-1234", formatterLV.format("LV1234"))
        assertEquals("LV-12", formatterLV.format("LV12"))
        assertEquals("12345-6", formatterUS.format("123456"))
        assertEquals("12345-6789", formatterUS.format("123456789"))
    }

    @Test
    fun `'-' not inserted when it is final character of a postcode`() {
        assertEquals("LV", formatterLV.format("LV"))
        assertEquals("LV", formatterLV.format("LV-"))
        assertEquals("12345", formatterUS.format("12345"))
        assertEquals("12345", formatterUS.format("12345-"))
    }

}