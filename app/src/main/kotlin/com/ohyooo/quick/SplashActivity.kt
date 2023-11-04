package com.ohyooo.quick

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text

class SplashActivity : ComponentActivity() {

    override fun onResume() {
        super.onResume()
        setContent {
            Text(text = "Hello")
        }
    }
}
