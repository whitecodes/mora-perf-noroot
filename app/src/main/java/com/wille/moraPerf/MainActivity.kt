package com.wille.moraPerf

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.wille.moraPerf.ui.MoraApp
import com.wille.moraPerf.ui.theme.MoraTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoraTheme {
                MoraApp()
            }
        }
    }
}
