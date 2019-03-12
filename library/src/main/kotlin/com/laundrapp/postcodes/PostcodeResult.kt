package com.laundrapp.postcodes

/**
 * @param postcode the postcode - can be formatted or not. See {@code formatted}
 * @param cursorPosition the required cursor position in a text field for the user to not be interrupted by the
 * formatting process
 * @param formatted true if the postcode was formatted
 * @param components an object to describe the components of the postcode. See {@code Components} for more details
 */
data class PostcodeResult(val postcode: String, val cursorPosition: Int, val formatted: Boolean, val components: Components?)
