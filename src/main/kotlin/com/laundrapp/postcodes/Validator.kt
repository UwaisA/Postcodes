package com.laundrapp.postcodes

import java.util.*
import java.util.regex.Pattern

class Validator(locale: Locale) {
    private val localisedRegex = RegexRetriever.getLocaleRegex(locale)
    private val localisedPattern = lazy {
        Pattern.compile(localisedRegex)
    }

    fun validate(postcode: String): Boolean {
        return localisedRegex.toRegex().matches(postcode)
    }

    fun partialValidate(postcode: String): Boolean {
        val matcher = localisedPattern.value.matcher(postcode)
        return matcher.matches() || matcher.hitEnd()
    }
}
