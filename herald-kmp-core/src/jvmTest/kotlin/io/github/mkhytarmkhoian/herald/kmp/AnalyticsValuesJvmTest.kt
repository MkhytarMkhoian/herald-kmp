package io.github.mkhytarmkhoian.herald.kmp

import io.github.mkhytarmkhoian.herald.AnalyticsValue as AndroidValue
import kotlin.test.Test
import kotlin.test.assertEquals

class AnalyticsValuesJvmTest {
    @Test
    fun valuesAreTheAndroidSdksOwn() {
        assertEquals(AndroidValue.String("dark"), AnalyticsValues.string("dark"))
        assertEquals(AndroidValue.Int(3), AnalyticsValues.int(3))
        assertEquals(AndroidValue.Long(3L), AnalyticsValues.long(3L))
        assertEquals(AndroidValue.Float(1.5f), AnalyticsValues.float(1.5f))
        assertEquals(AndroidValue.Double(9.5), AnalyticsValues.double(9.5))
        assertEquals(AndroidValue.Boolean(true), AnalyticsValues.bool(true))
    }
}
