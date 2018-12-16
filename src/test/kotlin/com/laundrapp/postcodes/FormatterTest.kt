package com.laundrapp.postcodes

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class FormatterTest {
    private val formatterLV = Formatter(Locale.forLanguageTag("en-LV"))
    private val formatterUS = Formatter(Locale.US)
    private val formatterSV = Formatter(Locale.forLanguageTag("en-SV"))

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

    @Test
    fun `' ' added to postcode where needed`() {
        assertEquals("CP 1115", formatterSV.format("CP1115"))
        assertEquals("CP 111", formatterSV.format("CP111"))
    }

    @Test
    fun `' ' not inserted when it is final character of a postcode`() {
        assertEquals("CP", formatterSV.format("CP"))
        assertEquals("CP", formatterSV.format("CP "))
    }

    @Test
    fun `Lower case input returns upper case output`(){
        assertEquals("CP 1115", formatterSV.format("cp 1115"))
        assertEquals("LV-1234", formatterLV.format("lv-1234"))
    }

    @Test
    fun `Lower case and trimming formats correctly`() {
        assertEquals("CP 1115", formatterSV.format("cp 1115;"))
        assertEquals("LV-1234", formatterLV.format("#lv-1234"))
    }

    @Test
    fun `Lower case and stripping formats correctly`() {
        assertEquals("CP 1115", formatterSV.format("cp 11'15"))
        assertEquals("LV-1234", formatterLV.format("#lv-1~234"))
    }

    @Test
    fun `Lower case and formatting formats correctly`() {
        assertEquals("CP 1115", formatterSV.format("cp1115"))
        assertEquals("LV-123", formatterLV.format("lv123"))
    }

}