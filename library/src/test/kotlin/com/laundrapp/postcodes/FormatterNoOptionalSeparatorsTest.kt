package com.laundrapp.postcodes

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*
import com.laundrapp.postcodes.Options.*

class FormatterFixedOptionalSeparatorsTest {

    private val localeBrazil = Locale.forLanguageTag("pt-BR")

    @Test
    fun `Include optional separators formats to add separators`() {
        val formatterBrazil = Formatter(Validator.create(localeBrazil, Options(OptionalSeparator.INCLUDE)))
        assertEquals("12345-123", formatterBrazil.format(CursoredString("12345123", 0)).string)
        assertEquals("12345-1", formatterBrazil.format(CursoredString("123451", 0)).string)
        val formatterUK = Formatter(Validator.create(Locale.UK, Options(OptionalSeparator.INCLUDE)))
        assertEquals("M34 4AB", formatterUK.format((CursoredString("m344Ab", 0))).string)
        assertEquals("M34 4A", formatterUK.format((CursoredString("m344A", 0))).string)
    }

    @Test
    fun `Don't include optional separators formats to remove separators`() {
        val formatterBrazil = Formatter(Validator.create(localeBrazil, Options(OptionalSeparator.EXCLUDE)))
        assertEquals("12345123", formatterBrazil.format(CursoredString("12345-123", 0)).string)
        assertEquals("123451", formatterBrazil.format(CursoredString("12345-1", 0)).string)
        val formatterUK = Formatter(Validator.create(Locale.UK, Options(OptionalSeparator.EXCLUDE)))
        assertEquals("M344AB", formatterUK.format(CursoredString("M34 4AB", 0)).string)
        assertEquals("M344A", formatterUK.format(CursoredString("M34 4A", 0)).string)
    }

    @Test
    fun `Include optional separators formats to change incorrect separators`() {
        val formatterBrazil = Formatter(Validator.create(localeBrazil, Options(OptionalSeparator.INCLUDE)))
        assertEquals("12345-123", formatterBrazil.format(CursoredString("12345 123", 0)).string)
        assertEquals("12345-1", formatterBrazil.format(CursoredString("12345 1", 0)).string)
        val formatterUK = Formatter(Validator.create(Locale.UK, Options(OptionalSeparator.INCLUDE)))
        assertEquals("M34 4AB", formatterUK.format((CursoredString("m34-4Ab", 0))).string)
        assertEquals("M34 4A", formatterUK.format((CursoredString("m34-4A", 0))).string)
    }

    @Test
    fun `Don't include optional separators formats to remove incorrect separators`() {
        val formatterBrazil = Formatter(Validator.create(localeBrazil, Options(OptionalSeparator.EXCLUDE)))
        assertEquals("12345123", formatterBrazil.format(CursoredString("12345 123", 0)).string)
        assertEquals("123451", formatterBrazil.format(CursoredString("12345 1", 0)).string)
        val formatterUK = Formatter(Validator.create(Locale.UK, Options(OptionalSeparator.EXCLUDE)))
        assertEquals("M344AB", formatterUK.format(CursoredString("M34-4AB", 0)).string)
        assertEquals("M344A", formatterUK.format(CursoredString("M34-4A", 0)).string)
    }

}