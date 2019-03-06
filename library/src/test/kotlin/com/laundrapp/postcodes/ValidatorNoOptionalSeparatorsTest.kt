package com.laundrapp.postcodes

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.*
import com.laundrapp.postcodes.Options.*

class ValidatorNoOptionalSeparatorsTest {

    private val localeBrazil = Locale.forLanguageTag("pt-BR")

    @Test
    fun `Constructor with options functions`() {
        Validator.create(localeBrazil, Options(OptionalSeparator.EXCLUDE))
        Validator.create(localeBrazil, Options(OptionalSeparator.INCLUDE))
    }

    @Test
    fun `Include optional separators validates positively as expected`() {
        val validatorBrazil = Validator.create(localeBrazil, Options(OptionalSeparator.INCLUDE))
        assertTrue(validatorBrazil.validate("12345-123"))
        assertTrue(validatorBrazil.partialValidate("12345-1"))
        val validatorUK = Validator.create(Locale.UK, Options(OptionalSeparator.INCLUDE))
        assertTrue(validatorUK.validate(("M34 4AB")))
        assertTrue(validatorUK.partialValidate(("M34 4A")))
    }

    @Test
    fun `Include optional separators validates negatively as expected`() {
        val validatorBrazil = Validator.create(localeBrazil, Options(OptionalSeparator.INCLUDE))
        assertFalse(validatorBrazil.validate("12345123"))
        assertFalse(validatorBrazil.partialValidate("123451"))
        val validatorUK = Validator.create(Locale.UK, Options(OptionalSeparator.INCLUDE))
        assertFalse(validatorUK.validate("M344AB"))
        assertFalse(validatorUK.partialValidate("M344A"))
    }

    @Test
    fun `Don't include optional separators validates positively as expected`() {
        val validatorBrazil = Validator.create(localeBrazil, Options(OptionalSeparator.EXCLUDE))
        assertTrue(validatorBrazil.validate("12345123"))
        assertTrue(validatorBrazil.partialValidate("123451"))
        val validatorUK = Validator.create(Locale.UK, Options(OptionalSeparator.EXCLUDE))
        assertTrue(validatorUK.validate("M344AB"))
        assertTrue(validatorUK.partialValidate("M344A"))
    }

    @Test
    fun `Don't include optional separators validates negatively as expected`() {
        val validatorBrazil = Validator.create(localeBrazil, Options(OptionalSeparator.EXCLUDE))
        assertFalse(validatorBrazil.validate("12345-123"))
        assertFalse(validatorBrazil.partialValidate("12345-1"))
        val validatorUK = Validator.create(Locale.UK, Options(OptionalSeparator.EXCLUDE))
        assertFalse(validatorUK.validate("M34 4AB"))
        assertFalse(validatorUK.partialValidate("M34 4A"))
    }

}