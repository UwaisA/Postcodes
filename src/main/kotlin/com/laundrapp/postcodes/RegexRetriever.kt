package com.laundrapp.postcodes

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.util.*


object RegexRetriever {
    internal const val regexesFileLocation = "postcode-regexes.json"
    private val typeToken = object: TypeToken<Map<String, String>>(){}.type;

    val regexMap: Map<String, String> = Gson().fromJson(File(regexesFileLocation).readText(), typeToken)

    fun getLocaleRegex(locale: Locale): String {
        checkCountryCodeValidity(locale.country)
        return regexMap.getValue(locale.country)
    }

    private fun checkCountryCodeValidity(countryCode: String) {
        if (!regexMap.containsKey(countryCode)) {
            throw IllegalStateException("No postcode rules found for country code: $countryCode")
        }
    }
}