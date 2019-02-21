package com.laundrapp.postcodes

class Options(val includeOptionalSeparators: OptionalSeparator) {
    enum class OptionalSeparator {
        INCLUDE,
        EXCLUDE,
        ACCEPT_EITHER
    }
}