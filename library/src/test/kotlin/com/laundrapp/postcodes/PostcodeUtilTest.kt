package com.laundrapp.postcodes

import org.junit.Test

import org.junit.Assert.*
import java.util.*

class PostcodeUtilTest {

    @Test
    fun `Format formatted response`() {
        assertEquals(PostcodeResult("12345-6", true),
                PostcodeUtil(Locale.US).format("123456"))
        assertEquals(PostcodeResult("GIR 0A", true),
                PostcodeUtil(Locale.UK).format("GiR 0a"))
    }

    @Test
    fun `Format unformatted response`() {
        assertEquals(PostcodeResult("1234567890", false),
                PostcodeUtil(Locale.US).format("1234567890"))
        assertEquals(PostcodeResult("GiR 0aaa", false),
                PostcodeUtil(Locale.UK).format("GiR 0aaa"))
    }

    @Test
    fun `isValidPostcode returns true for valid postcodes`() {
        assertTrue(PostcodeUtil(Locale.UK).isValidPostcode("W1A 4ZZ"))
        assertTrue(PostcodeUtil(Locale.US).isValidPostcode("01234-5678"))
    }

    @Test
    fun `isValidPostcode returns false for invalid postcodes`() {
        assertFalse(PostcodeUtil(Locale.UK).isValidPostcode("W1A4ZZZ"))
        assertFalse(PostcodeUtil(Locale.US).isValidPostcode("A"))
    }

    @Test
    fun `isValidPostcode returns false for partial postcodes`() {
        assertFalse(PostcodeUtil(Locale.UK).isValidPostcode("W1A 4Z"))
        assertFalse(PostcodeUtil(Locale.US).isValidPostcode("01234-5"))
    }

    @Test
    fun `isValidPartialPostcode returns true for valid postcode`() {
        assertTrue(PostcodeUtil(Locale.UK).isValidPartialPostcode("W1A 4ZZ"))
        assertTrue(PostcodeUtil(Locale.US).isValidPartialPostcode("01234-5678"))
    }

    @Test
    fun `isValidPartialPostcode returns false for invalid postcodes`() {
        assertFalse(PostcodeUtil(Locale.UK).isValidPartialPostcode("W1A4ZZZ"))
        assertFalse(PostcodeUtil(Locale.US).isValidPartialPostcode("A"))
    }

    @Test
    fun `isValidPartialPostcode returns true for partial postcodes`() {
        assertTrue(PostcodeUtil(Locale.UK).isValidPartialPostcode("W1A 4Z"))
        assertTrue(PostcodeUtil(Locale.US).isValidPartialPostcode("01234-5"))
    }
}