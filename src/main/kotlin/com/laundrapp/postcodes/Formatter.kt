package com.laundrapp.postcodes

import java.util.*

class Formatter(locale: Locale) {

    private val nonAlphanumeric = "[^a-zA-Z0-9]".toRegex()
    private val validator = Validator(locale)
    private val separator = "-"

    fun format(postcode: String): String {
        val postcodeStripped = postcode.replace(nonAlphanumeric, "")
        if (validator.partialValidate(postcodeStripped)) return postcodeStripped

        var postcodeOutput = postcodeStripped
        var newSeparatorLoc = findSeparatorLocation(postcodeOutput)
        while (newSeparatorLoc != -1) {
            postcodeOutput = postcodeOutput.take(newSeparatorLoc) + separator + postcodeOutput.drop(newSeparatorLoc)
            if (validator.partialValidate(postcodeOutput)) {
                return postcodeOutput
            } else {
                newSeparatorLoc = findSeparatorLocation(postcodeOutput)
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

            val partialValidateMiddle = validator.partialValidate(postcode.take(middle))
            if (!partialValidateMiddle) {
                end = middle - 1
                continue
            }
            val partialValidateMiddlePlusOne = validator.partialValidate(postcode.take(middle + 1))

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