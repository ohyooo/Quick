@file:Suppress("UnstableApiUsage")

import com.nishtahir.CargoBuildTask
import org.gradle.kotlin.dsl.support.listFilesOrdered

plugins {
    id("com.android.library")
    alias(libs.plugins.kgp)
    alias(libs.plugins.mozilla.rust)
}

android {
    namespace = "com.ohyooo.rust"
    ndkVersion = sdkDirectory.resolve("ndk").listFilesOrdered().last().name
    compileSdk = libs.versions.compile.sdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.min.sdk.get().toInt()
        consumerProguardFile("consumer-rules.pro")
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

dependencies {

}

cargo {
    module = "./src/main/rust"
    libname = "rust"
    targets = listOf("arm", "arm64", "x86", "x86_64")
    profile = if (gradle.startParameter.taskNames.any { it.lowercase().contains("debug") }) "debug" else "release"
    prebuiltToolchains = true
}


tasks.preBuild.configure {
    dependsOn.add(tasks.withType(CargoBuildTask::class.java))
}

tasks.configureEach {
    if ((this.name == "mergeDebugJniLibFolders" || this.name == "mergeReleaseJniLibFolders")) {
        this.dependsOn("cargoBuild")
        this.inputs.dir(layout.buildDirectory.dir("rustJniLibs/android"))
    }
}
