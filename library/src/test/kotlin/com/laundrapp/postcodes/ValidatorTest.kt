package com.laundrapp.postcodes

import org.junit.Assert.*
import org.junit.Test

import java.lang.IllegalArgumentException
import java.util.*
import com.laundrapp.postcodes.Options.OptionalSeparator.*

class ValidatorTest {

    @Test(expected = IllegalArgumentException::class)
    fun `Validator constructor throws for invalid country code`() {
        Validator.create(Locale.forLanguageTag("ZZ-ZZ"))
    }

    @Test
    fun `Validator can be constructed for valid country code`() {
        Validator.create(Locale.UK)
    }

    @Test
    fun `validate returns true for valid full postcode`() {
        val validatorDE = Validator.create(Locale.GERMANY)
        assertTrue(validatorDE.validate("12345"))
        assertTrue(validatorDE.validate("99999"))
        assertTrue(validatorDE.validate("00000"))
    }

    @Test
    fun `validate returns false for valid partial postcodes`() {
        val validatorDE = Validator.create(Locale.GERMANY)
        assertFalse(validatorDE.validate("123"))
        assertFalse(validatorDE.validate("9999"))
        assertFalse(validatorDE.validate("78"))
    }

    @Test
    fun `validate returns false for invalid postcodes`() {
        val validatorDE = Validator.create(Locale.GERMANY)
        assertFalse(validatorDE.validate("123456"))
        assertFalse(validatorDE.validate("ABCDE"))
        assertFalse(validatorDE.validate("!£$%%^"))
        assertFalse(validatorDE.validate("\\d{5}"))
    }

    @Test
    fun `validate returns false for empty string`() {
        val validatorDE = Validator.create(Locale.GERMANY)
        assertFalse(validatorDE.validate(""))
    }

    @Test
    fun `partialValidate returns true for valid full postcode`() {
        val validatorUK = Validator.create(Locale.UK)
        assertTrue(validatorUK.partialValidate("E1 1JJ"))
        assertTrue(validatorUK.partialValidate("WC2H 9AH"))
        assertTrue(validatorUK.partialValidate("LD5A 5EB"))
    }

    @Test
    fun `partialValidate returns true for valid partial postcodes`() {
        val validatorUK = Validator.create(Locale.UK)
        assertTrue(validatorUK.partialValidate("E1 1"))
        assertTrue(validatorUK.partialValidate("E1 "))
        assertTrue(validatorUK.partialValidate("E"))
        assertTrue(validatorUK.partialValidate("WC2H 9"))
        assertTrue(validatorUK.partialValidate("WC2"))
        assertTrue(validatorUK.partialValidate(""))
        assertTrue(validatorUK.partialValidate("LD5A 5E"))
    }

    @Test
    fun `partialValidate returns false for invalid postcodes`() {
        val validatorUK = Validator.create(Locale.UK)
        assertFalse(validatorUK.partialValidate("LD5A 5EC"))
        assertFalse(validatorUK.partialValidate("LD5A 5CB"))
        assertFalse(validatorUK.partialValidate(" LD5A 5EB"))
        assertFalse(validatorUK.partialValidate("LD5 A"))
        assertFalse(validatorUK.partialValidate("!£$%%^"))
        assertFalse(validatorUK.partialValidate("\\d{5}"))
    }

    @Test
    fun `partialValidate returns true for empty string`() {
        val validatorDE = Validator.create(Locale.GERMANY)
        assertTrue(validatorDE.partialValidate(""))
    }

    @Test
    fun `Validator recycling`() {
        assertTrue(Validator.create(Locale.US) === Validator.create(Locale.US))
    }

    @Test
    fun `Validator recycling for optional params`() {
        assertTrue(Validator.create(Locale.US, Options(INCLUDE)) === Validator.create(Locale.US))
    }
}