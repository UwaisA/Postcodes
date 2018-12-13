package com.laundrapp.postcodes

import java.util.*

class Formatter(locale: Locale) {

    private val nonAlphanumeric = "[^a-zA-Z0-9]".toRegex()
    private val validator = Validator(locale)

    fun format(postcode: String): String {
        if (validator.partialValidate(postcode)) return postcode
        val postcodeStripped = postcode.replace(nonAlphanumeric, "")
        if (validator.partialValidate(postcodeStripped)) return postcodeStripped

        throw CouldNotFormatException()
    }

    class CouldNotFormatException : RuntimeException()
}