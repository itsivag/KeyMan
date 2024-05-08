package com.sivag.password_manager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.sivag.password_manager.home.presentation.ui.HomeScreen
import com.sivag.password_manager.ui.theme.PasswordManagerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PasswordManagerTheme {
                HomeScreen()
            }
        }
    }
}
