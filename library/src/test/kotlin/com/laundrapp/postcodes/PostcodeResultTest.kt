package com.laundrapp.postcodes

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.junit.Assert.*
import org.junit.Test
import java.util.*

class PostcodeResultTest {

    @Test
    fun `Equals method`() {
        val components1 = Components("12345 6789", Locale.US)
        val components2 = Components("12345 678", Locale.US)

        assertEquals(
                PostcodeResult("ABCD", 10, true, components1),
                PostcodeResult("ABCD", 10, true, components1)
        )
        assertThat(
                PostcodeResult("ABCD", 5, true, components1),
                `is`(not(PostcodeResult("ABCE", 5, true, components1)))
        )
        assertThat(
                PostcodeResult("ABCD", 5, true, components1),
                `is`(not(PostcodeResult("ABCD", 6, true, components1)))
        )
        assertThat(
                PostcodeResult("ABCD", 5, true, components1),
                `is`(not(PostcodeResult("ABCD", 5, false, components1)))
        )
        assertThat(
                PostcodeResult("ABCD", 5, true, components1),
                `is`(not(PostcodeResult("ABCD", 5, true, components2)))
        )
    }


}