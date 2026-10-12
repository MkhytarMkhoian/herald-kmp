package io.github.mkhytarmkhoian.herald.kmp.amplitude

import io.github.mkhytarmkhoian.herald.kmp.AnalyticsValue
import io.github.mkhytarmkhoian.herald.kmp.Event

// On Android, these are herald-amplitude's own classes. On iOS, the app's Swift turns them
// into herald-ios-amplitude's (see RevenueTranslation.swift).

/**
 * A purchase, sent to Amplitude as revenue. Make one with [amplitudeRevenueEvent].
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
 * Shared code can't use the constructor itself: Kotlin drops its defaults.
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
