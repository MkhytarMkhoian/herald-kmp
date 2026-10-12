package io.github.mkhytarmkhoian.herald.kmp

/**
 * The analytics shared code uses on iOS. It passes every call to [bridge], which sends it to
 * herald-ios:
 *
 * ```swift
 * let herald = Herald(providers: [amplitudeProvider()])  // herald-ios, set up as usual
 * let bridge = HeraldBridge(herald, translate: revenueEvent, errorReporter: report)
 * let analytics = IosAnalytics(bridge: HeraldKmpBridge(bridge))
 * ```
 */
public class IosAnalytics(private val bridge: IosHeraldBridge) :
    EventTrackerService,
    PropertyTrackerService,
    IdentifiableUserService,
    AnalyticsLifecycleService,
    ConsentService {

    override suspend fun track(event: Event) {
        bridge.track(
            name = event.name,
            parameters = event.parameters.mapValues { (_, value) -> value.toBridge() },
            isScreenView = event is ScreenViewEvent,
            needsTranslation = event is IosVendorEvent,
            event = event,
        )
    }

    override suspend fun set(property: Property) {
        bridge.set(property.name, property.value.toBridge(), property is UserProperty)
    }

    override suspend fun identify(identity: Identity): Unit = bridge.identify(identity.userId)

    override suspend fun reset(): Unit = bridge.reset()

    override suspend fun start(): Unit = bridge.start()

    override suspend fun flush(): Unit = bridge.flush()

    override suspend fun setEnabled(enabled: Boolean): Unit = bridge.setEnabled(enabled)
}

/**
 * Implemented in Swift by the iOS app (herald-kmp's `HeraldKmpBridge.swift`, ready to copy), on top
 * of herald-ios's `HeraldBridge`. Plain functions, so the Swift side stays simple.
 */
public interface IosHeraldBridge {
    /**
     * [event] is the original object, so Swift can check your labels on it. [needsTranslation]
     * marks an [IosVendorEvent].
     */
    public fun track(
        name: String,
        parameters: Map<String, IosBridgeValue>,
        isScreenView: Boolean,
        needsTranslation: Boolean,
        event: Event,
    )

    public fun set(name: String, value: IosBridgeValue, isUserProperty: Boolean)

    public fun identify(userId: String)

    public fun reset()

    public fun start()

    public fun flush()

    public fun setEnabled(enabled: Boolean)
}

/**
 * A vendor event, such as revenue, that the iOS app must turn into its herald-ios type. If the app
 * doesn't, `HeraldBridge` reports it, so revenue isn't lost without a word.
 */
public interface IosVendorEvent : Event

/**
 * A parameter value on its way to Swift. herald-ios has four types, so a Long arrives as an Int
 * (64-bit in Swift) and a Float as a Double.
 */
public class IosBridgeValue internal constructor(
    public val type: IosBridgeValueType,
    public val string: String = "",
    public val int: Long = 0,
    public val double: Double = 0.0,
    public val bool: Boolean = false,
)

public enum class IosBridgeValueType { STRING, INT, DOUBLE, BOOL }

internal fun AnalyticsValue.toBridge(): IosBridgeValue = when (this) {
    is AnalyticsValue.String -> IosBridgeValue(IosBridgeValueType.STRING, string = value)
    is AnalyticsValue.Int -> IosBridgeValue(IosBridgeValueType.INT, int = value.toLong())
    is AnalyticsValue.Long -> IosBridgeValue(IosBridgeValueType.INT, int = value)
    is AnalyticsValue.Float -> IosBridgeValue(IosBridgeValueType.DOUBLE, double = value.toDouble())
    is AnalyticsValue.Double -> IosBridgeValue(IosBridgeValueType.DOUBLE, double = value)
    is AnalyticsValue.Boolean -> IosBridgeValue(IosBridgeValueType.BOOL, bool = value)
}
