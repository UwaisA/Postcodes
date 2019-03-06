package com.laundrapp.postcodes

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class FormatterTest {
    private val formatterLV = Formatter(Validator.create(Locale.forLanguageTag("en-LV")))
    private val formatterUS = Formatter(Validator.create(Locale.US))
    private val formatterSV = Formatter(Validator.create(Locale.forLanguageTag("en-SV")))

    @Test
    fun `'-' added to postcode where needed`() {
        assertEquals("LV-1234", formatterLV.format(CursoredString("LV1234", 0)).string)
        assertEquals("LV-12", formatterLV.format(CursoredString("LV12", 0)).string)
        assertEquals("12345-6", formatterUS.format(CursoredString("123456", 0)).string)
        assertEquals("12345-6789", formatterUS.format(CursoredString("123456789", 0)).string)
    }

    @Test
    fun `'-' not inserted when it is final character of a postcode`() {
        assertEquals("LV", formatterLV.format(CursoredString("LV", 0)).string)
        assertEquals("LV", formatterLV.format(CursoredString("LV-", 0)).string)
        assertEquals("12345", formatterUS.format(CursoredString("12345", 0)).string)
        assertEquals("12345", formatterUS.format(CursoredString("12345-", 0)).string)
    }

    @Test
    fun `' ' added to postcode where needed`() {
        assertEquals("CP 1115", formatterSV.format(CursoredString("CP1115", 0)).string)
        assertEquals("CP 111", formatterSV.format(CursoredString("CP111", 0)).string)
    }

    @Test
    fun `' ' not inserted when it is final character of a postcode`() {
        assertEquals("CP", formatterSV.format(CursoredString("CP", 0)).string)
        assertEquals("CP", formatterSV.format(CursoredString("CP ", 0)).string)
    }

    @Test
    fun `Lower case input returns upper case output`(){
        assertEquals("CP 1115", formatterSV.format(CursoredString("cp 1115", 0)).string)
        assertEquals("LV-1234", formatterLV.format(CursoredString("lv-1234", 0)).string)
    }

    @Test
    fun `Lower case and trimming formats correctly`() {
        assertEquals("CP 1115", formatterSV.format(CursoredString("cp 1115;", 0)).string)
        assertEquals("LV-1234", formatterLV.format(CursoredString("#lv-1234", 0)).string)
    }

    @Test
    fun `Lower case and stripping formats correctly`() {
        assertEquals("CP 1115", formatterSV.format(CursoredString("cp 11'15", 0)).string)
        assertEquals("LV-1234", formatterLV.format(CursoredString("#lv-1~234", 0)).string)
    }

    @Test
    fun `Lower case and formatting formats correctly`() {
        assertEquals("CP 1115", formatterSV.format(CursoredString("cp1115", 0)).string)
        assertEquals("LV-123", formatterLV.format(CursoredString("lv123", 0)).string)
    }

}