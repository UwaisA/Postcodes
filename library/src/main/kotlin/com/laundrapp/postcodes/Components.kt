package com.laundrapp.postcodes

import com.laundrapp.postcodes.Options.OptionalSeparator.*
import java.util.*

/**
 * Breaks a postcode down into it's major and minor components
 *
 * @param postcodeInput The formatted postcode to be broken into components
 * @param locale The Locale to be used to calculate the components of the postcode String
 * @throws IllegalArgumentException If the value of postcodeInput is invalid for the locale
 */
class Components internal constructor(private val postcodeInput: String, private val locale: Locale) {
    private val acceptingValidator = Validator.create(locale, Options(ACCEPT_EITHER))
    private val spaceIncludingValidator = Validator.create(locale, Options(INCLUDE))
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
     * the real world. E.g. "678" would be the major part of "12345 678"
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
            IllegalArgumentException("Input postcode $postcodeInput is invalid for locale $locale")

}