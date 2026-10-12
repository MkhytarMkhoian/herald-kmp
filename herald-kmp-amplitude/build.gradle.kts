plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
}

kotlin {
    explicitApi()

    // On Android, the revenue events are herald-amplitude's own, so its factories recognise them.
    android {
        namespace = "io.github.mkhytarmkhoian.herald.kmp.amplitude"
        compileSdk = 36
        minSdk = 23
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
            api(libs.herald.android.amplitude)
        }
    }
}
