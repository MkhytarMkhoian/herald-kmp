package io.github.mkhytarmkhoian.herald.sample

import io.github.mkhytarmkhoian.herald.kmp.AnalyticsValue
import io.github.mkhytarmkhoian.herald.kmp.AnalyticsValues
import io.github.mkhytarmkhoian.herald.kmp.Event
import io.github.mkhytarmkhoian.herald.kmp.EventTrackerService
import io.github.mkhytarmkhoian.herald.kmp.Property
import io.github.mkhytarmkhoian.herald.kmp.PropertyTrackerService
import io.github.mkhytarmkhoian.herald.kmp.parameters

/** A label: events about a person. Some vendors must never get them. */
public interface PersonalDataEvent : Event

public class ProfileOpened(userId: String) : PersonalDataEvent {
    override val name: String = "profile_opened"
    override val parameters: Map<String, AnalyticsValue> = parameters { put("user_id", userId) }
}

public class AppTheme(dark: Boolean) : Property {
    override val name: String = "app_theme"
    override val value: AnalyticsValue = AnalyticsValues.string(if (dark) "dark" else "light")
}

public class ProfileViewModel(
    private val analytics: EventTrackerService,
    private val properties: PropertyTrackerService,
) {
    public suspend fun opened(userId: String): Unit = analytics.track(ProfileOpened(userId))

    public suspend fun themeChanged(dark: Boolean): Unit = properties.set(AppTheme(dark))
}
