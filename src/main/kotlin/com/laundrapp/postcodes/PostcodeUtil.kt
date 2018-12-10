package com.laundrapp.postcodes

import java.util.*

class PostcodeUtil(val locale: Locale) {
    constructor(): this(Locale.getDefault())

    /**
     * @return A formatted representation of the input postcode
     */
    fun format(postcode: String): String {
        throw NotImplementedError()
    }

    /**
     * @return True if and only if the postcode passes validation for the given country
     */
    fun isValidPostcode(postcode: String): Boolean {
        throw NotImplementedError()
    }

    /**
     * @return True if and only if the postcode could have characters added to make it a valid postcode
     */
    fun isValidPartialPostcode(postcode: String): Boolean {
        throw NotImplementedError()
    }
}