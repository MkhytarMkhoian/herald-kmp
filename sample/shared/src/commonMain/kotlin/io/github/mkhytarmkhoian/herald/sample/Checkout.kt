package io.github.mkhytarmkhoian.herald.sample

import io.github.mkhytarmkhoian.herald.kmp.AnalyticsValue
import io.github.mkhytarmkhoian.herald.kmp.Event
import io.github.mkhytarmkhoian.herald.kmp.EventTrackerService
import io.github.mkhytarmkhoian.herald.kmp.ScreenViewEvent
import io.github.mkhytarmkhoian.herald.kmp.parameters

// Written once, in shared code. On Android these are Android Herald events; on iOS they reach
// herald-ios through the bridge.

public object CheckoutViewed : ScreenViewEvent {
    override val name: String = "checkout"
}

public class CheckoutStarted(plan: String, seats: Int, price: Double) : Event {
    override val name: String = "checkout_started"
    override val parameters: Map<String, AnalyticsValue> = parameters {
        put("plan", plan)
        put("seats", seats)
        put("price", price)
    }
}

public class CheckoutViewModel(private val analytics: EventTrackerService) {
    public suspend fun opened(): Unit = analytics.track(CheckoutViewed)

    public suspend fun started(plan: String, seats: Int): Unit =
        analytics.track(CheckoutStarted(plan, seats, price = seats * 4.99))
}
