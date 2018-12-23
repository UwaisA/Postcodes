package com.laundrapp.postcodes

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class FormatterNonZeroCursorPosition {
    private val formatterUS = Formatter(Validator(Locale.US))

    @Test
    fun `Non zero no change`() {
        assertEquals(CursoredString("12345", 1), formatterUS.format(CursoredString("12345", 1)))
    }

    @Test
    fun `Postcode formatted, no change to position`() {
        assertEquals(CursoredString("12345-6", 1), formatterUS.format(CursoredString("123456", 1)))
    }

    @Test
    fun `Postcode input sanitised, no change to position`() {
        assertEquals(CursoredString("12345", 1), formatterUS.format(CursoredString("12345#", 1)))
    }
}