package com.laundrapp.postcodes

import com.google.gson.JsonParser
import java.io.File
import java.io.FileOutputStream
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
    }?.toProperties(".regexp")

    postcodeProperties?.storeOrdered(getResourceFile(RegexRetriever.regexesFileName).outputStream(), null)
}

private fun getPostcodeRegex(countryCode: String): String? {
    println("Getting for $countryCode")
    return URL("$baseURL/data/$countryCode").readText().getAtJsonPath("zip")
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

private fun Properties.storeOrdered(outputStream: FileOutputStream, comments: String?) {
    val tmp = object : Properties() {
        @Synchronized
        override fun keys(): Enumeration<Any> {
            return Collections.enumeration(TreeSet<Any>(super.keys))
        }
    }
    tmp.putAll(this)
    tmp.store(outputStream, comments)
}

private fun Map<*, *>.toProperties(appendToKey: String): Properties {
    val properties = Properties()
    forEach {
        (key, value) -> properties[key.toString() + appendToKey] = value.toString()
    }
    return properties
}

private fun getResourceFile(fileName: String): File {
    return File("src/main/resources/$fileName")
}
