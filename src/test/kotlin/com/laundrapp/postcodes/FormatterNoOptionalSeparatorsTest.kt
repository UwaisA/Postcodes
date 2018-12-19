package com.laundrapp.postcodes

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class FormatterNoOptionalSeparatorsTest {

    private val localeBrazil = Locale.forLanguageTag("pt-BR")

    @Test
    fun `Include optional separators formats to add separators`() {
        val formatterBrazil = Formatter(Validator(localeBrazil, PostcodeUtil.Options(true)))
        assertEquals("12345-123", formatterBrazil.format("12345123"))
        assertEquals("12345-1", formatterBrazil.format("123451"))
        val formatterUK = Formatter(Validator(Locale.UK, PostcodeUtil.Options(true)))
        assertEquals("M34 4AB", formatterUK.format(("m344Ab")))
        assertEquals("M34 4A", formatterUK.format(("m344A")))
    }

    @Test
    fun `Don't include optional separators formats to remove separators`() {
        val formatterBrazil = Formatter(Validator(localeBrazil, PostcodeUtil.Options(false)))
        assertEquals("12345123", formatterBrazil.format("12345-123"))
        assertEquals("123451", formatterBrazil.format("12345-1"))
        val formatterUK = Formatter(Validator(Locale.UK, PostcodeUtil.Options(false)))
        assertEquals("M344AB", formatterUK.format("M34 4AB"))
        assertEquals("M344A", formatterUK.format("M34 4A"))
    }

    @Test
    fun `Include optional separators formats to change incorrect separators`() {
        val formatterBrazil = Formatter(Validator(localeBrazil, PostcodeUtil.Options(true)))
        assertEquals("12345-123", formatterBrazil.format("12345 123"))
        assertEquals("12345-1", formatterBrazil.format("12345 1"))
        val formatterUK = Formatter(Validator(Locale.UK, PostcodeUtil.Options(true)))
        assertEquals("M34 4AB", formatterUK.format(("m34-4Ab")))
        assertEquals("M34 4A", formatterUK.format(("m34-4A")))
    }

    @Test
    fun `Don't include optional separators formats to remove incorrect separators`() {
        val formatterBrazil = Formatter(Validator(localeBrazil, PostcodeUtil.Options(false)))
        assertEquals("12345123", formatterBrazil.format("12345 123"))
        assertEquals("123451", formatterBrazil.format("12345 1"))
        val formatterUK = Formatter(Validator(Locale.UK, PostcodeUtil.Options(false)))
        assertEquals("M344AB", formatterUK.format("M34-4AB"))
        assertEquals("M344A", formatterUK.format("M34-4A"))
    }

}