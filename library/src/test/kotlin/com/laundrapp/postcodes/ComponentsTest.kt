package com.laundrapp.postcodes

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class ComponentsTest {

    @Test(expected = IllegalArgumentException::class)
    fun `IllegalArgumentException for invalid postcode`() {
        Components("123a", Locale.US)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `IllegalArgumentException for invalid postcode with many separators`() {
        Components("1  2   3", Locale.US)
    }

    @Test
    fun `Successful construction for valid postcode`() {
        Components("123", Locale.US)
        Components("12345-6789", Locale.US)
        Components("M34 4AB", Locale.UK)
        Components("M34 4AB", Locale.UK)
    }

    @Test
    fun `Can get postcode`() {
        assertEquals(
                "123",
                Components("123", Locale.US).postcode
        )
        assertEquals(
                "M34 4AB",
                Components("M34 4AB", Locale.UK).postcode
        )
    }

    @Test
    fun `Can get major postcode`() {
        assertEquals(
                "12345",
                Components("12345 123", Locale.US).major
        )
        assertEquals(
                "M34",
                Components("M34 4AB", Locale.UK).major
        )
    }

    @Test
    fun `Can get minor postcode`() {
        assertEquals(
                "6789",
                Components("12345 6789", Locale.US).minor
        )
        assertEquals(
                "4AB",
                Components("M34 4AB", Locale.UK).minor
        )
    }

    @Test
    fun `Can get major postcode from input without separator`() {
        assertEquals(
                "12345",
                Components("12345678", Locale("pt", "BR")).major
        )
        assertEquals(
                "M34",
                Components("M344AB", Locale.UK).major
        )
    }

    @Test
    fun `Can get minor postcode from input without separator`() {
        assertEquals(
                "678",
                Components("12345678", Locale("pt", "BR")).minor
        )
        assertEquals(
                "4AB",
                Components("M344AB", Locale.UK).minor
        )
    }
}