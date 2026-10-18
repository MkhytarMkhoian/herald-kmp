plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.dokka) // before the publish plugin, which looks for its task
    alias(libs.plugins.maven.publish)
}

kotlin {
    explicitApi()

    // On Android, the purchase event is herald-meta's own class.
    android {
        namespace = "io.github.mkhytarmkhoian.herald.kmp.meta"
        compileSdk = 36
        minSdk = 23
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17) // as the Android SDK
        }
    }
    iosArm64()
    iosSimulatorArm64()

    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }

    sourceSets {
        commonMain.dependencies {
            api(project(":herald-kmp-core"))
        }
        androidMain.dependencies {
            api(libs.herald.android.meta)
        }
    }
}
