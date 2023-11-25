package com.ohyooo.quick

import androidx.activity.ComponentActivity
import com.ohyooo.kni.Kni

class SplashActivity : ComponentActivity() {

    override fun onResume() {
        super.onResume()
        Kni.sayHello()

        println("eeeeeeeeeeeeee")
        println(Kni.callFromNative())

        println(Kni.callJava())
        println("eeeeeeeeeeeeee")
    }

    fun SaveOnResume() {
        super.onResume()

    }
}

