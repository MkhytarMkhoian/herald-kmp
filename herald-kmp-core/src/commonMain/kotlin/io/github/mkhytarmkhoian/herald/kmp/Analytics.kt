package io.github.mkhytarmkhoian.herald.kmp

import kotlin.jvm.JvmInline

// The types shared code uses. On Android, each one is the Android SDK's own type. On iOS, each one
// is a Kotlin copy, and IosAnalytics sends the calls to herald-ios.

/** Something that happened: a name and its parameters. */
public expect interface Event {
    public val name: String

    /** Empty unless the event overrides it, as on Android. */
    public open val parameters: Map<String, AnalyticsValue>
}

/** An [Event] that is a screen view. */
public expect interface ScreenViewEvent : Event

/** Something about the session, such as the app's theme. */
public expect interface Property {
    public val name: String
    public val value: AnalyticsValue
}

/** Something about the person, kept by vendors on their profile. */
public expect interface UserProperty : Property

/** A parameter value that keeps its type all the way to the vendor. */
public expect sealed interface AnalyticsValue {
    @JvmInline
    public value class String(public val value: kotlin.String) : AnalyticsValue

    @JvmInline
    public value class Int(public val value: kotlin.Int) : AnalyticsValue

    @JvmInline
    public value class Long(public val value: kotlin.Long) : AnalyticsValue

    @JvmInline
    public value class Float(public val value: kotlin.Float) : AnalyticsValue

    @JvmInline
    public value class Double(public val value: kotlin.Double) : AnalyticsValue

    @JvmInline
    public value class Boolean(public val value: kotlin.Boolean) : AnalyticsValue
}

/** The signed-in user. */
public expect class Identity(userId: String) {
    public val userId: String
}

public expect fun interface EventTrackerService {
    public suspend fun track(event: Event)
}

public expect fun interface PropertyTrackerService {
    public suspend fun set(property: Property)
}

public expect interface IdentifiableUserService {
    public suspend fun identify(identity: Identity)

    public suspend fun reset()
}

public expect interface AnalyticsLifecycleService {
    public suspend fun start()

    /** Does nothing unless the service overrides it, as on Android. */
    public open suspend fun flush()
}

public expect interface ConsentService {
    public suspend fun setEnabled(enabled: Boolean)
}

/**
 * Builds an event's parameters, keeping each value's type:
 *
 * ```kotlin
 * override val parameters = parameters {
 *     put("plan", plan)
 *     put("seats", seats)
 * }
 * ```
 */
public expect fun parameters(block: ParametersBuilder.() -> Unit): Map<String, AnalyticsValue>

public expect class ParametersBuilder {
    public fun put(name: String, value: String)

    public fun put(name: String, value: Int)

    public fun put(name: String, value: Long)

    public fun put(name: String, value: Float)

    public fun put(name: String, value: Double)

    public fun put(name: String, value: Boolean)
}

/**
 * Makes an [AnalyticsValue], for a [Property]'s value:
 *
 * ```kotlin
 * override val value = AnalyticsValues.string(if (dark) "dark" else "light")
 * ```
 *
 * Shared code can't write `AnalyticsValue.String("dark")`: Kotlin doesn't allow it when the type
 * comes from the Android SDK.
 */
public expect object AnalyticsValues {
    public fun string(value: String): AnalyticsValue

    public fun int(value: Int): AnalyticsValue

    public fun long(value: Long): AnalyticsValue

    public fun float(value: Float): AnalyticsValue

    public fun double(value: Double): AnalyticsValue

    public fun bool(value: Boolean): AnalyticsValue
}
