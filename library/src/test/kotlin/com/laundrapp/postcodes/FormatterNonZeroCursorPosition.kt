package com.laundrapp.postcodes

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class FormatterNonZeroCursorPosition {
    private val formatterUS = Formatter(Validator.create(Locale.US))

    @Test
    fun `Non zero no change`() {
        assertEquals(1, formatterUS.format(CursoredString("12345", 1)).cursorPosition)
    }

    @Test
    fun `Postcode formatted, no change to position`() {
        assertEquals(1, formatterUS.format(CursoredString("123456", 1)).cursorPosition)
    }

    @Test
    fun `Postcode input start sanitised, no change to position`() {
        assertEquals(1, formatterUS.format(CursoredString("12345#", 1)).cursorPosition)
    }

    @Test
    fun `Postcode input start sanitised, partial reduction to position`() {
        assertEquals(0, formatterUS.format(CursoredString("####12345", 2)).cursorPosition)
    }

    @Test
    fun `Postcode input start sanitised, reduction to position`() {
        assertEquals(1, formatterUS.format(CursoredString("#12345", 2)).cursorPosition)
    }

    @Test
    fun `Postcode input end sanitised, no reduction to position`() {
        assertEquals(2, formatterUS.format(CursoredString("12345####", 2)).cursorPosition)
    }

    @Test
    fun `Postcode input end sanitised, reduction to position`() {
        assertEquals(5, formatterUS.format(CursoredString("12345####", 9)).cursorPosition)
    }

    @Test
    fun `Postcode input end sanitised, partial reduction to position`() {
        assertEquals(5, formatterUS.format(CursoredString("12345######", 7)).cursorPosition)
    }

    @Test
    fun `Postcode input sanitised, no reduction to position`() {
        assertEquals(2, formatterUS.format(CursoredString("1234##5", 2)).cursorPosition)
    }

    @Test
    fun `Postcode input sanitised, reduction to position`() {
        assertEquals(4, formatterUS.format(CursoredString("1####2345", 8)).cursorPosition)
    }

    @Test
    fun `Postcode input sanitised, partial reduction to position`() {
        assertEquals(3, formatterUS.format(CursoredString("1#2#34#5#", 5)).cursorPosition)
    }

    @Test
    fun `Postcode input formatted, no change to position`() {
        assertEquals(4, formatterUS.format(CursoredString("1234567", 4)).cursorPosition)
    }

    @Test
    fun `Postcode input formatted, input cursor on separator location`() {
        assertEquals(5, formatterUS.format(CursoredString("1234567", 5)).cursorPosition)
    }

    @Test
    fun `Postcode input formatted, input cursor after separator location`() {
        assertEquals(7, formatterUS.format(CursoredString("1234567", 6)).cursorPosition)
    }

    @Test
    fun `Incorrectly separated postcode input, input cursor on separator location`() {
        assertEquals(5, formatterUS.format(CursoredString("12345#67", 6)).cursorPosition)
        assertEquals(7, formatterUS.format(CursoredString("123+45+67", 8)).cursorPosition)
    }
}