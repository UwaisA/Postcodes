package com.laundrapp.postcodes

import com.google.gson.JsonParser
import java.io.File
import java.net.URL
import java.util.*

private const val baseURL = "http://i18napis.appspot.com/address"

fun main(args: Array<String>) {

    val jsonString = URL("$baseURL/data").readText()
    val countries = jsonString.getAtJsonPath("countries")?.split('~')

    val postcodeProperties = countries?.map {
        it to getPostcodeRegex(it)
    }?.toMap()?.filterValues {
        it != null
    }?.toProperties()

    postcodeProperties?.store(File(RegexRetriever.regexesFileLocation).outputStream(), "")
}

private fun getPostcodeRegex(countryCode: String): String? {
    println("Getting for $countryCode")
    val countryJsonString = URL("$baseURL/data/$countryCode").readText()
    return countryJsonString.getAtJsonPath("zip")
}

private fun String.getAtJsonPath (vararg path: String): String? {
    var jsonObject = JsonParser()
            .parse(this)
            .asJsonObject
    for (pathElement in path.dropLast(1)) {
        jsonObject = jsonObject.getAsJsonObject(pathElement)
    }
    return jsonObject.getAsJsonPrimitive(path.last())?.asString
}

private fun Map<*, *>.toProperties(): Properties {
    val properties = Properties()
    forEach {
        (key, value) -> properties["$key.regexp"] = value.toString()
    }
    return properties
}
