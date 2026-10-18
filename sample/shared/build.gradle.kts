plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
}

// An app's shared module: its events and view models, written once. The iOS framework exports
// herald-kmp, so Swift can use IosAnalytics and the revenue events.
kotlin {
    android {
        namespace = "io.github.mkhytarmkhoian.herald.sample"
        compileSdk = 36
        minSdk = 23
        withHostTestBuilder {}
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
        }
    }
    listOf(iosArm64(), iosSimulatorArm64()).forEach { target ->
        target.binaries.framework {
            baseName = "Shared"
            export(project(":herald-kmp-core"))
            export(project(":herald-kmp-amplitude"))
            export(project(":herald-kmp-adjust"))
            export(project(":herald-kmp-appsflyer"))
            export(project(":herald-kmp-meta"))
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(project(":herald-kmp-core"))
            api(project(":herald-kmp-amplitude"))
            api(project(":herald-kmp-adjust"))
            api(project(":herald-kmp-appsflyer"))
            api(project(":herald-kmp-meta"))
        }
        getByName("androidHostTest").dependencies {
            implementation(kotlin("test"))
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.herald.android.log)
        }
    }
}
