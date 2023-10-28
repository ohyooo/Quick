@file:Suppress("UnstableApiUsage")

import com.nishtahir.CargoBuildTask
import com.nishtahir.CargoExtension
import org.gradle.kotlin.dsl.support.listFilesOrdered


plugins {
    id("com.android.application")
    kotlin("android")
    id("org.mozilla.rust-android-gradle.rust-android")
}

android {
    ndkVersion = sdkDirectory.resolve("ndk").listFilesOrdered().last().name
    namespace = Ext.applicationId
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
    compileSdk = Ext.compileSdk
    defaultConfig {
        applicationId = Ext.applicationId
        minSdk = Ext.minSdk
        targetSdk = Ext.targetSdk
        versionCode = Ext.versionCode
        versionName = Ext.versionName
        signingConfig = signingConfigs.getByName("debug")
    }
    buildTypes {
        debug {
            isMinifyEnabled = false
        }
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "consumer-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        resValues = false
        shaders = false
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    Libs.appImplements.forEach(::implementation)
}

tasks.preBuild.configure {
    dependsOn.add(tasks.withType(CargoBuildTask::class.java))
}

extensions.configure(CargoExtension::class) {
    module = "../rust"
    libname = "rust"
    targets = listOf("arm", "arm64", "x86", "x86_64")
    profile = if (gradle.startParameter.taskNames.any { it.lowercase().contains("debug") }) "debug" else "release"
}

project.afterEvaluate {
    tasks.withType(CargoBuildTask::class)
        .forEach { buildTask ->
            tasks.withType(com.android.build.gradle.tasks.MergeSourceSetFolders::class)
                .configureEach {
                    this.inputs.dir(
                        layout.buildDirectory.dir("rustJniLibs" + File.separatorChar + buildTask.toolchain!!.folder)
                    )
                    this.dependsOn(buildTask)
                }
        }
}
