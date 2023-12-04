package com.ohyooo.quick

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import com.ohyooo.kni.Kni
import com.ohyooo.rust.librust

class SplashActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Kni.sayHello()

        setContent {
            Column {
                Text(text = Kni.callFromNative())
                Text(text = librust.hello("rust"))
            }
        }
    }

}
