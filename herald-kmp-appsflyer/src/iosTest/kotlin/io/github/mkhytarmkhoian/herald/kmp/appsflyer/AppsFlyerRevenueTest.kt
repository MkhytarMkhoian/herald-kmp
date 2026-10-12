package io.github.mkhytarmkhoian.herald.kmp.appsflyer

import io.github.mkhytarmkhoian.herald.kmp.AnalyticsValues
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class AppsFlyerRevenueTest {
    @Test
    fun appsFlyerPurchaseEventKeepsItsFieldsAndAndroidsDefaults() {
        val event = appsFlyerPurchaseEvent(name = "x", revenue = 4.99, currency = "x")

        assertEquals("x", event.name)
        assertEquals(4.99, event.revenue)
        assertEquals("x", event.currency)
        assertNull(event.contentId)
        assertNull(event.contentType)
        assertNull(event.quantity)
        assertNull(event.orderId)
        assertEquals(emptyMap(), event.parameters)
    }

    @Test
    fun appsFlyerPurchaseEventKeepsEveryField() {
        val event = appsFlyerPurchaseEvent(
            name = "x",
            revenue = 4.99,
            currency = "x",
            contentId = "y",
            contentType = "y",
            quantity = 2,
            orderId = "y",
            parameters = mapOf("source" to AnalyticsValues.string("paywall")),
        )

        assertEquals("x", event.name)
        assertEquals(4.99, event.revenue)
        assertEquals("x", event.currency)
        assertEquals("y", event.contentId)
        assertEquals("y", event.contentType)
        assertEquals(2, event.quantity)
        assertEquals("y", event.orderId)
        assertEquals(setOf("source"), event.parameters.keys)
    }

    @Test
    fun appsFlyerSubscribeEventKeepsItsFieldsAndAndroidsDefaults() {
        val event = appsFlyerSubscribeEvent(name = "x", revenue = 4.99, currency = "x")

        assertEquals("x", event.name)
        assertEquals(4.99, event.revenue)
        assertEquals("x", event.currency)
        assertEquals(emptyMap(), event.parameters)
    }

    @Test
    fun appsFlyerSubscribeEventKeepsEveryField() {
        val event = appsFlyerSubscribeEvent(
            name = "x",
            revenue = 4.99,
            currency = "x",
            parameters = mapOf("source" to AnalyticsValues.string("paywall")),
        )

        assertEquals("x", event.name)
        assertEquals(4.99, event.revenue)
        assertEquals("x", event.currency)
        assertEquals(setOf("source"), event.parameters.keys)
    }

    @Test
    fun appsFlyerAdRevenueEventKeepsItsFieldsAndAndroidsDefaults() {
        val event = appsFlyerAdRevenueEvent(
            name = "x",
            monetizationNetwork = "x",
            mediationNetwork = MediationNetwork.GOOGLE_ADMOB,
            revenue = 4.99,
            currency = "x",
        )

        assertEquals("x", event.name)
        assertEquals("x", event.monetizationNetwork)
        assertEquals(MediationNetwork.GOOGLE_ADMOB, event.mediationNetwork)
        assertEquals(4.99, event.revenue)
        assertEquals("x", event.currency)
        assertEquals(emptyMap(), event.parameters)
    }

    @Test
    fun appsFlyerAdRevenueEventKeepsEveryField() {
        val event = appsFlyerAdRevenueEvent(
            name = "x",
            monetizationNetwork = "x",
            mediationNetwork = MediationNetwork.GOOGLE_ADMOB,
            revenue = 4.99,
            currency = "x",
            parameters = mapOf("source" to AnalyticsValues.string("paywall")),
        )

        assertEquals("x", event.name)
        assertEquals("x", event.monetizationNetwork)
        assertEquals(MediationNetwork.GOOGLE_ADMOB, event.mediationNetwork)
        assertEquals(4.99, event.revenue)
        assertEquals("x", event.currency)
        assertEquals(setOf("source"), event.parameters.keys)
    }

}
