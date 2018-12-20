package com.laundrapp.postcodes

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.*

class ValidatorNoOptionalSeparatorsTest {

    private val localeBrazil = Locale.forLanguageTag("pt-BR")

    @Test
    fun `Constructor with options functions`() {
        Validator(localeBrazil, PostcodeUtil.Options(false))
    }

    @Test
    fun `Include optional separators validates positively as expected`() {
        val validatorBrazil = Validator(localeBrazil, PostcodeUtil.Options(true))
        assertTrue(validatorBrazil.validate("12345-123"))
        assertTrue(validatorBrazil.partialValidate("12345-1"))
        val validatorUK = Validator(Locale.UK, PostcodeUtil.Options(true))
        assertTrue(validatorUK.validate(("M34 4AB")))
        assertTrue(validatorUK.partialValidate(("M34 4A")))
    }

    @Test
    fun `Include optional separators validates negatively as expected`() {
        val validatorBrazil = Validator(localeBrazil, PostcodeUtil.Options(true))
        assertFalse(validatorBrazil.validate("12345123"))
        assertFalse(validatorBrazil.partialValidate("123451"))
        val validatorUK = Validator(Locale.UK, PostcodeUtil.Options(true))
        assertFalse(validatorUK.validate("M344AB"))
        assertFalse(validatorUK.partialValidate("M344A"))
    }

    @Test
    fun `Don't include optional separators validates positively as expected`() {
        val validatorBrazil = Validator(localeBrazil, PostcodeUtil.Options(false))
        assertTrue(validatorBrazil.validate("12345123"))
        assertTrue(validatorBrazil.partialValidate("123451"))
        val validatorUK = Validator(Locale.UK, PostcodeUtil.Options(false))
        assertTrue(validatorUK.validate("M344AB"))
        assertTrue(validatorUK.partialValidate("M344A"))
    }

    @Test
    fun `Don't include optional separators validates negatively as expected`() {
        val validatorBrazil = Validator(localeBrazil, PostcodeUtil.Options(false))
        assertFalse(validatorBrazil.validate("12345-123"))
        assertFalse(validatorBrazil.partialValidate("12345-1"))
        val validatorUK = Validator(Locale.UK, PostcodeUtil.Options(false))
        assertFalse(validatorUK.validate("M34 4AB"))
        assertFalse(validatorUK.partialValidate("M34 4A"))
    }

}