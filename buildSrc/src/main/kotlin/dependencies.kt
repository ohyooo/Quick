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

    object Version {
        const val agp = "8.0.0"
        const val kotlin = "1.8.21"
    }

    object Kotlin {
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Version.kotlin}"
        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4"
    }

    object Plugin {
        const val AGP = "com.android.tools.build:gradle:${Version.agp}"
        const val KGP = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Version.kotlin}"
    }

    object AndroidX {
        const val appcompat = "androidx.appcompat:appcompat:1.6.1"
        const val coreKtx = "androidx.core:core-ktx:1.10.0"
        const val fragmentKtx = "androidx.fragment:fragment-ktx:1.5.7"
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
