package com.laundrapp.postcodes

data class Options(val includeOptionalSeparators: OptionalSeparator) {
    enum class OptionalSeparator {
        INCLUDE,
        EXCLUDE,
        ACCEPT_EITHER
    }
}