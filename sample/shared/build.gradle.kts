plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
}

// An app's shared module: its events and view models, written once. The iOS framework exports
// herald-kmp, so Swift can create IosAnalytics and read Kotlin revenue events.
kotlin {
    android {
        namespace = "io.github.mkhytarmkhoian.herald.sample"
        compileSdk = 36
        minSdk = 23
        withHostTestBuilder {}
    }
    listOf(iosArm64(), iosSimulatorArm64()).forEach { target ->
        target.binaries.framework {
            baseName = "Shared"
            export(project(":herald-kmp-core"))
            export(project(":herald-kmp-amplitude"))
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(project(":herald-kmp-core"))
            api(project(":herald-kmp-amplitude"))
        }
        getByName("androidHostTest").dependencies {
            implementation(kotlin("test"))
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.herald.android.log)
        }
    }
}
