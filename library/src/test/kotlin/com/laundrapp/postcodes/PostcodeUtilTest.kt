package com.laundrapp.postcodes

import org.junit.Test

import org.junit.Assert.*
import java.util.*

class PostcodeUtilTest {

    @Test(expected = IndexOutOfBoundsException::class)
    fun `Format cursorPosition less than 0`() {
        PostcodeUtil(Locale.UK).format("E1 1J", -1)
    }

    @Test
    fun `Format formatted response`() {
        val postcode1 = "12345-6"
        assertEquals(PostcodeResult(postcode1, 0, true, Components(postcode1, Locale.US)),
                PostcodeUtil(Locale.US).format("123456"))
        val postcode2 = "GIR 0A"
        assertEquals(PostcodeResult(postcode2, 0, true, Components(postcode2, Locale.UK)),
                PostcodeUtil(Locale.UK).format("GiR 0a"))
    }

    @Test
    fun `Format unformatted response`() {
        assertEquals(PostcodeResult("1234567890", 0, false, null),
                PostcodeUtil(Locale.US).format("1234567890"))
        assertEquals(PostcodeResult("GiR 0aaa", 0, false, null),
                PostcodeUtil(Locale.UK).format("GiR 0aaa"))
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun `Format cursorPosition more than postcode length`() {
        PostcodeUtil(Locale.UK).format("E1 1J", 6)
    }

    @Test
    fun `Format cursorPosition is moved`() {
        assertEquals(5, PostcodeUtil(Locale.US).format("12345####", 9).cursorPosition)
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