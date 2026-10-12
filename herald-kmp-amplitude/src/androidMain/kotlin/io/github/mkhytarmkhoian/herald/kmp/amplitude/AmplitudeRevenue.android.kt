package io.github.mkhytarmkhoian.herald.kmp.amplitude

import io.github.mkhytarmkhoian.herald.kmp.AnalyticsValue

public actual typealias AmplitudeRevenueEvent =
    io.github.mkhytarmkhoian.herald.amplitude.AmplitudeRevenueEvent

public actual fun amplitudeRevenueEvent(
    name: String,
    price: Double,
    quantity: Int,
    productId: String?,
    revenueType: String?,
    currency: String?,
    revenue: Double?,
    receipt: String?,
    receiptSig: String?,
    insertId: String?,
    parameters: Map<String, AnalyticsValue>,
): AmplitudeRevenueEvent = io.github.mkhytarmkhoian.herald.amplitude.AmplitudeRevenueEvent(
    name = name,
    price = price,
    quantity = quantity,
    productId = productId,
    revenueType = revenueType,
    currency = currency,
    revenue = revenue,
    receipt = receipt,
    receiptSig = receiptSig,
    insertId = insertId,
    parameters = parameters,
)
