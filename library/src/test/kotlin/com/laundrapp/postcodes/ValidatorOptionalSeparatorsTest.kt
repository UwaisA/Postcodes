package com.laundrapp.postcodes

import org.junit.Assert.*
import org.junit.Test
import java.util.*
import com.laundrapp.postcodes.Options.*

class ValidatorOptionalSeparatorsTest {

    private val localeBrazil = Locale.forLanguageTag("pt-BR")

    @Test
    fun `Constructor with options functions`() {
        Validator(localeBrazil, Options(OptionalSeparator.ACCEPT_EITHER))
    }

    @Test
    fun `Accept either optional separators validates positively as expected for included separators`() {
        val validatorBrazil = Validator(localeBrazil, Options(OptionalSeparator.ACCEPT_EITHER))
        assertTrue(validatorBrazil.validate("12345-123"))
        assertTrue(validatorBrazil.partialValidate("12345-1"))
        val validatorUK = Validator(Locale.UK, Options(OptionalSeparator.ACCEPT_EITHER))
        assertTrue(validatorUK.validate(("M34 4AB")))
        assertTrue(validatorUK.partialValidate(("M34 4A")))
    }

    @Test
    fun `Accept either optional separators validates positively as expected for excluded separators`() {
        val validatorBrazil = Validator(localeBrazil, Options(OptionalSeparator.ACCEPT_EITHER))
        assertTrue(validatorBrazil.validate("12345123"))
        assertTrue(validatorBrazil.partialValidate("123451"))
        val validatorUK = Validator(Locale.UK, Options(OptionalSeparator.ACCEPT_EITHER))
        assertTrue(validatorUK.validate(("M344AB")))
        assertTrue(validatorUK.partialValidate(("M344A")))
    }
}