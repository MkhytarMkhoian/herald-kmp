@file:OptIn(ExperimentalObjCName::class)

package io.github.mkhytarmkhoian.herald.kmp.adjust

import io.github.mkhytarmkhoian.herald.kmp.AnalyticsValue
import io.github.mkhytarmkhoian.herald.kmp.Event
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

// herald-ios-adjust has classes with these names too, so Swift sees these with
// "Kotlin" in front.

@ObjCName(swiftName = "KotlinAdjustRevenueEvent")
public actual class AdjustRevenueEvent internal constructor(
    actual override val name: String,
    public actual val revenue: Double,
    public actual val currency: String,
    public actual val deduplicationId: String?,
    actual override val parameters: Map<String, AnalyticsValue>,
) : Event

@ObjCName(swiftName = "KotlinAdjustAdRevenueEvent")
public actual class AdjustAdRevenueEvent internal constructor(
    actual override val name: String,
    public actual val source: String,
    public actual val revenue: Double,
    public actual val currency: String,
    public actual val adImpressionsCount: Int?,
    public actual val adRevenueNetwork: String?,
    public actual val adRevenueUnit: String?,
    public actual val adRevenuePlacement: String?,
    actual override val parameters: Map<String, AnalyticsValue>,
) : Event

public actual fun adjustRevenueEvent(
    name: String,
    revenue: Double,
    currency: String,
    deduplicationId: String?,
    parameters: Map<String, AnalyticsValue>,
): AdjustRevenueEvent = AdjustRevenueEvent(
    name = name,
    revenue = revenue,
    currency = currency,
    deduplicationId = deduplicationId,
    parameters = parameters,
)

public actual fun adjustAdRevenueEvent(
    name: String,
    source: String,
    revenue: Double,
    currency: String,
    adImpressionsCount: Int?,
    adRevenueNetwork: String?,
    adRevenueUnit: String?,
    adRevenuePlacement: String?,
    parameters: Map<String, AnalyticsValue>,
): AdjustAdRevenueEvent = AdjustAdRevenueEvent(
    name = name,
    source = source,
    revenue = revenue,
    currency = currency,
    adImpressionsCount = adImpressionsCount,
    adRevenueNetwork = adRevenueNetwork,
    adRevenueUnit = adRevenueUnit,
    adRevenuePlacement = adRevenuePlacement,
    parameters = parameters,
)
