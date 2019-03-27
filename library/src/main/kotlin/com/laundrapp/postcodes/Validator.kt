package com.laundrapp.postcodes

import com.laundrapp.postcodes.Options.OptionalSeparator.*
import com.laundrapp.postcodes.RegexRetriever.getLocaleRegex
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.regex.Matcher
import java.util.regex.Pattern

internal class Validator private constructor(locales: List<Locale>, options: Options) {
    private val localisedPattern: Pattern
    private val partialValidateMemo = ConcurrentHashMap<String, Boolean>()
    private val validateMemo = ConcurrentHashMap<String, Boolean>()

    init {
        val alteredRegex = locales
                .map { getLocaleRegex(it) }
                .map {
                    when (options.includeOptionalSeparators) {
                        INCLUDE -> it.replace("([\\- ])\\?".toRegex(), "$1")
                        EXCLUDE -> it.replace("([\\- ])\\?".toRegex(), "")
                        ACCEPT_EITHER -> it
                    }
                }
                .reduce { regex1, regex2 -> "(?:$regex1)|(?:$regex2)" }
        localisedPattern = Pattern.compile(alteredRegex)
    }

    fun validate(postcode: String): Boolean =
            validateMemo[postcode] ?: localisedPattern.matcher(postcode).matches().also {
                validateMemo[postcode] = it
            }

    fun partialValidate(postcode: String): Boolean =
            partialValidateMemo[postcode] ?: localisedPattern.matcher(postcode).partiallyMatches().also {
                partialValidateMemo[postcode] = it
            }

    private fun Matcher.partiallyMatches(): Boolean = this.matches() || this.hitEnd()

    companion object {
        private val validatorMemo = ConcurrentHashMap<Params, Validator>()

        fun create(locale: Locale, options: Options = Options(INCLUDE)): Validator =
                create(listOf(locale), options)

        fun create(locales: List<Locale>, options: Options = Options(INCLUDE)): Validator {
            val params = Params(locales, options)
            return validatorMemo[params] ?: Validator(locales, options).also {
                validatorMemo[params] = it
            }
        }

        private data class Params(val locales: List<Locale>, val options: Options)
    }
}
