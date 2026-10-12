package io.github.mkhytarmkhoian.herald.kmp.adjust

import io.github.mkhytarmkhoian.herald.kmp.AnalyticsValue
import io.github.mkhytarmkhoian.herald.kmp.Event

/**
 * A purchase, sent to Adjust as revenue with its event token. On Android it is herald-adjust's own
 * class; on iOS the app's Swift turns it into herald-ios-adjust's. Shared code makes one with
 * [adjustRevenueEvent].
 */
public expect class AdjustRevenueEvent : Event {
    override val name: String
    public val revenue: Double
    public val currency: String
    public val deduplicationId: String?
    override val parameters: Map<String, AnalyticsValue>
}

/**
 * Ad revenue, sent to Adjust from an ad network. On Android it is herald-adjust's own class; on iOS
 * the app's Swift turns it into herald-ios-adjust's. Shared code makes one with
 * [adjustAdRevenueEvent].
 */
public expect class AdjustAdRevenueEvent : Event {
    override val name: String
    public val source: String
    public val revenue: Double
    public val currency: String
    public val adImpressionsCount: Int?
    public val adRevenueNetwork: String?
    public val adRevenueUnit: String?
    public val adRevenuePlacement: String?
    override val parameters: Map<String, AnalyticsValue>
}

/**
 * Makes an [AdjustRevenueEvent], with the same parameters and defaults as Android's constructor.
 * Shared code can't call that constructor: Kotlin drops its default values on the way from Android.
 */
public expect fun adjustRevenueEvent(
    name: String,
    revenue: Double,
    currency: String,
    deduplicationId: String? = null,
    parameters: Map<String, AnalyticsValue> = emptyMap(),
): AdjustRevenueEvent

/**
 * Makes an [AdjustAdRevenueEvent], with the same parameters and defaults as Android's constructor.
 * Shared code can't call that constructor: Kotlin drops its default values on the way from Android.
 */
public expect fun adjustAdRevenueEvent(
    name: String,
    source: String,
    revenue: Double,
    currency: String,
    adImpressionsCount: Int? = null,
    adRevenueNetwork: String? = null,
    adRevenueUnit: String? = null,
    adRevenuePlacement: String? = null,
    parameters: Map<String, AnalyticsValue> = emptyMap(),
): AdjustAdRevenueEvent
