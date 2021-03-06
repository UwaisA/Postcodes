package com.laundrapp.postcodes

import com.laundrapp.postcodes.Options.OptionalSeparator.*
import java.util.*

/**
 * Breaks a postcode down into it's major and minor components
 *
 * @param postcodeInput The formatted postcode to be broken into components
 * @param locales The Locales to be used to calculate the components of the postcode String
 * @throws IllegalArgumentException If the value of postcodeInput is invalid for the locale
 */
class Components internal constructor(private val postcodeInput: String, private val locales: List<Locale>) {

    constructor(postcodeInput: String, locale: Locale): this(postcodeInput, listOf(locale))

    private val acceptingValidator = Validator.create(locales, Options(ACCEPT_EITHER))
    private val spaceIncludingValidator = Validator.create(locales, Options(INCLUDE))
    private val formatter = Formatter.create(spaceIncludingValidator)

    private val formattedPostcode: String =
            try {
                formatter.format(CursoredString(postcodeInput, 0)).string
            } catch (e: Formatter.CouldNotFormatException) {
                throw invalidPostcodeException()
            }

    /**
     * The postcode input to this `Components` instance
     */
    val postcode: String = if (acceptingValidator.partialValidate(postcodeInput)) {
        postcodeInput
    } else {
        throw invalidPostcodeException()
    }

    /**
     * The major part of a postcode is the portion before the separator. It usually corresponds to a larger area in
     * the real world. E.g. "12345" would be the major part of "12345 678"
     */
    val major: String?

    /**
     * The minor part of a postcode is the portion after the separator. It usually corresponds to a smaller area in
     * the real world. E.g. "678" would be the minor part of "12345 678"
     */
    val minor: String?

    init {
        val postcodeSplit = formattedPostcode.split(' ', '-')
        if (postcodeSplit.size > 1) {
            major = postcodeSplit[0]
            minor = if(spaceIncludingValidator.validate(formattedPostcode)) postcodeSplit[1] else null
        } else if (spaceIncludingValidator.partialValidate("${postcodeSplit[0]} ") ||
                spaceIncludingValidator.partialValidate("${postcodeSplit[0]}-")) {
            major = postcodeSplit[0]
            minor = null
        } else {
            major = null
            minor = null
        }
    }

    private fun invalidPostcodeException(): IllegalArgumentException =
            IllegalArgumentException("Input postcode $postcodeInput is invalid for locales $locales")

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Components

        if (postcode != other.postcode) return false
        if (major != other.major) return false
        if (minor != other.minor) return false

        return true
    }

    override fun hashCode(): Int {
        var result = postcode.hashCode()
        result = 31 * result + (major?.hashCode() ?: 0)
        result = 31 * result + (minor?.hashCode() ?: 0)
        return result
    }


}