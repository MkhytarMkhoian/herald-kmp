package io.github.mkhytarmkhoian.herald.kmp.meta

import io.github.mkhytarmkhoian.herald.kmp.AnalyticsValue
import io.github.mkhytarmkhoian.herald.kmp.Event

// On Android, this is herald-meta's own class. On iOS, the app's Swift turns it into
// herald-ios-meta's (see RevenueTranslation.swift).

/** A purchase, sent to Meta as fb_mobile_purchase. Make one with [metaPurchaseEvent]. */
public expect class MetaPurchaseEvent : Event {
    override val name: String
    public val amount: Double
    public val currency: String
    override val parameters: Map<String, AnalyticsValue>
}

/**
 * Makes a [MetaPurchaseEvent], with the same parameters and defaults as Android's constructor.
 * Shared code can't use the constructor itself: Kotlin drops its defaults.
 */
public expect fun metaPurchaseEvent(
    name: String,
    amount: Double,
    currency: String,
    parameters: Map<String, AnalyticsValue> = emptyMap(),
): MetaPurchaseEvent
