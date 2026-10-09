package io.github.mkhytarmkhoian.herald.kmp

// On iOS, each shared type is a Kotlin twin of the Android SDK's, so shared code reads the same on
// both. IosAnalytics passes the calls on to herald-ios.

public actual interface Event {
    public actual val name: String
    public actual val parameters: Map<String, AnalyticsValue> get() = emptyMap()
}

public actual interface ScreenViewEvent : Event

public actual interface Property {
    public actual val name: String
    public actual val value: AnalyticsValue
}

public actual interface UserProperty : Property

public actual sealed interface AnalyticsValue {
    public actual value class String(public actual val value: kotlin.String) : AnalyticsValue

    public actual value class Int(public actual val value: kotlin.Int) : AnalyticsValue

    public actual value class Long(public actual val value: kotlin.Long) : AnalyticsValue

    public actual value class Float(public actual val value: kotlin.Float) : AnalyticsValue

    public actual value class Double(public actual val value: kotlin.Double) : AnalyticsValue

    public actual value class Boolean(public actual val value: kotlin.Boolean) : AnalyticsValue
}

public actual class Identity actual constructor(public actual val userId: String) {
    override fun equals(other: Any?): Boolean = other is Identity && other.userId == userId

    override fun hashCode(): Int = userId.hashCode()

    override fun toString(): String = "Identity(…)"
}

public actual fun interface EventTrackerService {
    public actual suspend fun track(event: Event)
}

public actual fun interface PropertyTrackerService {
    public actual suspend fun set(property: Property)
}

public actual interface IdentifiableUserService {
    public actual suspend fun identify(identity: Identity)

    public actual suspend fun reset()
}

public actual interface AnalyticsLifecycleService {
    public actual suspend fun start()

    public actual suspend fun flush() {}
}

public actual interface ConsentService {
    public actual suspend fun setEnabled(enabled: Boolean)
}

public actual fun parameters(block: ParametersBuilder.() -> Unit): Map<String, AnalyticsValue> =
    ParametersBuilder().apply(block).build()

public actual class ParametersBuilder internal constructor() {
    private val values = mutableMapOf<String, AnalyticsValue>()

    public actual fun put(name: String, value: String) {
        values[name] = AnalyticsValue.String(value)
    }

    public actual fun put(name: String, value: Int) {
        values[name] = AnalyticsValue.Int(value)
    }

    public actual fun put(name: String, value: Long) {
        values[name] = AnalyticsValue.Long(value)
    }

    public actual fun put(name: String, value: Float) {
        values[name] = AnalyticsValue.Float(value)
    }

    public actual fun put(name: String, value: Double) {
        values[name] = AnalyticsValue.Double(value)
    }

    public actual fun put(name: String, value: Boolean) {
        values[name] = AnalyticsValue.Boolean(value)
    }

    internal fun build(): Map<String, AnalyticsValue> = values.toMap()
}

public actual object AnalyticsValues {
    public actual fun string(value: String): AnalyticsValue = AnalyticsValue.String(value)

    public actual fun int(value: Int): AnalyticsValue = AnalyticsValue.Int(value)

    public actual fun long(value: Long): AnalyticsValue = AnalyticsValue.Long(value)

    public actual fun float(value: Float): AnalyticsValue = AnalyticsValue.Float(value)

    public actual fun double(value: Double): AnalyticsValue = AnalyticsValue.Double(value)

    public actual fun bool(value: Boolean): AnalyticsValue = AnalyticsValue.Boolean(value)
}
