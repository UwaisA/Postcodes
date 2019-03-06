package com.laundrapp.postcodes

/**
 * Breaks a postcode down into it's major and minor components
 *
 * @param postcodeInput The formatted postcode to be broken into components
 * @param validator The Validator to be used to calculate the components of the postcode String
 * @throws IllegalArgumentException If the value of postcodeInput is invalid according to the validator
 */
class Components internal constructor(postcodeInput: String, private val validator: Validator) {
    val postcode: String = if (validator.partialValidate(postcodeInput)) {
        postcodeInput
    } else {
        throw IllegalArgumentException("Input postcode $postcodeInput incorrectly formatted")
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
        val postcodeSplit = postcode.split(' ', '-')
        if (postcodeSplit.size > 1) {
            major = postcodeSplit[0]
            minor = if(validator.validate(postcodeInput)) postcodeSplit[1] else null
        } else if (validator.partialValidate("$postcodeInput ") || validator.partialValidate("$postcodeInput-")) {
            major = postcode
            minor = null
        } else {
            major = null
            minor = null
        }
    }

}