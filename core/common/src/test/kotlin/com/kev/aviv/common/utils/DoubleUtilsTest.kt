package com.kev.aviv.common.utils

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class DoubleUtilsTest {

    @Test
    fun `area without decimal part is shown as an integer`() {
        assertEquals("250", 250.0.toFormatArea())
    }

    @Test
    fun `area keeps one decimal when useful`() {
        assertEquals("72,5", 72.5.toFormatArea())
    }

    @Test
    fun `large areas are grouped like prices`() {
        assertEquals(1250.0.toFormatPrice(), 1250.0.toFormatArea())
    }

    @Test
    fun `price per square meter is computed from the price and the surface`() {
        assertEquals(6000.0, pricePerSquareMeter(price = 1_500_000.0, area = 250.0))
    }

    @Test
    fun `price per square meter is rounded to the euro`() {
        assertEquals(3333.0, pricePerSquareMeter(price = 10_000.0, area = 3.0))
    }

    @Test
    fun `price per square meter is unknown without a surface`() {
        assertNull(pricePerSquareMeter(price = 1_500_000.0, area = 0.0))
    }

    @Test
    fun `price per square meter is unknown without a price`() {
        assertNull(pricePerSquareMeter(price = 0.0, area = 120.0))
    }
}
