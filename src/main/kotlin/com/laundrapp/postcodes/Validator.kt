package com.laundrapp.postcodes

import com.laundrapp.postcodes.RegexRetriever.getLocaleRegex
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.regex.Matcher
import java.util.regex.Pattern

class Validator(locale: Locale) {
    private val localisedPattern = Pattern.compile(getLocaleRegex(locale))
    private val partialValidateMemo = ConcurrentHashMap<String, Boolean>()
    private val validateMemo = ConcurrentHashMap<String, Boolean>()

    fun validate(postcode: String): Boolean {
        return validateMemo[postcode] ?: localisedPattern.matcher(postcode).matches().also {
            validateMemo[postcode] = it
        }
    }

    fun partialValidate(postcode: String): Boolean {
        return partialValidateMemo[postcode] ?: localisedPattern.matcher(postcode).partiallyMatches().also {
            partialValidateMemo[postcode] = it
        }
    }
}

private fun Matcher.partiallyMatches(): Boolean {
    return this.matches() || this.hitEnd()
}
