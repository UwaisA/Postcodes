package com.laundrapp.postcodes

import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import java.util.regex.Pattern

class Validator(locale: Locale) {
    private val localisedRegex = RegexRetriever.getLocaleRegex(locale)
    private val localisedPattern = lazy {
        Pattern.compile(localisedRegex)
    }
    private val partialValidateMemo: ConcurrentMap<String, Boolean> = ConcurrentHashMap()
    private val validateMemo: ConcurrentMap<String, Boolean> = ConcurrentHashMap()

    fun validate(postcode: String): Boolean {
        if (!validateMemo.containsKey(postcode)) {
            validateMemo[postcode] = localisedRegex.toRegex().matches(postcode)
        }

        return validateMemo.getValue(postcode)
    }

    fun partialValidate(postcode: String): Boolean {
        if (!partialValidateMemo.containsKey(postcode)) {
            val matcher = localisedPattern.value.matcher(postcode)
            partialValidateMemo[postcode] = matcher.matches() || matcher.hitEnd()
        }

        return partialValidateMemo.getValue(postcode)
    }
}
