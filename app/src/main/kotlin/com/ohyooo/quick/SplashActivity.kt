package com.ohyooo.quick

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import com.ohyooo.kni.Kni

class SplashActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Kni.sayHello()

        setContent {
            Text(text = Kni.callFromNative())
        }
    }

}
