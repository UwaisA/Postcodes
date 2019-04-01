package com.laundrapp.postcodes

import junit.framework.Assert.assertTrue
import org.junit.Test
import java.util.*

class ValidatorMultiCountryTest {

    private val differentValidators: Validator = Validator.create(listOf(
            Locale.US,
            Locale.UK
    ), Options(Options.OptionalSeparator.ACCEPT_EITHER))

    private val initiallyOverlappingValidators: Validator = Validator.create(listOf(
            Locale.US,
            Locale.forLanguageTag("nl-NL")
    ), Options(Options.OptionalSeparator.ACCEPT_EITHER))

    private val completeOverlappingValidators: Validator = Validator.create(listOf(
            Locale.forLanguageTag("fr-FR"),
            Locale.forLanguageTag("gr-GR"),
            Locale.forLanguageTag("pl-PL")
    ), Options(Options.OptionalSeparator.ACCEPT_EITHER))

    private val identicalValidators: Validator = Validator.create(listOf(
            Locale.forLanguageTag("es-ES"),
            Locale.GERMANY
    ), Options(Options.OptionalSeparator.ACCEPT_EITHER))

    @Test
    fun `Validation works with first validator`() {
        assertTrue(differentValidators.validate("01234-5678"))
        assertTrue(initiallyOverlappingValidators.validate("01234-5678"))
        assertTrue(completeOverlappingValidators.validate("123 45"))
        assertTrue(identicalValidators.validate("12345"))
    }

    @Test
    fun `Validation works with second validator`() {
        assertTrue(differentValidators.validate("LD5A 5EB"))
        assertTrue(initiallyOverlappingValidators.validate("0123 AB"))
        assertTrue(completeOverlappingValidators.validate("123 45"))
        assertTrue(identicalValidators.validate("12345"))
    }

    @Test
    fun `Validation works with third validator`() {
        assertTrue(completeOverlappingValidators.validate("12-345"))
    }

    @Test
    fun `Partial validation works with first validator`() {
        assertTrue(differentValidators.partialValidate("012"))
        assertTrue(initiallyOverlappingValidators.partialValidate("01234"))
        assertTrue(completeOverlappingValidators.partialValidate("123 4"))
        assertTrue(identicalValidators.partialValidate("1234"))
    }

    @Test
    fun `Partial validation works with second validator`() {
        assertTrue(differentValidators.partialValidate("LD5A 5"))
        assertTrue(initiallyOverlappingValidators.partialValidate("0123 A"))
        assertTrue(completeOverlappingValidators.partialValidate("123 "))
        assertTrue(identicalValidators.partialValidate("123"))
    }

    @Test
    fun `Partial validation works with third validator`() {
        assertTrue(completeOverlappingValidators.partialValidate("12-3"))
    }

}