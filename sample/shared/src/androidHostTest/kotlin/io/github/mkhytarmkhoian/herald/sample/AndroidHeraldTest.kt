package io.github.mkhytarmkhoian.herald.sample

import io.github.mkhytarmkhoian.herald.Herald
import io.github.mkhytarmkhoian.herald.log.AnalyticsLogger
import io.github.mkhytarmkhoian.herald.log.CompositeLogEventTrackerFactory
import io.github.mkhytarmkhoian.herald.log.GenericLogEventTrackerFactory
import io.github.mkhytarmkhoian.herald.log.GenericLogPropertySetterFactory
import io.github.mkhytarmkhoian.herald.log.LogAnalyticsTrackerService
import io.github.mkhytarmkhoian.herald.log.ScreenViewLogEventTrackerFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

/** The shared view model, given the real Android Herald (1.2.0 from Maven Central). */
class AndroidHeraldTest {
    @Test
    fun sharedEventsReachTheAndroidHeraldAsItsOwnTypes() = runTest {
        val lines = mutableListOf<String>()
        val logger = AnalyticsLogger { message -> lines += message }
        val log = LogAnalyticsTrackerService(
            eventTrackerFactory = CompositeLogEventTrackerFactory(
                ScreenViewLogEventTrackerFactory(logger), // claims only Android's ScreenViewEvent
                GenericLogEventTrackerFactory(logger),
            ),
            propertySetterFactory = GenericLogPropertySetterFactory(logger),
        )
        val herald = Herald {
            provider("log", events = log, properties = log)
            dispatcher(Dispatchers.Unconfined)
        }

        val viewModel = CheckoutViewModel(analytics = herald) // no adapter: the same type
        viewModel.opened()
        viewModel.started(plan = "pro", seats = 2)

        lines.forEach(::println)
        assertEquals(
            listOf(
                "[herald] screen  checkout",
                "[herald] event   checkout_started\n" +
                    "    ├─ plan  = pro\n" +
                    "    ├─ price = 9.98\n" +
                    "    └─ seats = 2",
            ),
            lines,
        )
    }
}
