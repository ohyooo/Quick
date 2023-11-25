package com.ohyooo.kni

object Kni {
    init {
        System.loadLibrary("kni")
    }

    external fun stringFromJNI(): String
    external fun callJava(): String
    external fun sayHello()
    external fun sayHello2()
    fun callFromNative(): String {
        return stringFromJNI()
    }
}
