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
    fun `Postcode input start sanitised, no change to position`() {
        assertEquals(CursoredString("12345", 1), formatterUS.format(CursoredString("12345#", 1)))
    }

    @Test
    fun `Postcode input start sanitised, partial reduction to position`() {
        assertEquals(CursoredString("12345", 0), formatterUS.format(CursoredString("####12345", 2)))
    }

    @Test
    fun `Postcode input start sanitised, reduction to position`() {
        assertEquals(CursoredString("12345", 1), formatterUS.format(CursoredString("#12345", 2)))
    }

    @Test
    fun `Postcode input end sanitised, no reduction to position`() {
        assertEquals(CursoredString("12345", 2), formatterUS.format(CursoredString("12345####", 2)))
    }

    @Test
    fun `Postcode input end sanitised, reduction to position`() {
        assertEquals(CursoredString("12345", 5), formatterUS.format(CursoredString("12345####", 9)))
    }

    @Test
    fun `Postcode input end sanitised, partial reduction to position`() {
        assertEquals(CursoredString("12345", 5), formatterUS.format(CursoredString("12345######", 7)))
    }
}