package com.laundrapp.postcodes

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.junit.Assert.*
import org.junit.Test

class PostcodeResultTest {

    @Test
    fun `Equals method`() {
        assertEquals(
                PostcodeResult("ABCD", 10, true),
                PostcodeResult("ABCD", 10, true)
        )
        assertThat(
                PostcodeResult("ABCD", 5, true),
                `is`(not(PostcodeResult("ABCE", 5, true)))
        )
        assertThat(
                PostcodeResult("ABCD", 5, true),
                `is`(not(PostcodeResult("ABCD", 6, true)))
        )
        assertThat(
                PostcodeResult("ABCD", 5, true),
                `is`(not(PostcodeResult("ABCD", 5, false)))
        )
    }


}