package io.github.mkhytarmkhoian.herald.kmp.amplitude

import io.github.mkhytarmkhoian.herald.kmp.parameters
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class AmplitudeRevenueEventTest {
    @Test
    fun theDefaultsAreAndroids() {
        val event = AmplitudeRevenue.event(name = "purchase", price = 4.99)

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
    fun everyFieldIsKept() {
        val event = AmplitudeRevenue.event(
            name = "purchase",
            price = 4.99,
            quantity = 2,
            productId = "pro",
            revenueType = "subscription",
            currency = "USD",
            revenue = 9.98,
            receipt = "r",
            receiptSig = "s",
            insertId = "i",
            parameters = parameters { put("source", "paywall") },
        )

        assertEquals(
            listOf("purchase", 4.99, 2, "pro", "subscription", "USD", 9.98, "r", "s", "i"),
            listOf(
                event.name, event.price, event.quantity, event.productId, event.revenueType,
                event.currency, event.revenue, event.receipt, event.receiptSig, event.insertId,
            ),
        )
        assertEquals(setOf("source"), event.parameters.keys)
    }
}
