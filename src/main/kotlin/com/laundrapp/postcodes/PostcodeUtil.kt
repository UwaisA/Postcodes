package com.laundrapp.postcodes

import java.util.*

class PostcodeUtil(locale: Locale = Locale.getDefault()) {

    private val validator = Validator(locale)
    private val formatter = Formatter(locale)

    /**
     * @return A formatted representation of the input postcode
     */
    fun format(postcode: String): PostcodeResult {
        return try {
            PostcodeResult(formatter.format(postcode), true)
        } catch (ex: Formatter.CouldNotFormatException) {
            PostcodeResult(postcode, false)
        }
    }

    /**
     * @return True if and only if the postcode passes validation for the given country
     */
    fun isValidPostcode(postcode: String): Boolean = validator.validate(postcode)

    /**
     * @return True if and only if the postcode could have characters added to make it a valid postcode
     */
    fun isValidPartialPostcode(postcode: String): Boolean = validator.partialValidate(postcode)
}