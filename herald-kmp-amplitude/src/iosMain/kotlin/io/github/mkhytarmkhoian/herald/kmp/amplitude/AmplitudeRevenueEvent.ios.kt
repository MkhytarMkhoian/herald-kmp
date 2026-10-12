@file:OptIn(ExperimentalObjCName::class)

package io.github.mkhytarmkhoian.herald.kmp.amplitude

import io.github.mkhytarmkhoian.herald.kmp.AnalyticsValue
import io.github.mkhytarmkhoian.herald.kmp.Event
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

// herald-ios-amplitude has an AmplitudeRevenueEvent too, so Swift sees this one as
// KotlinAmplitudeRevenueEvent.
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
) : Event

public actual object AmplitudeRevenue {
    public actual fun event(
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
}
