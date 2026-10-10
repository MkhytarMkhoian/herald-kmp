import org.jetbrains.kotlin.gradle.targets.native.tasks.KotlinNativeSimulatorTest

plugins {
    alias(libs.plugins.kotlin.multiplatform) apply false
}

// iOS tests run in the simulator SIMULATOR_ID names, when set. CI sets it, because its machines
// don't always have the simulator Kotlin picks by default.
subprojects {
    tasks.withType<KotlinNativeSimulatorTest>().configureEach {
        providers.environmentVariable("SIMULATOR_ID").orNull?.let { id -> device.set(id) }
    }
}
