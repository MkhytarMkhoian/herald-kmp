package io.github.mkhytarmkhoian.herald.kmp

import kotlin.test.Test
import kotlin.test.assertEquals

class AnalyticsValuesJvmTest {
    @Test
    fun valuesAreTheAndroidSdksOwn() {
        assertEquals(io.github.mkhytarmkhoian.herald.AnalyticsValue.String("dark"), AnalyticsValues.string("dark"))
        assertEquals(io.github.mkhytarmkhoian.herald.AnalyticsValue.Int(3), AnalyticsValues.int(3))
        assertEquals(io.github.mkhytarmkhoian.herald.AnalyticsValue.Long(3L), AnalyticsValues.long(3L))
        assertEquals(io.github.mkhytarmkhoian.herald.AnalyticsValue.Float(1.5f), AnalyticsValues.float(1.5f))
        assertEquals(io.github.mkhytarmkhoian.herald.AnalyticsValue.Double(9.5), AnalyticsValues.double(9.5))
        assertEquals(io.github.mkhytarmkhoian.herald.AnalyticsValue.Boolean(true), AnalyticsValues.bool(true))
    }
}
