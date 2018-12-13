package com.laundrapp.postcodes

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.util.regex.Pattern

const val regexesFileLocation = "postcode-regexes.json"
private val typeToken = object: TypeToken<Map<String, String>>(){}.type;
val regexes: Map<String, String> = Gson().fromJson(File(regexesFileLocation).readText(), typeToken)

fun validate(countryCode: String, postcode: String): Boolean {
    checkCountryCodeValidity(countryCode)
    return regexes.getValue(countryCode).toRegex().matches(postcode)
}

fun partialValidate(countryCode: String, postcode: String): Boolean {
    checkCountryCodeValidity(countryCode)
    val matcher = Pattern.compile(regexes[countryCode]).matcher(postcode)
    return matcher.matches() || matcher.hitEnd()
}

private fun checkCountryCodeValidity(countryCode: String) {
    if (!regexes.containsKey(countryCode)) {
        throw IllegalStateException("No postcode rules found for country code: $countryCode")
    }
}
