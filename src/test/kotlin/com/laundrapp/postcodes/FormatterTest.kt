package com.laundrapp.postcodes

import org.junit.Assert.*
import org.junit.Test
import java.util.*

class FormatterTest {

    @Test
    fun `Empty string formats successfully`() {
        assertEquals("", Formatter(Locale.UK).format(""))
    }
}