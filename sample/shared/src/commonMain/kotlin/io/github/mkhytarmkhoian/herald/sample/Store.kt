package io.github.mkhytarmkhoian.herald.sample

import io.github.mkhytarmkhoian.herald.kmp.EventTrackerService
import io.github.mkhytarmkhoian.herald.kmp.adjust.adjustAdRevenueEvent
import io.github.mkhytarmkhoian.herald.kmp.adjust.adjustRevenueEvent
import io.github.mkhytarmkhoian.herald.kmp.amplitude.amplitudeRevenueEvent
import io.github.mkhytarmkhoian.herald.kmp.appsflyer.MediationNetwork
import io.github.mkhytarmkhoian.herald.kmp.appsflyer.appsFlyerAdRevenueEvent
import io.github.mkhytarmkhoian.herald.kmp.appsflyer.appsFlyerPurchaseEvent
import io.github.mkhytarmkhoian.herald.kmp.appsflyer.appsFlyerSubscribeEvent
import io.github.mkhytarmkhoian.herald.kmp.parameters

// Written once. Each vendor gets its revenue on both platforms.
public class StoreViewModel(private val analytics: EventTrackerService) {
    public suspend fun bought(plan: String) {
        val source = parameters { put("source", "paywall") }
        analytics.track(
            amplitudeRevenueEvent(
                name = "purchase", price = 4.99, productId = plan, currency = "USD",
                parameters = source,
            ),
        )
        analytics.track(
            adjustRevenueEvent(
                name = "purchase", revenue = 4.99, currency = "USD", deduplicationId = "order-1",
            ),
        )
        analytics.track(
            appsFlyerPurchaseEvent(
                name = "af_purchase", revenue = 4.99, currency = "USD", contentId = plan,
                quantity = 1,
            ),
        )
        analytics.track(
            appsFlyerSubscribeEvent(name = "af_subscribe", revenue = 4.99, currency = "USD"),
        )
    }

    public suspend fun adShown() {
        analytics.track(
            adjustAdRevenueEvent(
                name = "ad_revenue", source = "applovin_max_sdk", revenue = 0.01, currency = "USD",
                adRevenueNetwork = "AppLovin",
            ),
        )
        analytics.track(
            appsFlyerAdRevenueEvent(
                name = "ad_revenue", monetizationNetwork = "AppLovin",
                mediationNetwork = MediationNetwork.APPLOVIN_MAX, revenue = 0.01, currency = "USD",
            ),
        )
    }
}
