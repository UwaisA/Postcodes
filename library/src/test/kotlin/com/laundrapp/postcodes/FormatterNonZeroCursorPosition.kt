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

    @Test
    fun `Postcode input sanitised, no reduction to position`() {
        assertEquals(CursoredString("12345", 2), formatterUS.format(CursoredString("1234##5", 2)))
    }

    @Test
    fun `Postcode input sanitised, reduction to position`() {
        assertEquals(CursoredString("12345", 4), formatterUS.format(CursoredString("1####2345", 8)))
    }

    @Test
    fun `Postcode input sanitised, partial reduction to position`() {
        assertEquals(CursoredString("12345", 3), formatterUS.format(CursoredString("1#2#34#5#", 5)))
    }

    @Test
    fun `Postcode input formatted, no change to position`() {
        assertEquals(CursoredString("12345-67", 4), formatterUS.format(CursoredString("1234567", 4)))
    }

    @Test
    fun `Postcode input formatted, input cursor on separator location`() {
        assertEquals(CursoredString("12345-67", 5), formatterUS.format(CursoredString("1234567", 5)))
    }

    @Test
    fun `Postcode input formatted, input cursor after separator location`() {
        assertEquals(CursoredString("12345-67", 7), formatterUS.format(CursoredString("1234567", 6)))
    }

    @Test
    fun `Incorrectly separated postcode input, input cursor on separator location`() {
        assertEquals(CursoredString("12345-67", 5), formatterUS.format(CursoredString("12345#67", 6)))
        assertEquals(CursoredString("12345-67", 7), formatterUS.format(CursoredString("123+45+67", 8)))
    }
}