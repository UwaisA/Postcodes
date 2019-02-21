package com.laundrapp.postcodes

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*
import com.laundrapp.postcodes.Options.*

class FormatterOptionalSeparatorsTest {

    private val localeBrazil = Locale.forLanguageTag("pt-BR")

    @Test
    fun `Accept either optional separators formats to leave out separators that have been missed`() {
        val formatterBrazil = Formatter(Validator(localeBrazil, Options(OptionalSeparator.ACCEPT_EITHER)))
        assertEquals("12345123", formatterBrazil.format(CursoredString("12345123", 0)).string)
        assertEquals("123451", formatterBrazil.format(CursoredString("123451", 0)).string)
        val formatterUK = Formatter(Validator(Locale.UK, Options(OptionalSeparator.ACCEPT_EITHER)))
        assertEquals("M344AB", formatterUK.format((CursoredString("m344Ab", 0))).string)
        assertEquals("M344A", formatterUK.format((CursoredString("m344A", 0))).string)
    }

    @Test
    fun `Accept either optional separators formats to leave in separators that have been added`() {
        val formatterBrazil = Formatter(Validator(localeBrazil, Options(OptionalSeparator.ACCEPT_EITHER)))
        assertEquals("12345-123", formatterBrazil.format(CursoredString("12345-123", 0)).string)
        assertEquals("12345-1", formatterBrazil.format(CursoredString("12345-1", 0)).string)
        val formatterUK = Formatter(Validator(Locale.UK, Options(OptionalSeparator.ACCEPT_EITHER)))
        assertEquals("M34 4AB", formatterUK.format(CursoredString("M34 4AB", 0)).string)
        assertEquals("M34 4A", formatterUK.format(CursoredString("M34 4A", 0)).string)
    }

    @Test
    fun `Accept either optional separators formats to change incorrect separators`() {
        val formatterBrazil = Formatter(Validator(localeBrazil, Options(OptionalSeparator.ACCEPT_EITHER)))
        assertEquals("12345123", formatterBrazil.format(CursoredString("12345 123", 0)).string)
        assertEquals("123451", formatterBrazil.format(CursoredString("12345 1", 0)).string)
        val formatterUK = Formatter(Validator(Locale.UK, Options(OptionalSeparator.ACCEPT_EITHER)))
        assertEquals("M344AB", formatterUK.format((CursoredString("m34-4Ab", 0))).string)
        assertEquals("M344A", formatterUK.format((CursoredString("m34-4A", 0))).string)
    }
}