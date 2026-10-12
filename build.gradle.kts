import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.MavenPublishBaseExtension
import org.gradle.api.publish.maven.tasks.PublishToMavenRepository
import org.gradle.plugins.signing.SigningExtension
import org.jetbrains.dokka.gradle.DokkaExtension
import org.jetbrains.kotlin.gradle.targets.native.tasks.KotlinNativeSimulatorTest

plugins {
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.android.kotlin.multiplatform.library) apply false
    alias(libs.plugins.maven.publish) apply false
    alias(libs.plugins.dokka) apply false
    alias(libs.plugins.binary.compatibility.validator)
}

// Records the public API of each published module, on Android/JVM and on iOS, in its api/ folder.
// `check` fails when the code no longer matches the record, so a change to the API is never by
// accident: run `./gradlew apiDump` and commit the new record when the change is meant.
apiValidation {
    ignoredProjects += "shared"
    @OptIn(kotlinx.validation.ExperimentalBCVApi::class)
    klib {
        enabled = true
    }
}

// iOS tests run in the simulator SIMULATOR_ID names, when set. CI sets it, because its machines
// don't always have the simulator Kotlin picks by default.
subprojects {
    tasks.withType<KotlinNativeSimulatorTest>().configureEach {
        providers.environmentVariable("SIMULATOR_ID").orNull?.let { id -> device.set(id) }
    }
}

// Maven Central, as in the Android repo: every module that applies the publish plugin gets the
// same POM, signing and Dokka javadoc. The version comes from the release tag (-Pversion=...).
subprojects {
    pluginManager.withPlugin("com.vanniktech.maven.publish") {
        extensions.configure<MavenPublishBaseExtension> {
            publishToMavenCentral()
            signAllPublications()
            coordinates(project.group.toString(), project.name, project.version.toString())
            configureBasedOnAppliedPlugins(
                javadocJar = JavadocJar.Dokka("dokkaGeneratePublicationHtml"),
            )

            pom {
                name.set(project.name)
                description.set(
                    "Herald for Kotlin Multiplatform: one analytics API in shared Kotlin.",
                )
                inceptionYear.set("2026")
                url.set("https://github.com/MkhytarMkhoian/herald-kmp")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                        distribution.set("repo")
                    }
                }
                developers {
                    developer {
                        id.set("MkhytarMkhoian")
                        name.set("Mkhytar Mkhoian")
                        url.set("https://github.com/MkhytarMkhoian")
                    }
                }
                scm {
                    url.set("https://github.com/MkhytarMkhoian/herald-kmp")
                    connection.set("scm:git:git://github.com/MkhytarMkhoian/herald-kmp.git")
                    developerConnection.set(
                        "scm:git:ssh://git@github.com/MkhytarMkhoian/herald-kmp.git",
                    )
                }
            }
        }

        // Only an upload needs the key: publishToMavenLocal works without one.
        extensions.configure<SigningExtension> {
            setRequired({ gradle.taskGraph.allTasks.any { it is PublishToMavenRepository } })
        }
    }

    // Each symbol in the API reference links to its source at the release tag.
    pluginManager.withPlugin("org.jetbrains.dokka") {
        extensions.configure<DokkaExtension> {
            dokkaSourceSets.configureEach {
                includes.from("Module.md")
                val sourceSet = name
                sourceLink {
                    localDirectory.set(file("src/$sourceSet/kotlin"))
                    remoteUrl(
                        "https://github.com/MkhytarMkhoian/herald-kmp/tree/v${project.version}/" +
                            "${project.name}/src/$sourceSet/kotlin",
                    )
                    remoteLineSuffix.set("#L")
                }
            }
        }
    }
}
