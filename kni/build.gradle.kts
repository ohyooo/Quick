import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType
import org.jetbrains.kotlin.konan.target.KonanTarget.ANDROID_ARM32
import org.jetbrains.kotlin.konan.target.KonanTarget.ANDROID_ARM64
import org.jetbrains.kotlin.konan.target.KonanTarget.ANDROID_X64
import org.jetbrains.kotlin.konan.target.KonanTarget.ANDROID_X86

plugins {
    alias(libs.plugins.kmm)
    id("com.android.library")
}

val jniLibDir = File(project.layout.buildDirectory.get().toString(), arrayOf("generated", "jniLibs").joinToString(File.separator))

val sharedLib_name_prefix = "kni"

kotlin {
    androidTarget()

    val nativeConfigure: KotlinNativeTarget.() -> Unit = {
        binaries {
            sharedLib(sharedLib_name_prefix) {
                linkTask.doLast {
                    copy {
                        from(outputFile)

                        val typeName = if (buildType == NativeBuildType.DEBUG) "Debug" else "Release"
                        val abiDirName = when (target.konanTarget) {
                            ANDROID_ARM32 -> "armeabi-v7a"
                            ANDROID_ARM64 -> "arm64-v8a"
                            ANDROID_X86 -> "x86"
                            ANDROID_X64 -> "x86_64"
                            else -> "unknown"
                        }

                        into(file("$jniLibDir/$typeName/$abiDirName"))
                    }
                }

                afterEvaluate {
                    val preBuild by tasks.getting
                    preBuild.dependsOn(linkTask)
                }
            }
        }
    }

    androidNativeArm32(configure = nativeConfigure)
    androidNativeArm64(configure = nativeConfigure)
    androidNativeX86(configure = nativeConfigure)
    androidNativeX64(configure = nativeConfigure)

    sourceSets {
        val androidNativeArm32Main by getting
        val androidNativeArm64Main by getting
        val androidNativeX86Main by getting
        val androidNativeX64Main by getting

        val nativeMain by creating {
            androidNativeArm32Main.dependsOn(this)
            androidNativeArm64Main.dependsOn(this)
            androidNativeX86Main.dependsOn(this)
            androidNativeX64Main.dependsOn(this)
        }
    }
    applyDefaultHierarchyTemplate()

}

android {
    compileSdk = 34
    namespace = "com.ohyooo.kni"
    defaultConfig {
        minSdk = 21
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    sourceSets {
        getByName("debug").jniLibs.srcDirs("$jniLibDir/Debug")
        getByName("release").jniLibs.srcDirs("$jniLibDir/Release")
    }
}
