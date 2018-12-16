package com.laundrapp.postcodes

import org.junit.Test
import java.util.*

class FormatterUnformattableTest {

    private val formatterDE = Formatter(Locale.GERMANY)
    private val formatterUS = Formatter(Locale.US)
    private val formatterUK = Formatter(Locale.UK)

    @Test(expected = Formatter.CouldNotFormatException::class)
    fun `Too long UK postcode throws CouldNotFormatException`() {
        formatterUK.format("E1 1JJJJ")
    }

    @Test(expected = Formatter.CouldNotFormatException::class)
    fun `Too long US postcode throws CouldNotFormatException`() {
        formatterUS.format("1234567890")
    }

    @Test(expected = Formatter.CouldNotFormatException::class)
    fun `Too long German postcode throws CouldNotFormatException`() {
        formatterDE.format("123456")
    }

    @Test(expected = Formatter.CouldNotFormatException::class)
    fun `Invalid UK postcode throws CouldNotFormatException`() {
        formatterUK.format("1")
    }

    @Test(expected = Formatter.CouldNotFormatException::class)
    fun `Invalid US postcode throws CouldNotFormatException`() {
        formatterUS.format("A")
    }

    @Test(expected = Formatter.CouldNotFormatException::class)
    fun `Invalid German postcode throws CouldNotFormatException`() {
        formatterDE.format("A")
    }

}