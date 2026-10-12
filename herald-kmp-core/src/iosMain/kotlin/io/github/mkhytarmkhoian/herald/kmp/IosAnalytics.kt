package io.github.mkhytarmkhoian.herald.kmp

/**
 * The analytics shared code uses on iOS. It passes every call to [bridge], which the iOS app
 * implements in Swift on top of herald-ios`s `HeraldBridge`:
 *
 * ```swift
 * let herald = Herald(providers: [firebaseProvider()])  // herald-ios, set up as usual
 * let analytics = IosAnalytics(bridge: HeraldKmpBridge(HeraldBridge(herald)))
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
 * What the iOS app implements in Swift, usually by calling herald-ios`s `HeraldBridge`. Plain,
 * synchronous functions, so the Swift side stays simple.
 */
public interface IosHeraldBridge {
    /**
     * [event] is the original object, so Swift can check your shared labels on it:
     * `event.kotlinEvent is PersonalDataEvent`. [needsTranslation] marks a vendor event, such as
     * revenue, that the app's Swift must turn into its herald-ios type.
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
 * A vendor event, such as revenue, that herald-ios knows as its own type. The iOS app's Swift
 * turns it into that type; when it doesn't, the bridge reports it instead of letting the vendor
 * get a plain event without a word. herald-kmp's vendor modules mark their revenue events with it.
 */
public interface IosVendorEvent : Event

/**
 * A parameter value on its way to Swift. herald-ios knows four types, so a Long arrives as an Int
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
