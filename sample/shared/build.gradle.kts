plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

// An app's shared module: its events and view models, written once. The iOS framework exports
// herald-kmp, so Swift can create IosAnalytics.
kotlin {
    jvm()
    listOf(iosArm64(), iosSimulatorArm64()).forEach { target ->
        target.binaries.framework {
            baseName = "Shared"
            export(project(":herald-kmp-core"))
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(project(":herald-kmp-core"))
        }
        jvmTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.herald.android.log)
        }
    }
}
