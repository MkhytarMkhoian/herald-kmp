package io.github.mkhytarmkhoian.herald.kmp

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame

private interface PersonalDataEvent : Event

private class ProfileOpened : PersonalDataEvent {
    override val name = "profile_opened"
}

private object HomeViewed : ScreenViewEvent {
    override val name = "home"
}

private class CheckoutStarted : Event {
    override val name = "checkout_started"
    override val parameters = parameters {
        put("plan", "pro")
        put("seats", 3)
        put("users", 3_000_000_000L)
        put("discount", 0.5f)
        put("price", 9.5)
        put("trial", true)
    }
}

private class Purchase : IosVendorEvent {
    override val name = "purchase"
}

private class AppTheme : Property {
    override val name = "app_theme"
    override val value = AnalyticsValues.string("dark")
}

private class Plan : UserProperty {
    override val name = "plan"
    override val value = AnalyticsValues.string("pro")
}

class IosAnalyticsTest {
    private val bridge = RecordingBridge()
    private val analytics = IosAnalytics(bridge)

    @Test
    fun parametersKeepTheirTypes() = runTest {
        analytics.track(CheckoutStarted())

        val values = bridge.tracked.single().parameters.mapValues { (_, value) -> value.describe() }
        assertEquals(
            mapOf(
                "plan" to "STRING pro",
                "seats" to "INT 3",
                "users" to "INT 3000000000",
                "discount" to "DOUBLE 0.5",
                "price" to "DOUBLE 9.5",
                "trial" to "BOOL true",
            ),
            values,
        )
    }

    @Test
    fun aScreenViewIsFlagged() = runTest {
        analytics.track(HomeViewed)
        analytics.track(CheckoutStarted())

        assertEquals(listOf(true, false), bridge.tracked.map { call -> call.isScreenView })
    }

    @Test
    fun aVendorEventAsksForTranslation() = runTest {
        analytics.track(Purchase())
        analytics.track(CheckoutStarted())

        assertEquals(listOf(true, false), bridge.tracked.map { call -> call.needsTranslation })
    }

    @Test
    fun theOriginalEventCrossesTheBridge() = runTest {
        val event = ProfileOpened()

        analytics.track(event)

        assertSame(event, bridge.tracked.single().event) // so Swift can check PersonalDataEvent
    }

    @Test
    fun propertiesSayWhetherTheyAreAboutThePerson() = runTest {
        analytics.set(AppTheme())
        analytics.set(Plan())

        assertEquals(
            listOf("app_theme STRING dark false", "plan STRING pro true"),
            bridge.properties,
        )
    }

    @Test
    fun theOtherCallsReachTheBridge() = runTest {
        analytics.start()
        analytics.setEnabled(true)
        analytics.identify(Identity("user-42"))
        analytics.flush()
        analytics.reset()

        assertEquals(
            listOf("start", "enabled true", "identify user-42", "flush", "reset"),
            bridge.calls,
        )
    }
}

private fun IosBridgeValue.describe(): String = when (type) {
    IosBridgeValueType.STRING -> "STRING $string"
    IosBridgeValueType.INT -> "INT $int"
    IosBridgeValueType.DOUBLE -> "DOUBLE $double"
    IosBridgeValueType.BOOL -> "BOOL $bool"
}

private class RecordingBridge : IosHeraldBridge {
    class Tracked(
        val name: String,
        val parameters: Map<String, IosBridgeValue>,
        val isScreenView: Boolean,
        val needsTranslation: Boolean,
        val event: Event,
    )

    val tracked = mutableListOf<Tracked>()
    val properties = mutableListOf<String>()
    val calls = mutableListOf<String>()

    override fun track(
        name: String,
        parameters: Map<String, IosBridgeValue>,
        isScreenView: Boolean,
        needsTranslation: Boolean,
        event: Event,
    ) {
        tracked += Tracked(name, parameters, isScreenView, needsTranslation, event)
    }

    override fun set(name: String, value: IosBridgeValue, isUserProperty: Boolean) {
        properties += "$name ${value.describe()} $isUserProperty"
    }

    override fun identify(userId: String) {
        calls += "identify $userId"
    }

    override fun reset() {
        calls += "reset"
    }

    override fun start() {
        calls += "start"
    }

    override fun flush() {
        calls += "flush"
    }

    override fun setEnabled(enabled: Boolean) {
        calls += "enabled $enabled"
    }
}
