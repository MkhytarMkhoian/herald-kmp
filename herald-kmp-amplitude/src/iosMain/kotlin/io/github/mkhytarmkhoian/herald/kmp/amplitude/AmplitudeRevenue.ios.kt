@file:OptIn(ExperimentalObjCName::class)

package io.github.mkhytarmkhoian.herald.kmp.amplitude

import io.github.mkhytarmkhoian.herald.kmp.AnalyticsValue
import io.github.mkhytarmkhoian.herald.kmp.IosVendorEvent
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

// herald-ios-amplitude has the same names, so Swift sees these with "Kotlin" in front.
// IosVendorEvent tells the bridge the app must translate them.

@ObjCName(swiftName = "KotlinAmplitudeRevenueEvent")
public actual class AmplitudeRevenueEvent internal constructor(
    actual override val name: String,
    public actual val price: Double,
    public actual val quantity: Int,
    public actual val productId: String?,
    public actual val revenueType: String?,
    public actual val currency: String?,
    public actual val revenue: Double?,
    public actual val receipt: String?,
    public actual val receiptSig: String?,
    public actual val insertId: String?,
    actual override val parameters: Map<String, AnalyticsValue>,
) : IosVendorEvent

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
): AmplitudeRevenueEvent = AmplitudeRevenueEvent(
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
