package io.github.mkhytarmkhoian.herald.kmp.amplitude

import io.github.mkhytarmkhoian.herald.kmp.AnalyticsValue
import io.github.mkhytarmkhoian.herald.kmp.Event

/**
 * A purchase, sent to Amplitude as revenue. On Android it is herald-amplitude's own class; on iOS
 * the app's Swift turns it into herald-ios-amplitude's.
 */
public expect class AmplitudeRevenueEvent : Event {
    override val name: String
    public val price: Double
    public val quantity: Int
    public val productId: String?
    public val revenueType: String?
    public val currency: String?
    public val revenue: Double?
    public val receipt: String?
    public val receiptSig: String?
    public val insertId: String?
    override val parameters: Map<String, AnalyticsValue>
}

/**
 * Makes Amplitude revenue events, with the same parameters and defaults as on Android:
 *
 * ```kotlin
 * analytics.track(AmplitudeRevenue.event(name = "purchase", price = 4.99, productId = "pro"))
 * ```
 *
 * A function rather than a constructor: Kotlin doesn't let a shared constructor keep its default
 * values when Android's class stands in for it.
 */
public expect object AmplitudeRevenue {
    public fun event(
        name: String,
        price: Double,
        quantity: Int = 1,
        productId: String? = null,
        revenueType: String? = null,
        currency: String? = null,
        revenue: Double? = null,
        receipt: String? = null,
        receiptSig: String? = null,
        insertId: String? = null,
        parameters: Map<String, AnalyticsValue> = emptyMap(),
    ): AmplitudeRevenueEvent
}
