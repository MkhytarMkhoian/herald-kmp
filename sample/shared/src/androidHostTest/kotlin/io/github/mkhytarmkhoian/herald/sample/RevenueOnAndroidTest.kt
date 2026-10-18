package io.github.mkhytarmkhoian.herald.sample

import com.appsflyer.share.MediationNetwork
import io.github.mkhytarmkhoian.herald.Event
import io.github.mkhytarmkhoian.herald.EventTrackerService
import io.github.mkhytarmkhoian.herald.adjust.AdjustAdRevenueEvent
import io.github.mkhytarmkhoian.herald.adjust.AdjustRevenueEvent
import io.github.mkhytarmkhoian.herald.amplitude.AmplitudeRevenueEvent
import io.github.mkhytarmkhoian.herald.appsflyer.AppsFlyerAdRevenueEvent
import io.github.mkhytarmkhoian.herald.appsflyer.AppsFlyerPurchaseEvent
import io.github.mkhytarmkhoian.herald.appsflyer.AppsFlyerSubscribeEvent
import io.github.mkhytarmkhoian.herald.asString
import io.github.mkhytarmkhoian.herald.meta.MetaPurchaseEvent
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

/**
 * Each shared revenue event is the Android vendor module's own class, so its factories claim it.
 */
class RevenueOnAndroidTest {
    private val tracked = mutableListOf<Event>()
    private val store =
        StoreViewModel(analytics = EventTrackerService { event -> tracked += event })

    @Test
    fun purchasesAreTheAndroidSdksClasses() = runTest {
        store.bought("pro")

        val amplitude = assertIs<AmplitudeRevenueEvent>(tracked[0])
        assertEquals(1, amplitude.quantity) // the default, as on Android
        assertEquals("pro", amplitude.productId)
        assertEquals("paywall", amplitude.parameters.getValue("source").asString)

        val adjust = assertIs<AdjustRevenueEvent>(tracked[1])
        assertEquals("order-1", adjust.deduplicationId)

        val purchase = assertIs<AppsFlyerPurchaseEvent>(tracked[2])
        assertEquals("pro", purchase.contentId)

        assertIs<AppsFlyerSubscribeEvent>(tracked[3])

        val meta = assertIs<MetaPurchaseEvent>(tracked[4])
        assertEquals(4.99, meta.amount)
    }

    @Test
    fun adRevenueIsTheAndroidSdksClasses() = runTest {
        store.adShown()

        val adjust = assertIs<AdjustAdRevenueEvent>(tracked[0])
        assertEquals("applovin_max_sdk", adjust.source)

        val appsFlyer = assertIs<AppsFlyerAdRevenueEvent>(tracked[1])
        // AppsFlyer's own enum
        assertEquals(MediationNetwork.APPLOVIN_MAX, appsFlyer.mediationNetwork)
    }
}
