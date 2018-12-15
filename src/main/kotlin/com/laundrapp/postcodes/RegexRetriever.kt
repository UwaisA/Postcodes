package com.laundrapp.postcodes

import java.io.File
import java.util.*


object RegexRetriever {
    internal const val regexesFileLocation = "postcode-regexes.properties"
    private val regexMap: Properties = Properties()

    init {
        regexMap.load(File(regexesFileLocation).inputStream())
    }

    fun getLocaleRegex(locale: Locale): String {
        val countryKey = "${locale.country}.regexp"
        checkCountryCodeValidity(countryKey)
        return regexMap.getProperty(countryKey)
    }

    private fun checkCountryCodeValidity(countryKey: String) {
        if (!regexMap.containsKey(countryKey)) {
            throw IllegalStateException("No postcode rules found for country code: $countryKey")
        }
    }
}