package com.laundrapp.postcodes

import java.util.concurrent.ConcurrentHashMap

internal class Formatter private constructor(private val validator: Validator) {

    private val nonAlphanumeric = "[^a-zA-Z0-9]".toRegex()
    private val leadingNonAlphanumeric = "(^[^a-zA-Z0-9]+)".toRegex()
    private val trailingNonAlphanumeric = "([^a-zA-Z0-9]+\$)".toRegex()

    fun format(postcode: CursoredString): CursoredString {
        var outputCursorPosition = postcode.cursorPosition
        val postcodeUpperCase = postcode.string.toUpperCase()
        val postcodeTrimmedStart = postcodeUpperCase.replace(leadingNonAlphanumeric, "")
        outputCursorPosition -= Math.min(postcodeUpperCase.length - postcodeTrimmedStart.length, outputCursorPosition)
        val postcodeTrimmed = postcodeTrimmedStart.replace(trailingNonAlphanumeric, "")
        outputCursorPosition -= Math.max(0, outputCursorPosition - postcodeTrimmed.length)
        if (validator.partialValidate(postcodeTrimmed)) return CursoredString(postcodeTrimmed, outputCursorPosition)
        val postcodeStripped = postcodeTrimmed.replace(nonAlphanumeric, "")
        outputCursorPosition = postcodeTrimmed.substring(0, outputCursorPosition).replace(nonAlphanumeric, "").length
        if (validator.partialValidate(postcodeStripped)) return CursoredString(postcodeStripped, outputCursorPosition)

        val separatorLoc = findSeparatorLocation(postcodeStripped)
        if (separatorLoc != -1) {
            if (separatorLoc < outputCursorPosition) outputCursorPosition++

            val postcodeDashSeparated = postcodeStripped.insert("-", separatorLoc)
            if (validator.partialValidate(postcodeDashSeparated)) return CursoredString(postcodeDashSeparated, outputCursorPosition)

            val postcodeSpaceSeparated = postcodeStripped.insert(" ", separatorLoc)
            if (validator.partialValidate(postcodeSpaceSeparated)) return CursoredString(postcodeSpaceSeparated, outputCursorPosition)
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

    companion object {
        private val formatterMemo = ConcurrentHashMap<Validator, Formatter>()

        fun create(validator: Validator): Formatter {
            return formatterMemo[validator] ?: Formatter(validator).also {
                formatterMemo[validator] = it
            }
        }
    }

    class CouldNotFormatException : RuntimeException()

    private fun String.insert(string: String, location: Int): String {
        return substring(0, location) + string + substring(location)
    }
}