package io.github.mkhytarmkhoian.herald.kmp.meta

import io.github.mkhytarmkhoian.herald.kmp.AnalyticsValues
import kotlin.test.Test
import kotlin.test.assertEquals

class MetaRevenueTest {
    @Test
    fun metaPurchaseEventKeepsItsFieldsAndAndroidsDefaults() {
        val event = metaPurchaseEvent(name = "x", amount = 4.99, currency = "x")

        assertEquals("x", event.name)
        assertEquals(4.99, event.amount)
        assertEquals("x", event.currency)
        assertEquals(emptyMap(), event.parameters)
    }

    @Test
    fun metaPurchaseEventKeepsEveryField() {
        val event = metaPurchaseEvent(
            name = "x",
            amount = 4.99,
            currency = "x",
            parameters = mapOf("source" to AnalyticsValues.string("paywall")),
        )

        assertEquals("x", event.name)
        assertEquals(4.99, event.amount)
        assertEquals("x", event.currency)
        assertEquals(setOf("source"), event.parameters.keys)
    }
}
