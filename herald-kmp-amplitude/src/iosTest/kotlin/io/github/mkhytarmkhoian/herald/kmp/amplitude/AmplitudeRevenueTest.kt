package io.github.mkhytarmkhoian.herald.kmp.amplitude

import io.github.mkhytarmkhoian.herald.kmp.AnalyticsValues
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class AmplitudeRevenueTest {
    @Test
    fun amplitudeRevenueEventKeepsItsFieldsAndAndroidsDefaults() {
        val event = amplitudeRevenueEvent(name = "x", price = 4.99)

        assertEquals("x", event.name)
        assertEquals(4.99, event.price)
        assertEquals(1, event.quantity)
        assertNull(event.productId)
        assertNull(event.revenueType)
        assertNull(event.currency)
        assertNull(event.revenue)
        assertNull(event.receipt)
        assertNull(event.receiptSig)
        assertNull(event.insertId)
        assertEquals(emptyMap(), event.parameters)
    }

    @Test
    fun amplitudeRevenueEventKeepsEveryField() {
        val event = amplitudeRevenueEvent(
            name = "x",
            price = 4.99,
            quantity = 2,
            productId = "y",
            revenueType = "y",
            currency = "y",
            revenue = 9.98,
            receipt = "y",
            receiptSig = "y",
            insertId = "y",
            parameters = mapOf("source" to AnalyticsValues.string("paywall")),
        )

        assertEquals("x", event.name)
        assertEquals(4.99, event.price)
        assertEquals(2, event.quantity)
        assertEquals("y", event.productId)
        assertEquals("y", event.revenueType)
        assertEquals("y", event.currency)
        assertEquals(9.98, event.revenue)
        assertEquals("y", event.receipt)
        assertEquals("y", event.receiptSig)
        assertEquals("y", event.insertId)
        assertEquals(setOf("source"), event.parameters.keys)
    }

}
