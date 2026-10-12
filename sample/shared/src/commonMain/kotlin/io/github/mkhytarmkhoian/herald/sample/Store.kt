package io.github.mkhytarmkhoian.herald.sample

import io.github.mkhytarmkhoian.herald.kmp.EventTrackerService
import io.github.mkhytarmkhoian.herald.kmp.amplitude.AmplitudeRevenue
import io.github.mkhytarmkhoian.herald.kmp.parameters

public class StoreViewModel(private val analytics: EventTrackerService) {
    // Written once. Amplitude gets it as revenue on both platforms.
    public suspend fun bought(plan: String): Unit = analytics.track(
        AmplitudeRevenue.event(
            name = "purchase",
            price = 4.99,
            productId = plan,
            currency = "USD",
            parameters = parameters { put("source", "paywall") },
        ),
    )
}
