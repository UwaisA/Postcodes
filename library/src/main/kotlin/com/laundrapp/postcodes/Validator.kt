package com.laundrapp.postcodes

import com.laundrapp.postcodes.Options.OptionalSeparator.*
import com.laundrapp.postcodes.RegexRetriever.getLocaleRegex
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.regex.Matcher
import java.util.regex.Pattern

internal class Validator(locale: Locale, options: Options = Options(INCLUDE)) {
    private val localisedPattern: Pattern
    private val partialValidateMemo = ConcurrentHashMap<String, Boolean>()
    private val validateMemo = ConcurrentHashMap<String, Boolean>()

    init {
        val localeRegex = getLocaleRegex(locale)
        val alteredRegex = when (options.includeOptionalSeparators) {
            INCLUDE -> localeRegex.replace("([\\- ])\\?".toRegex(), "$1")
            EXCLUDE -> localeRegex.replace("([\\- ])\\?".toRegex(), "")
            ACCEPT_EITHER -> localeRegex
        }
        localisedPattern = Pattern.compile(alteredRegex)
    }

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

    private fun Matcher.partiallyMatches(): Boolean {
        return this.matches() || this.hitEnd()
    }
}
