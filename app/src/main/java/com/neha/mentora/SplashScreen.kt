package com.neha.mentora

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*          // Box, Column, Spacer, fillMaxSize, size, height
import androidx.compose.material3.*                // Text, MaterialTheme
import androidx.compose.runtime.*                 // @Composable, remember, LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource     // painterResource for logo drawable
import androidx.compose.ui.text.font.FontWeight   // fontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SplashScreen(navController: NavController) {
    val scale = remember { androidx.compose.animation.core.Animatable(0.6f) }
    LaunchedEffect(true) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = androidx.compose.animation.core.tween(durationMillis = 600)
        )
        kotlinx.coroutines.delay(1200)
        val sharedPrefs = navController.context.getSharedPreferences("sociallearn_prefs", 0)
        val firstLaunch = sharedPrefs.getBoolean("first_launch", true)
        val user = FirebaseAuth.getInstance().currentUser

        when {
            user != null -> navController.navigate("dashboard") { popUpTo("splash") { inclusive = true } }
            firstLaunch -> {
                sharedPrefs.edit().putBoolean("first_launch", false).apply()
                navController.navigate("onboarding") { popUpTo("splash") { inclusive = true } }
            }
            else -> navController.navigate("login") { popUpTo("splash") { inclusive = true } }
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // logo (add ic_mentora_logo.png to drawable)
            Image(
                painter = painterResource(id = R.drawable.ic_mentora_logo),
                contentDescription = "Mentora logo",
                modifier = Modifier
                    .size(120.dp)
                    .graphicsLayer {
                        scaleX = scale.value
                        scaleY = scale.value
                    }
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text("Mentora", fontSize = 34.sp, fontWeight = FontWeight.Bold, color = Color(0xFF7E57C2))
            Text("Learn. Quiz. Collaborate.", style = MaterialTheme.typography.bodyMedium)
        }
    }
}
