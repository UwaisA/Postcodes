package com.laundrapp.postcodes

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class FormatterTest {
    private val formatterLV = Formatter(Validator(Locale.forLanguageTag("en-LV")))
    private val formatterUS = Formatter(Validator( Locale.US))
    private val formatterSV = Formatter(Validator(Locale.forLanguageTag("en-SV")))

    @Test
    fun `'-' added to postcode where needed`() {
        assertEquals(CursoredString("LV-1234", 0), formatterLV.format(CursoredString("LV1234", 0)))
        assertEquals(CursoredString("LV-12", 0), formatterLV.format(CursoredString("LV12", 0)))
        assertEquals(CursoredString("12345-6", 0), formatterUS.format(CursoredString("123456", 0)))
        assertEquals(CursoredString("12345-6789", 0), formatterUS.format(CursoredString("123456789", 0)))
    }

    @Test
    fun `'-' not inserted when it is final character of a postcode`() {
        assertEquals(CursoredString("LV", 0), formatterLV.format(CursoredString("LV", 0)))
        assertEquals(CursoredString("LV", 0), formatterLV.format(CursoredString("LV-", 0)))
        assertEquals(CursoredString("12345", 0), formatterUS.format(CursoredString("12345", 0)))
        assertEquals(CursoredString("12345", 0), formatterUS.format(CursoredString("12345-", 0)))
    }

    @Test
    fun `' ' added to postcode where needed`() {
        assertEquals(CursoredString("CP 1115", 0), formatterSV.format(CursoredString("CP1115", 0)))
        assertEquals(CursoredString("CP 111", 0), formatterSV.format(CursoredString("CP111", 0)))
    }

    @Test
    fun `' ' not inserted when it is final character of a postcode`() {
        assertEquals(CursoredString("CP", 0), formatterSV.format(CursoredString("CP", 0)))
        assertEquals(CursoredString("CP", 0), formatterSV.format(CursoredString("CP ", 0)))
    }

    @Test
    fun `Lower case input returns upper case output`(){
        assertEquals(CursoredString("CP 1115", 0), formatterSV.format(CursoredString("cp 1115", 0)))
        assertEquals(CursoredString("LV-1234", 0), formatterLV.format(CursoredString("lv-1234", 0)))
    }

    @Test
    fun `Lower case and trimming formats correctly`() {
        assertEquals(CursoredString("CP 1115", 0), formatterSV.format(CursoredString("cp 1115;", 0)))
        assertEquals(CursoredString("LV-1234", 0), formatterLV.format(CursoredString("#lv-1234", 0)))
    }

    @Test
    fun `Lower case and stripping formats correctly`() {
        assertEquals(CursoredString("CP 1115", 0), formatterSV.format(CursoredString("cp 11'15", 0)))
        assertEquals(CursoredString("LV-1234", 0), formatterLV.format(CursoredString("#lv-1~234", 0)))
    }

    @Test
    fun `Lower case and formatting formats correctly`() {
        assertEquals(CursoredString("CP 1115", 0), formatterSV.format(CursoredString("cp1115", 0)))
        assertEquals(CursoredString("LV-123", 0), formatterLV.format(CursoredString("lv123", 0)))
    }

}