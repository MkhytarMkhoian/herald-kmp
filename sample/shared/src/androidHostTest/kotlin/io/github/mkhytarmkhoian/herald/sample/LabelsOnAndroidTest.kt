package io.github.mkhytarmkhoian.herald.sample

import io.github.mkhytarmkhoian.herald.EventTrackerService
import io.github.mkhytarmkhoian.herald.Herald
import io.github.mkhytarmkhoian.herald.log.AnalyticsLogger
import io.github.mkhytarmkhoian.herald.log.GenericLogEventTrackerFactory
import io.github.mkhytarmkhoian.herald.log.GenericLogPropertySetterFactory
import io.github.mkhytarmkhoian.herald.log.LogAnalyticsTrackerService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

/** The shared label, checked by Android code the usual way: `event is PersonalDataEvent`. */
class LabelsOnAndroidTest {
    @Test
    fun aSharedLabelWorksInAndroidCode() = runTest {
        val all = mutableListOf<String>()
        val noPersonalData = mutableListOf<String>()
        val allLog = log { message -> all += message }
        val filteredLog = log { message -> noPersonalData += message }
        val herald = Herald {
            provider("all", events = allLog, properties = allLog)
            provider(
                "no-personal-data",
                // Android code, no bridge: the shared label is an ordinary Kotlin type.
                events = EventTrackerService { event ->
                    if (event !is PersonalDataEvent) filteredLog.track(event)
                },
                properties = filteredLog,
            )
            dispatcher(Dispatchers.Unconfined)
        }

        val viewModel = ProfileViewModel(analytics = herald, properties = herald)
        viewModel.opened(userId = "u42")
        viewModel.themeChanged(dark = true)

        assertEquals(
            listOf(
                "[herald] event   profile_opened\n    └─ user_id = u42",
                "[herald] prop    app_theme = dark",
            ),
            all,
        )
        assertEquals(listOf("[herald] prop    app_theme = dark"), noPersonalData)
    }

    private fun log(logger: AnalyticsLogger) = LogAnalyticsTrackerService(
        eventTrackerFactory = GenericLogEventTrackerFactory(logger),
        propertySetterFactory = GenericLogPropertySetterFactory(logger),
    )
}
