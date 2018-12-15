package com.laundrapp.postcodes

import org.junit.Assert
import org.junit.Test
import java.util.*

class FormatterTest {
    private val formatterLV: Formatter = Formatter(Locale.forLanguageTag("en-LV"))

    @Test
    fun `'-' added to postcode where needed`() {
        Assert.assertEquals("LV-1234", formatterLV.format("LV1234"))
        Assert.assertEquals("LV-12", formatterLV.format("LV12"))
    }

    @Test
    fun `'-' not inserted when it is final character of a postcode`() {
        Assert.assertEquals("LV", formatterLV.format("LV"))
        Assert.assertEquals("LV", formatterLV.format("LV-"))
    }
}