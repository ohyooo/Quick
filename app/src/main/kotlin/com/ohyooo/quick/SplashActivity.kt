package com.ohyooo.quick

import android.os.Bundle
import androidx.activity.ComponentActivity

class SplashActivity : ComponentActivity() {

    override fun onResume() {
        super.onResume()
        println("1234")
        SaveOnResume()
    }

    fun SaveOnResume() {
        super.onResume()

    }
}
