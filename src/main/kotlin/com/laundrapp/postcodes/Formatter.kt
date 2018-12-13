package com.laundrapp.postcodes

import java.util.*

class Formatter(private val locale: Locale) {

    fun format(postcode: String): String {
        val validator = Validator(locale)

        if (validator.partialValidate(postcode)) return postcode

        for (i in 0..postcode.length) {
            postcode.take(i)
            validator.partialValidate(postcode)
        }
        throw CouldNotFormatException()
    }

    class CouldNotFormatException : RuntimeException()
}