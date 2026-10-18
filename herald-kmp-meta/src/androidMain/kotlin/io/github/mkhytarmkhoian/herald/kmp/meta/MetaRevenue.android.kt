package io.github.mkhytarmkhoian.herald.kmp.meta

import io.github.mkhytarmkhoian.herald.kmp.AnalyticsValue

public actual typealias MetaPurchaseEvent = io.github.mkhytarmkhoian.herald.meta.MetaPurchaseEvent

public actual fun metaPurchaseEvent(
    name: String,
    amount: Double,
    currency: String,
    parameters: Map<String, AnalyticsValue>,
): MetaPurchaseEvent = io.github.mkhytarmkhoian.herald.meta.MetaPurchaseEvent(
    name = name,
    amount = amount,
    currency = currency,
    parameters = parameters,
)
