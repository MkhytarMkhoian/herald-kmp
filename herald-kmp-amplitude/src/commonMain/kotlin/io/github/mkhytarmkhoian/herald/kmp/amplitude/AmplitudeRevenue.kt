package io.github.mkhytarmkhoian.herald.kmp.amplitude

import io.github.mkhytarmkhoian.herald.kmp.AnalyticsValue
import io.github.mkhytarmkhoian.herald.kmp.Event

/**
 * A purchase, sent to Amplitude as revenue. On Android it is herald-amplitude's own class; on iOS
 * the app's Swift turns it into herald-ios-amplitude's. Shared code makes one with
 * [amplitudeRevenueEvent].
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
 * Makes an [AmplitudeRevenueEvent], with the same parameters and defaults as Android's constructor.
 * Shared code can't call that constructor: Kotlin drops its default values on the way from Android.
 */
public expect fun amplitudeRevenueEvent(
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
