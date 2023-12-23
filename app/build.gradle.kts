@file:Suppress("UnstableApiUsage")

plugins {
    alias(libs.plugins.agp)
    alias(libs.plugins.kgp)
    alias(libs.plugins.jc)
}

android {
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
        // buildConfig = false
        aidl = false
        renderScript = false
        shaders = false
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
}

compose {
    kotlinCompilerPluginArgs.add("suppressKotlinVersionCompatibilityCheck=${libs.versions.kotlin.get()}")
}

dependencies {
    implementation(libs.bundles.compose)
    implementation(project(":kni"))
    implementation(project(":rust"))
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

