package io.github.mkhytarmkhoian.herald.kmp.adjust

import io.github.mkhytarmkhoian.herald.kmp.AnalyticsValues
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class AdjustRevenueTest {
    @Test
    fun adjustRevenueEventKeepsItsFieldsAndAndroidsDefaults() {
        val event = adjustRevenueEvent(name = "x", revenue = 4.99, currency = "x")

        assertEquals("x", event.name)
        assertEquals(4.99, event.revenue)
        assertEquals("x", event.currency)
        assertNull(event.deduplicationId)
        assertEquals(emptyMap(), event.parameters)
    }

    @Test
    fun adjustRevenueEventKeepsEveryField() {
        val event = adjustRevenueEvent(
            name = "x",
            revenue = 4.99,
            currency = "x",
            deduplicationId = "y",
            parameters = mapOf("source" to AnalyticsValues.string("paywall")),
        )

        assertEquals("x", event.name)
        assertEquals(4.99, event.revenue)
        assertEquals("x", event.currency)
        assertEquals("y", event.deduplicationId)
        assertEquals(setOf("source"), event.parameters.keys)
    }

    @Test
    fun adjustAdRevenueEventKeepsItsFieldsAndAndroidsDefaults() {
        val event = adjustAdRevenueEvent(name = "x", source = "x", revenue = 4.99, currency = "x")

        assertEquals("x", event.name)
        assertEquals("x", event.source)
        assertEquals(4.99, event.revenue)
        assertEquals("x", event.currency)
        assertNull(event.adImpressionsCount)
        assertNull(event.adRevenueNetwork)
        assertNull(event.adRevenueUnit)
        assertNull(event.adRevenuePlacement)
        assertEquals(emptyMap(), event.parameters)
    }

    @Test
    fun adjustAdRevenueEventKeepsEveryField() {
        val event = adjustAdRevenueEvent(
            name = "x",
            source = "x",
            revenue = 4.99,
            currency = "x",
            adImpressionsCount = 2,
            adRevenueNetwork = "y",
            adRevenueUnit = "y",
            adRevenuePlacement = "y",
            parameters = mapOf("source" to AnalyticsValues.string("paywall")),
        )

        assertEquals("x", event.name)
        assertEquals("x", event.source)
        assertEquals(4.99, event.revenue)
        assertEquals("x", event.currency)
        assertEquals(2, event.adImpressionsCount)
        assertEquals("y", event.adRevenueNetwork)
        assertEquals("y", event.adRevenueUnit)
        assertEquals("y", event.adRevenuePlacement)
        assertEquals(setOf("source"), event.parameters.keys)
    }

}
