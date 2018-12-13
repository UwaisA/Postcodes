package com.laundrapp.postcodes

import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import java.io.File
import java.net.URL

private const val baseURL = "http://i18napis.appspot.com/address"

fun main(args: Array<String>) {

    val jsonString = URL("$baseURL/data").readText()
    val countries = jsonString.getAtJsonPath("countries")?.split('~')

    val postcodeJsonObject = countries!!.map {
        it to getPostcodeRegex(it)
    }.toMap().filterValues {
        it != null
    }.toJsonObject()

    val gson = GsonBuilder().setPrettyPrinting().create()

    File(regexesFileLocation).writeText(gson.toJson(postcodeJsonObject))
}

private fun getPostcodeRegex(it: String): String? {
    val countryJsonString = URL("$baseURL/data/$it").readText()
    return countryJsonString
            .getAtJsonPath("zip")
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

private fun Map<*, *>.toJsonObject(): JsonObject {
    val jsonObject = JsonObject()
    forEach {
        (key, value) -> jsonObject.addProperty(key.toString(), value.toString())
    }
    return jsonObject
}
