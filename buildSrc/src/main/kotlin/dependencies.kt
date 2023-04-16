object Ext {
    const val applicationId = "com.ohyooo.quick"
    const val minSdk = 28
    const val compileSdk = 33
    const val buildToolsVersion = "33.0.2"
    const val targetSdk = 33
    const val versionCode = 1
    const val versionName = "1.0"
}

object Libs {
    const val kotlinVersion = "1.8.20"

    object Kotlin {
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion"
        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4"
    }

    object Plugin {
        const val AGP = "com.android.tools.build:gradle:8.0.0"
        const val KGP = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
    }

    object AndroidX {
        const val appcompat = "androidx.appcompat:appcompat:1.6.1"
        const val coreKtx = "androidx.core:core-ktx:1.10.0"
        const val fragmentKtx = "androidx.fragment:fragment-ktx:1.5.6"
    }

    val appImplements = arrayOf(
        AndroidX.appcompat,
        AndroidX.coreKtx,
        AndroidX.fragmentKtx,
        Kotlin.coroutines,
        Kotlin.stdlib,
    )

    val deps: List<String> = mutableSetOf<String>().apply {
        addAll(appImplements)
    }.toList()
}
