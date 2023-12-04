@file:Suppress("UnstableApiUsage")

import com.nishtahir.CargoBuildTask
import org.gradle.kotlin.dsl.support.listFilesOrdered


plugins {
    id("com.android.application")
    kotlin("android")
    alias(libs.plugins.jc)
    id("org.mozilla.rust-android-gradle.rust-android")
}

android {
    ndkVersion = sdkDirectory.resolve("ndk").listFilesOrdered().last().name
    signingConfigs {
        getByName("debug") {
            storeFile = file("signkey.jks")
            storePassword = "123456"
            keyPassword = "123456"
            keyAlias = "demo"

            enableV3Signing = true
            enableV4Signing = true
        }
    }
    namespace = libs.versions.application.id.get()
    compileSdk = libs.versions.compile.sdk.get().toInt()
    defaultConfig {
        applicationId = libs.versions.application.id.get()
        minSdk = libs.versions.min.sdk.get().toInt()
        targetSdk = libs.versions.target.sdk.get().toInt()
        versionCode = libs.versions.version.code.get().toInt()
        versionName = libs.versions.target.sdk.get() + hashTag
        proguardFile("proguard-rules.pro")
        signingConfig = signingConfigs.getByName("debug")
    }
    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-defaults.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    kotlinOptions {
        jvmTarget = "21"
    }
    buildFeatures {
        compose = true
        // Disable unused AGP features
        buildConfig = false
        aidl = false
        renderScript = false
        resValues = false
        shaders = false
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "21"
    }
}

dependencies {
    implementation(libs.bundles.compose)
    implementation(project(":kni"))
}

val hashTag: String
    get() {
        if (!File(rootDir.path + "/.git").exists()) return ""
        return ProcessBuilder(listOf("git", "rev-parse", "--short", "HEAD"))
            .directory(rootDir)
            .redirectOutput(ProcessBuilder.Redirect.PIPE)
            .redirectError(ProcessBuilder.Redirect.PIPE)
            .start()
            .apply { waitFor(5, TimeUnit.SECONDS) }
            .run {
                val error = errorStream.bufferedReader().readText().trim()
                if (error.isNotEmpty()) {
                    ""
                } else {
                    "-" + inputStream.bufferedReader().readText().trim()
                }
            }
    }

cargo {
    module = "../rust"
    libname = "rust"
    targets = listOf("arm", "arm64", "x86", "x86_64")
    profile = if (gradle.startParameter.taskNames.any { it.lowercase().contains("debug") }) "debug" else "release"
    prebuiltToolchains = true
}


tasks.preBuild.configure {
    dependsOn.add(tasks.withType(CargoBuildTask::class.java))
}

tasks.whenObjectAdded {
    if ((this.name == "mergeDebugJniLibFolders" || this.name == "mergeReleaseJniLibFolders")) {
        this.dependsOn("cargoBuild")
        this.inputs.dir(layout.buildDirectory.dir("rustJniLibs/android"))
    }
}
