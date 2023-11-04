object Ext {
    const val applicationId = "com.ohyooo.quick"
    const val minSdk = 28
    const val compileSdk = 34
    const val targetSdk = 34
    const val versionCode = 1
    const val versionName = "1.0"
}

object Libs {

    object Version {
        const val agp = "8.1.2"
        const val kotlin = "1.9.10"
    }

    object Kotlin {
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Version.kotlin}"
        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3"
    }

    object Plugin {
        const val AGP = "com.android.tools.build:gradle:${Version.agp}"
        const val KGP = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Version.kotlin}"
    }

    object AndroidX {
        const val appcompat = "androidx.appcompat:appcompat:1.6.1"
        const val coreKtx = "androidx.core:core-ktx:1.12.0"
        const val fragmentKtx = "androidx.fragment:fragment-ktx:1.6.1"
    }

    object Compose {
        val activity_compose = "androidx.activity:activity-compose:1.8.0"

        val composeVersion = "1.5.4"
        val compilerVersion = "1.5.3"
        val compiler = "androidx.compose.compiler:compiler:$compilerVersion"
        val foundation = "androidx.compose.foundation:foundation:$composeVersion"
        val material = "androidx.compose.material:material:$composeVersion"
        val materialIconsExtended = "androidx.compose.material:material-icons-extended:$composeVersion"
        val runtime = "androidx.compose.runtime:runtime:$composeVersion"
        val ui = "androidx.compose.ui:ui:$composeVersion"
        val uiToolingPreview = "androidx.compose.ui:ui-tooling-preview:$composeVersion"
        val uiTooling = "androidx.compose.ui:ui-tooling:$composeVersion"

        val list = listOf(activity_compose, compiler, foundation, material, materialIconsExtended, runtime, ui, uiToolingPreview, uiTooling)
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
