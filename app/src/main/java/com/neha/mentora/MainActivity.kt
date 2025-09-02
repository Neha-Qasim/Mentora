// File: MainActivity.kt
package com.neha.mentora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import com.neha.mentora.navigation.AppNavGraph
import com.neha.mentora.ui.theme.MentoraTheme
import com.google.android.gms.ads.MobileAds
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Init Firebase + AdMob
        FirebaseApp.initializeApp(this)
        MobileAds.initialize(this) {}

        setContent {
            MentoraTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    AppNavGraph(navController = navController)
                }
            }
        }
    }
}
