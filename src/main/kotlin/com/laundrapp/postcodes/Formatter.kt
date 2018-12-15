package com.laundrapp.postcodes

import java.util.*

class Formatter(locale: Locale) {

    private val nonAlphanumeric = "[^a-zA-Z0-9]".toRegex()
    private val leadingTrailingNonAlphanumeric = "(^[^a-zA-Z0-9]+)|([^a-zA-Z0-9]+$)".toRegex()
    private val validator = Validator(locale)
    private val separator = "-"

    fun format(postcode: String): String {
        val postcodeTrimmed = postcode.replace(leadingTrailingNonAlphanumeric, "")
        if (validator.partialValidate(postcodeTrimmed)) return postcodeTrimmed
        val postcodeStripped = postcode.replace(nonAlphanumeric, "")
        if (validator.partialValidate(postcodeStripped)) return postcodeStripped

        val newSeparatorLoc = findSeparatorLocation(postcodeStripped)
        if (newSeparatorLoc != -1) {
            val postcodeSeparated = postcodeStripped.substring(0, newSeparatorLoc) + separator + postcodeStripped.substring(newSeparatorLoc)
            if (validator.partialValidate(postcodeSeparated)) {
                return postcodeSeparated
            }
        }

        throw CouldNotFormatException()
    }

    /**
     * Assuming there is a need for a separator, find it's location
     * @return The index of the character after which a separator needs to be added
     *          or -1 if no separator position could be found
     */
    private fun findSeparatorLocation(postcode: String): Int {
        var start = 0
        var end = postcode.length - 1

        while (start <= end) {
            val middle = (start + end) / 2

            val partialValidateMiddle = validator.partialValidate(postcode.substring(0, middle))
            if (!partialValidateMiddle) {
                end = middle - 1
                continue
            }
            val partialValidateMiddlePlusOne = validator.partialValidate(postcode.substring(0, middle + 1))

            if (partialValidateMiddlePlusOne) {
                start = middle + 1
                continue
            }

            return middle
        }
        return -1
    }

    class CouldNotFormatException : RuntimeException()
}