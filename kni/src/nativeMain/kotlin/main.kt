import kotlinx.cinterop.COpaquePointer
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.CPointerVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.alloc
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.cstr
import kotlinx.cinterop.get
import kotlinx.cinterop.invoke
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.pointed
import kotlinx.cinterop.ptr
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.sizeOf
import kotlinx.cinterop.staticCFunction
import kotlinx.cinterop.value
import platform.android.ANDROID_LOG_INFO
import platform.android.JNIEnvVar
import platform.android.JNINativeMethod
import platform.android.JNI_OK
import platform.android.JNI_VERSION_1_6
import platform.android.JavaVMVar
import platform.android.__android_log_print
import platform.android.jint
import platform.android.jobject
import platform.android.jstring
import kotlin.experimental.ExperimentalNativeApi

// https://www.bennyhuo.com/2020/04/19/kotlin-native-android-mpp/


@OptIn(ExperimentalNativeApi::class)
@CName("Java_com_ohyooo_kni_Kni_sayHello")
fun sayHello() {
    __android_log_print(ANDROID_LOG_INFO.toInt(), "Kn", "Hello %s", "Native")
}

@OptIn(ExperimentalForeignApi::class, ExperimentalNativeApi::class)
@CName("Java_com_ohyooo_kni_Kni_stringFromJNI")
fun stringFromJNI(env: CPointer<JNIEnvVar>, thiz: jobject): jstring {
    memScoped {
        return env.pointed.pointed!!.NewStringUTF!!.invoke(env, "This is from Kotlin Native!!".cstr.ptr)!!
    }
}

@OptIn(ExperimentalNativeApi::class, ExperimentalForeignApi::class)
@CName("Java_com_ohyooo_kni_Kni_callJava")
fun callJava(env: CPointer<JNIEnvVar>, thiz: jobject): jstring {
    memScoped {
        val jniEnvVal = env.pointed.pointed!!
        val jclass = jniEnvVal.GetObjectClass!!.invoke(env, thiz)
        val methodId = jniEnvVal.GetMethodID!!.invoke(
            env, jclass,
            "callFromNative".cstr.ptr, "()Ljava/lang/String;".cstr.ptr
        )
        return jniEnvVal.CallObjectMethodA!!.invoke(env, thiz, methodId, null) as jstring
    }
}

fun sayHello2() {
    __android_log_print(ANDROID_LOG_INFO.toInt(), "Kn", "Hello %s", "Native")
}

@OptIn(ExperimentalNativeApi::class, ExperimentalForeignApi::class)
@CName("JNI_OnLoad")
fun JNI_OnLoad(vm: CPointer<JavaVMVar>, preserved: COpaquePointer): jint {
    return memScoped {
        val envStorage = alloc<CPointerVar<JNIEnvVar>>()
        val vmValue = vm.pointed.pointed!!
        val result = vmValue.GetEnv!!(vm, envStorage.ptr.reinterpret(), JNI_VERSION_1_6)
        __android_log_print(ANDROID_LOG_INFO.toInt(), "Kn", "JNI_OnLoad")
        if (result == JNI_OK) {
            val env = envStorage.pointed!!.pointed!!
            val jclass = env.FindClass!!(envStorage.value, "com/ohyooo/kni/Kni".cstr.ptr)

            val jniMethod = allocArray<JNINativeMethod>(1)
            jniMethod[0].fnPtr = staticCFunction(::sayHello2)
            jniMethod[0].name = "sayHello2".cstr.ptr
            jniMethod[0].signature = "()V".cstr.ptr
            env.RegisterNatives!!(envStorage.value, jclass, jniMethod, 1)

            __android_log_print(ANDROID_LOG_INFO.toInt(), "Kn", "register say hello2, %d, %d", sizeOf<CPointerVar<JNINativeMethod>>(), sizeOf<JNINativeMethod>())
        }
        JNI_VERSION_1_6
    }
}
