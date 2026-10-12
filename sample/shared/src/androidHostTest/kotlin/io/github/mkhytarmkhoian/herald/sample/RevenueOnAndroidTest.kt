package io.github.mkhytarmkhoian.herald.sample

import io.github.mkhytarmkhoian.herald.Event
import io.github.mkhytarmkhoian.herald.EventTrackerService
import io.github.mkhytarmkhoian.herald.amplitude.AmplitudeRevenueEvent
import io.github.mkhytarmkhoian.herald.asString
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

/** The shared revenue event is herald-amplitude's own class, so Android's Amplitude claims it. */
class RevenueOnAndroidTest {
    @Test
    fun sharedRevenueIsTheAndroidSdksClass() = runTest {
        val tracked = mutableListOf<Event>()

        StoreViewModel(analytics = EventTrackerService { event -> tracked += event }).bought("pro")

        val revenue = assertIs<AmplitudeRevenueEvent>(tracked.single())
        assertEquals("purchase", revenue.name)
        assertEquals(4.99, revenue.price)
        assertEquals(1, revenue.quantity) // the default, as on Android
        assertEquals("pro", revenue.productId)
        assertEquals("USD", revenue.currency)
        assertEquals("paywall", revenue.parameters.getValue("source").asString)
    }
}
