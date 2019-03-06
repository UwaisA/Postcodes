package com.laundrapp.postcodes

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class ComponentsTest {

    @Test(expected = IllegalArgumentException::class)
    fun `IllegalArgumentException for invalid postcode`() {
        Components("123a", Validator(Locale.US))
    }

    @Test
    fun `Successful construction for valid postcode`() {
        Components("123", Validator(Locale.US))
        Components("12345-6789", Validator(Locale.US))
        Components("M34 4AB", Validator(Locale.UK))
        Components("M34 4AB", Validator(Locale.UK))
    }

    @Test
    fun `Can get postcode`() {
        assertEquals(
                "123",
                Components("123", Validator(Locale.US)).postcode
        )
        assertEquals(
                "M34 4AB",
                Components("M34 4AB", Validator(Locale.UK)).postcode
        )
    }

    @Test
    fun `Can get major postcode`() {
        assertEquals(
                "12345",
                Components("12345 123", Validator(Locale.US)).major
        )
        assertEquals(
                "M34",
                Components("M34 4AB", Validator(Locale.UK)).major
        )
    }

    @Test
    fun `Can get minor postcode`() {
        assertEquals(
                "6789",
                Components("12345 6789", Validator(Locale.US)).minor
        )
        assertEquals(
                "4AB",
                Components("M34 4AB", Validator(Locale.UK)).minor
        )
    }
}