package com.laundrapp.postcodes

import org.junit.Assert.*
import org.junit.Test
import java.util.*

class FormatterTest {

    @Test
    fun `Empty string formats`() {
        assertEquals("", Formatter(Locale.UK).format(""))
    }

    @Test
    fun `Valid partial postcode formats`() {
        assertEquals("12", Formatter(Locale.US).format("12"))
    }

    @Test
    fun `Valid full postcode formats`() {
        assertEquals("12345", Formatter(Locale.US).format("12345"))
        assertEquals("12345-1234", Formatter(Locale.US).format("12345-1234"))
        assertEquals("12345 1234", Formatter(Locale.US).format("12345 1234"))
    }
}