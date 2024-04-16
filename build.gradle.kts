@file:Suppress("UnstableApiUsage")

import java.io.ByteArrayOutputStream
import java.nio.charset.Charset

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

abstract class GitVersionValueSource : ValueSource<String, ValueSourceParameters.None> {
    @get:Inject
    abstract val execOperations: ExecOperations

    override fun obtain(): String {
        val output = ByteArrayOutputStream()
        val error = ByteArrayOutputStream()
        execOperations.exec {
            commandLine("git rev-parse --short HEAD".split(" "))
            standardOutput = output
            errorOutput = error
        }

        return if (error.toByteArray().isNotEmpty()) {
            ""
        } else {
            "-" + String(output.toByteArray(), Charset.defaultCharset()).trim()
        }
    }
}
