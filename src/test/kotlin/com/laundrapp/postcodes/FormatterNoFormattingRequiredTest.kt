package com.laundrapp.postcodes

import org.junit.Assert.*
import org.junit.Test
import java.util.*

class FormatterNoFormattingRequiredTest {

    private val formatterUS = Formatter(Locale.US)

    @Test
    fun `Empty string formats`() {
        assertEquals("", formatterUS.format(""))
    }

    @Test
    fun `Valid partial postcode formats`() {
        assertEquals("12", formatterUS.format("12"))
    }

    @Test
    fun `Valid full postcode formats`() {
        assertEquals("12345", formatterUS.format("12345"))
        assertEquals("12345-1234", formatterUS.format("12345-1234"))
        assertEquals("12345 1234", formatterUS.format("12345 1234"))
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