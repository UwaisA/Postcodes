package com.laundrapp.postcodes

import java.util.*

class Formatter(locale: Locale) {

    private val nonAlphanumeric = "[^a-zA-Z0-9]".toRegex()
    private val validator = Validator(locale)
    private val separator = "-"

    fun format(postcode: String): String {
        val postcodeStripped = postcode.replace(nonAlphanumeric, "")
        if (validator.partialValidate(postcodeStripped)) return postcodeStripped

        return formatFrom(postcodeStripped, 1)
    }

    private fun formatFrom(postcode: String, startingChar: Int = 0): String {
        if (startingChar > postcode.length) {
            return postcode
        }

        if (validator.partialValidate(postcode.take(startingChar))) {
            return formatFrom(postcode, startingChar + 1)
        }

        val separatedPostcode = postcode.take(startingChar-1) + separator
        if (validator.partialValidate(separatedPostcode)) {
            return formatFrom(separatedPostcode + postcode.drop(startingChar-1), startingChar)
        }

        throw CouldNotFormatException()
    }

    class CouldNotFormatException : RuntimeException()
}