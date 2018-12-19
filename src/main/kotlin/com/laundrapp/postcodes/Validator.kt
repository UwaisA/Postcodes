package com.laundrapp.postcodes

import com.laundrapp.postcodes.RegexRetriever.getLocaleRegex
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.regex.Matcher
import java.util.regex.Pattern

internal class Validator(locale: Locale, options: PostcodeUtil.Options = PostcodeUtil.Options(true)) {
    private val localisedPattern: Pattern
    private val partialValidateMemo = ConcurrentHashMap<String, Boolean>()
    private val validateMemo = ConcurrentHashMap<String, Boolean>()

    init {
        val localeRegex = getLocaleRegex(locale)
        val replaceWith = if (options.includeOptionalSeparators) "$1" else ""
        val localeRegexAltered = localeRegex.replace("([\\- ])\\?".toRegex(), replaceWith)
        localisedPattern = Pattern.compile(localeRegexAltered)
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
