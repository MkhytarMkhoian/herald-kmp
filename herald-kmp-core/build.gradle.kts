plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.dokka) // before the publish plugin, which looks for its task
    alias(libs.plugins.maven.publish)
}

kotlin {
    explicitApi()

    // Android apps use the JVM target. There, each type is the Android SDK's own, so an event
    // written in shared code is an Android Herald event.
    jvm {
        // The same as the Android SDK, so any Android app's build can read the classes.
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
        }
    }
    iosArm64()
    iosSimulatorArm64()

    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }

    sourceSets {
        commonMain.dependencies {
            api(libs.kotlinx.coroutines.core)
        }
        jvmMain.dependencies {
            api(libs.herald.android.core)
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.kotlinx.coroutines.test)
        }
    }
}
