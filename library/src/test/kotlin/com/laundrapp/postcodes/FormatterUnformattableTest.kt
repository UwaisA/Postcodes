package com.laundrapp.postcodes

import org.junit.Test
import java.util.*

class FormatterUnformattableTest {

    private val formatterDE = Formatter(Validator(Locale.GERMANY))
    private val formatterUS = Formatter(Validator(Locale.US))
    private val formatterUK = Formatter(Validator(Locale.UK))

    @Test(expected = Formatter.CouldNotFormatException::class)
    fun `Too long UK postcode throws CouldNotFormatException`() {
        formatterUK.format(CursoredString("E1 1JJJJ", 0))
    }

    @Test(expected = Formatter.CouldNotFormatException::class)
    fun `Too long US postcode throws CouldNotFormatException`() {
        formatterUS.format(CursoredString("1234567890", 0))
    }

    @Test(expected = Formatter.CouldNotFormatException::class)
    fun `Too long German postcode throws CouldNotFormatException`() {
        formatterDE.format(CursoredString("123456", 0))
    }

    @Test(expected = Formatter.CouldNotFormatException::class)
    fun `Invalid UK postcode throws CouldNotFormatException`() {
        formatterUK.format(CursoredString("1", 0))
    }

    @Test(expected = Formatter.CouldNotFormatException::class)
    fun `Invalid US postcode throws CouldNotFormatException`() {
        formatterUS.format(CursoredString("A", 0))
    }

    @Test(expected = Formatter.CouldNotFormatException::class)
    fun `Invalid German postcode throws CouldNotFormatException`() {
        formatterDE.format(CursoredString("A", 0))
    }

}