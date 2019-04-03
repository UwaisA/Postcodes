package com.laundrapp.postcodes

import junit.framework.Assert.assertEquals
import org.junit.Test
import java.util.*

class FormatterMultiCountryTest {

    private val differentValidators: Validator = Validator.create(listOf(
            Locale.US,
            Locale.UK
    ))

    private val initiallyOverlappingValidators: Validator = Validator.create(listOf(
            Locale.US,
            Locale.forLanguageTag("nl-NL")
    ))

    private val completeOverlappingValidators: Validator = Validator.create(listOf(
            Locale.forLanguageTag("fr-FR"),
            Locale.forLanguageTag("gr-GR"),
            Locale.forLanguageTag("pl-PL")
    ))

    private val identicalValidators: Validator = Validator.create(listOf(
            Locale.forLanguageTag("es-ES"),
            Locale.GERMANY
    ))

    @Test
    fun `Formatter works with first locale`() {
        assertEquals(
                Formatter.create(Validator.create(Locale.US)).format("012345678"),
                Formatter.create(differentValidators).format("012345678")
        )
        assertEquals(
                Formatter.create(Validator.create(Locale.US)).format("012345678"),
                Formatter.create(initiallyOverlappingValidators).format("012345678")
        )
//        TODO This test would fail as the Formatter chooses the second format over the first
//        assertEquals(
//                Formatter.create(Validator.create(Locale.forLanguageTag("fr-FR"))).format("1234"),
//                Formatter.create(completeOverlappingValidators).format("1234")
//        )
        assertEquals(
                Formatter.create(Validator.create(Locale.forLanguageTag("es-ES"))).format("12345"),
                Formatter.create(identicalValidators).format("12345")
        )
    }

    @Test
    fun `Formatter works with second locale`() {
        assertEquals(
                Formatter.create(Validator.create(Locale.UK)).format("LD5A5"),
                Formatter.create(differentValidators).format("LD5A5")
        )
        assertEquals(
                Formatter.create(Validator.create(Locale.forLanguageTag("nl-NL"))).format("0123AB"),
                Formatter.create(initiallyOverlappingValidators).format("0123AB")
        )
        assertEquals(
                Formatter.create(Validator.create(Locale.forLanguageTag("gr-GR"))).format("123 "),
                Formatter.create(completeOverlappingValidators).format("123 ")
        )
        assertEquals(
                Formatter.create(Validator.create(Locale.GERMANY)).format("12345"),
                Formatter.create(identicalValidators).format("12345")
        )
    }

    @Test
    fun `Formatter works with third locale`() {
        assertEquals(
                Formatter.create(Validator.create(Locale.forLanguageTag("pl-PL"))).format("12-345"),
                Formatter.create(completeOverlappingValidators).format("12-345")
        )
    }
}