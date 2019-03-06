package com.laundrapp.postcodes

import java.io.InputStream
import java.util.*


object RegexRetriever {
    private const val regexesFileName = "/postcode-regexes.properties"
    private val regexMap: Properties = Properties()

    init {
        regexMap.load(getResourceAsStream(regexesFileName))
    }

    fun getLocaleRegex(locale: Locale): String {
        val countryKey = "${locale.country}.regexp"
        checkCountryCodeValidity(countryKey)
        return regexMap.getProperty(countryKey)
    }

    private fun checkCountryCodeValidity(countryKey: String) {
        if (!regexMap.containsKey(countryKey)) {
            throw IllegalArgumentException("No postcode rules found for country code: $countryKey")
        }
    }

    private fun getResourceAsStream(fileName: String): InputStream {
        return RegexRetriever::class.java.getResourceAsStream(fileName)
    }
}