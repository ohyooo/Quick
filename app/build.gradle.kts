@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = Ext.applicationId
    signingConfigs {
        getByName("debug") {
            storeFile = file("..\\signkey.jks")
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
