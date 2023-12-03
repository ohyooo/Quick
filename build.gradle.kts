@file:Suppress("UnstableApiUsage")

plugins {
    alias(libs.plugins.agp) apply false
    alias(libs.plugins.kgp) apply false
    alias(libs.plugins.ks) apply false
    alias(libs.plugins.jc) apply false
}

allprojects {
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "19"
            freeCompilerArgs = freeCompilerArgs + listOf(
                "-Xbackend-threads=12",
                "-opt-in=kotlin.RequiresOptIn",
                "-Xcontext-receivers",
                "-jvm-target=19",
            )
        }
    }
}
