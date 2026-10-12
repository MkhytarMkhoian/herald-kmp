package io.github.mkhytarmkhoian.herald.kmp

// On the JVM, and so on Android, each shared type is the Android SDK's own, so events from shared
// code reach the Android Herald unchanged.

public actual typealias Event = io.github.mkhytarmkhoian.herald.Event

public actual typealias ScreenViewEvent = io.github.mkhytarmkhoian.herald.ScreenViewEvent

public actual typealias Property = io.github.mkhytarmkhoian.herald.Property

public actual typealias UserProperty = io.github.mkhytarmkhoian.herald.UserProperty

public actual typealias AnalyticsValue = io.github.mkhytarmkhoian.herald.AnalyticsValue

public actual typealias Identity = io.github.mkhytarmkhoian.herald.Identity

public actual typealias EventTrackerService = io.github.mkhytarmkhoian.herald.EventTrackerService

public actual typealias PropertyTrackerService =
    io.github.mkhytarmkhoian.herald.PropertyTrackerService

public actual typealias IdentifiableUserService =
    io.github.mkhytarmkhoian.herald.IdentifiableUserService

public actual typealias AnalyticsLifecycleService =
    io.github.mkhytarmkhoian.herald.AnalyticsLifecycleService

public actual typealias ConsentService = io.github.mkhytarmkhoian.herald.ConsentService

public actual typealias ParametersBuilder = io.github.mkhytarmkhoian.herald.ParametersBuilder

public actual fun parameters(block: ParametersBuilder.() -> Unit): Map<String, AnalyticsValue> =
    io.github.mkhytarmkhoian.herald.parameters(block)

public actual object AnalyticsValues {
    public actual fun string(value: String): AnalyticsValue =
        io.github.mkhytarmkhoian.herald.AnalyticsValue.String(value)

    public actual fun int(value: Int): AnalyticsValue =
        io.github.mkhytarmkhoian.herald.AnalyticsValue.Int(value)

    public actual fun long(value: Long): AnalyticsValue =
        io.github.mkhytarmkhoian.herald.AnalyticsValue.Long(value)

    public actual fun float(value: Float): AnalyticsValue =
        io.github.mkhytarmkhoian.herald.AnalyticsValue.Float(value)

    public actual fun double(value: Double): AnalyticsValue =
        io.github.mkhytarmkhoian.herald.AnalyticsValue.Double(value)

    public actual fun bool(value: Boolean): AnalyticsValue =
        io.github.mkhytarmkhoian.herald.AnalyticsValue.Boolean(value)
}
