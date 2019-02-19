package com.laundrapp.postcodes

import java.util.*

class PostcodeUtil
/**
 * @param locale This country of this locale is used to determine which postcode format to use
 * @param options Allows customisation of the formatting of postcodes
 * @throws IllegalArgumentException This can be thrown if the provided country does not support postcodes. If
 * relying on the default locale then catching this exception is recommended
 */ @JvmOverloads constructor(locale: Locale = Locale.getDefault(), options: Options = Options(true)) {

    private val validator = Validator(locale, options)
    private val formatter = Formatter(validator)

    /**
     * @throws IndexOutOfBoundsException {@code cursorPosition} is outside the length of string {@code postcode}
     * @return A formatted representation of the input postcode
     */
    fun format(postcode: String, cursorPosition: Int = 0): PostcodeResult {
        if (cursorPosition < 0 || cursorPosition > postcode.length) {
            throw IndexOutOfBoundsException("cursorPosition: \"$cursorPosition\" invalid for postcode: \"$postcode\"")
        }
        return try {
            val formattedCursoredString = formatter.format(CursoredString(postcode, cursorPosition))
            PostcodeResult(formattedCursoredString.string, formattedCursoredString.cursorPosition, true)
        } catch (ex: Formatter.CouldNotFormatException) {
            PostcodeResult(postcode, cursorPosition, false)
        }
    }

    /**
     * @param postcode The string to be validated
     * @return True if and only if the postcode passes validation for the given country
     */
    fun isValidPostcode(postcode: String): Boolean = validator.validate(postcode)

    /**
     * @param postcode The string to be validated
     * @return true if and only if the postcode could have characters appended to make it a valid postcode
     */
    fun isValidPartialPostcode(postcode: String): Boolean = validator.partialValidate(postcode)

    class Options(val includeOptionalSeparators: Boolean)

}