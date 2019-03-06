package com.laundrapp.postcodes

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*
import com.laundrapp.postcodes.Options.OptionalSeparator.*

class ComponentsTest {

    @Test(expected = IllegalArgumentException::class)
    fun `IllegalArgumentException for invalid postcode`() {
        Components("123a", Validator.create(Locale.US))
    }

    @Test
    fun `Successful construction for valid postcode`() {
        Components("123", Validator.create(Locale.US))
        Components("12345-6789", Validator.create(Locale.US))
        Components("M34 4AB", Validator.create(Locale.UK))
        Components("M34 4AB", Validator.create(Locale.UK))
    }

    @Test
    fun `Can get postcode`() {
        assertEquals(
                "123",
                Components("123", Validator.create(Locale.US)).postcode
        )
        assertEquals(
                "M34 4AB",
                Components("M34 4AB", Validator.create(Locale.UK)).postcode
        )
    }

    @Test
    fun `Can get major postcode`() {
        assertEquals(
                "12345",
                Components("12345 123", Validator.create(Locale.US)).major
        )
        assertEquals(
                "M34",
                Components("M34 4AB", Validator.create(Locale.UK)).major
        )
    }

    @Test
    fun `Can get minor postcode`() {
        assertEquals(
                "6789",
                Components("12345 6789", Validator.create(Locale.US)).minor
        )
        assertEquals(
                "4AB",
                Components("M34 4AB", Validator.create(Locale.UK)).minor
        )
    }

    @Test
    fun `Can get major postcode from input without separator`() {
        assertEquals(
                "12345",
                Components("12345678", Validator.create(Locale("pt", "BR"), Options(EXCLUDE))).major
        )
        assertEquals(
                "M34",
                Components("M344AB", Validator.create(Locale.UK, Options(EXCLUDE))).major
        )
    }

    @Test
    fun `Can get minor postcode from input without separator`() {
        assertEquals(
                "6789",
                Components("12345678", Validator.create(Locale("pt", "BR"), Options(EXCLUDE))).minor
        )
        assertEquals(
                "4AB",
                Components("M344AB", Validator.create(Locale.UK, Options(EXCLUDE))).minor
        )
    }
}