package com.niolasdev.randommouse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.niolasdev.randommouse.ui.theme.RandomMouseTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RandomMouseTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CatHome(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}