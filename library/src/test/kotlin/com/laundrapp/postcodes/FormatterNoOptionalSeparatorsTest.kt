package com.laundrapp.postcodes

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class FormatterNoOptionalSeparatorsTest {

    private val localeBrazil = Locale.forLanguageTag("pt-BR")

    @Test
    fun `Include optional separators formats to add separators`() {
        val formatterBrazil = Formatter(Validator(localeBrazil, PostcodeUtil.Options(true)))
        assertEquals(CursoredString("12345-123", 0), formatterBrazil.format(CursoredString("12345123", 0)))
        assertEquals(CursoredString("12345-1", 0), formatterBrazil.format(CursoredString("123451", 0)))
        val formatterUK = Formatter(Validator(Locale.UK, PostcodeUtil.Options(true)))
        assertEquals(CursoredString("M34 4AB", 0), formatterUK.format((CursoredString("m344Ab", 0))))
        assertEquals(CursoredString("M34 4A", 0), formatterUK.format((CursoredString("m344A", 0))))
    }

    @Test
    fun `Don't include optional separators formats to remove separators`() {
        val formatterBrazil = Formatter(Validator(localeBrazil, PostcodeUtil.Options(false)))
        assertEquals(CursoredString("12345123", 0), formatterBrazil.format(CursoredString("12345-123", 0)))
        assertEquals(CursoredString("123451", 0), formatterBrazil.format(CursoredString("12345-1", 0)))
        val formatterUK = Formatter(Validator(Locale.UK, PostcodeUtil.Options(false)))
        assertEquals(CursoredString("M344AB", 0), formatterUK.format(CursoredString("M34 4AB", 0)))
        assertEquals(CursoredString("M344A", 0), formatterUK.format(CursoredString("M34 4A", 0)))
    }

    @Test
    fun `Include optional separators formats to change incorrect separators`() {
        val formatterBrazil = Formatter(Validator(localeBrazil, PostcodeUtil.Options(true)))
        assertEquals(CursoredString("12345-123", 0), formatterBrazil.format(CursoredString("12345 123", 0)))
        assertEquals(CursoredString("12345-1", 0), formatterBrazil.format(CursoredString("12345 1", 0)))
        val formatterUK = Formatter(Validator(Locale.UK, PostcodeUtil.Options(true)))
        assertEquals(CursoredString("M34 4AB", 0), formatterUK.format((CursoredString("m34-4Ab", 0))))
        assertEquals(CursoredString("M34 4A", 0), formatterUK.format((CursoredString("m34-4A", 0))))
    }

    @Test
    fun `Don't include optional separators formats to remove incorrect separators`() {
        val formatterBrazil = Formatter(Validator(localeBrazil, PostcodeUtil.Options(false)))
        assertEquals(CursoredString("12345123", 0), formatterBrazil.format(CursoredString("12345 123", 0)))
        assertEquals(CursoredString("123451", 0), formatterBrazil.format(CursoredString("12345 1", 0)))
        val formatterUK = Formatter(Validator(Locale.UK, PostcodeUtil.Options(false)))
        assertEquals(CursoredString("M344AB", 0), formatterUK.format(CursoredString("M34-4AB", 0)))
        assertEquals(CursoredString("M344A", 0), formatterUK.format(CursoredString("M34-4A", 0)))
    }

}