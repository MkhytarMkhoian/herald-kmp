@file:OptIn(ExperimentalObjCName::class)

package io.github.mkhytarmkhoian.herald.kmp.meta

import io.github.mkhytarmkhoian.herald.kmp.AnalyticsValue
import io.github.mkhytarmkhoian.herald.kmp.IosVendorEvent
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

// herald-ios-meta has the same name, so Swift sees this with "Kotlin" in front.
// IosVendorEvent tells the bridge the app must translate it.

@ObjCName(swiftName = "KotlinMetaPurchaseEvent")
public actual class MetaPurchaseEvent internal constructor(
    actual override val name: String,
    public actual val amount: Double,
    public actual val currency: String,
    actual override val parameters: Map<String, AnalyticsValue>,
) : IosVendorEvent

public actual fun metaPurchaseEvent(
    name: String,
    amount: Double,
    currency: String,
    parameters: Map<String, AnalyticsValue>,
): MetaPurchaseEvent = MetaPurchaseEvent(
    name = name,
    amount = amount,
    currency = currency,
    parameters = parameters,
)
