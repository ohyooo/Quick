@file:Suppress("UnstableApiUsage")

plugins {
    alias(libs.plugins.agp) apply false
    alias(libs.plugins.kgp) apply false
    alias(libs.plugins.ks) apply false
    alias(libs.plugins.jc) apply false
    alias(libs.plugins.mozilla.rust) apply false
    alias(libs.plugins.kmm) apply false
}

allprojects {
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "21"
            freeCompilerArgs = freeCompilerArgs + listOf(
                "-Xbackend-threads=12",
                "-opt-in=kotlin.RequiresOptIn",
                "-Xcontext-receivers",
                "-jvm-target=21",
            )
        }
    }
}
