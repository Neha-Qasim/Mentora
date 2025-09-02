package com.neha.mentora.auth.onboarding

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.neha.mentora.ads.BannerAdView
import com.neha.mentora.R
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource    // painterResource for icons/images
// ----------------------- Onboarding Card -----------------------
@Composable
fun OnboardingCard(
    title: String,
    description: String,
    imageRes: Int? = null,
    onNext: () -> Unit,
    buttonText: String = "Next"
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                imageRes?.let {
                    Icon(
                        painter = painterResource(id = it),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(100.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
                Text(title, style = MaterialTheme.typography.headlineMedium, color = Color(0xFF7E57C2))
                Spacer(modifier = Modifier.height(12.dp))
                Text(description, style = MaterialTheme.typography.bodyLarge)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
        BannerAdView(modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = onNext,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7E57C2))
        ) {
            Text(buttonText, color = Color.White)
        }
    }
}

// ----------------------- Individual Screens -----------------------
@Composable
fun Onboard1(onNext: () -> Unit) {
    OnboardingCard(
        title = "Welcome to Mentora",
        description = "Mentora helps you learn with friends — take quizzes, manage study tasks, and chat in real time. Let’s make learning fun and social!",
        imageRes = R.drawable.ic_onboard_study, // add an icon in drawable
        onNext = onNext
    )
}

@Composable
fun Onboard2(onNext: () -> Unit) {
    OnboardingCard(
        title = "Features at a glance",
        description = "• Timed quizzes with instant scoring\n• Personal task manager\n• Group chat for study groups\n• Score history saved to your profile",
        imageRes = R.drawable.ic_onboard_features, // add an icon in drawable
        onNext = onNext
    )
}

@Composable
fun Onboard3(
    interstitialAd: InterstitialAd?,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text("Privacy & Terms", style = MaterialTheme.typography.headlineMedium, color = Color(0xFF7E57C2))
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    "Your data is stored securely in Firebase. We keep only your profile info for app features. By continuing, you agree to our Terms of Service and Privacy Policy.",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text("You can delete your account anytime from Profile.", style = MaterialTheme.typography.bodySmall)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
        BannerAdView(modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                val activity = navController.context as Activity
                if (interstitialAd != null) {
                    interstitialAd.show(activity)
                    interstitialAd.fullScreenContentCallback = object : com.google.android.gms.ads.FullScreenContentCallback() {
                        override fun onAdDismissedFullScreenContent() {
                            navController.navigate("login") {
                                popUpTo("onboarding") { inclusive = true }
                            }
                        }
                        override fun onAdFailedToShowFullScreenContent(adError: com.google.android.gms.ads.AdError) {
                            navController.navigate("login") {
                                popUpTo("onboarding") { inclusive = true }
                            }
                        }
                    }
                } else {
                    // fallback if ad not ready
                    navController.navigate("login") {
                        popUpTo("onboarding") { inclusive = true }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7E57C2))
        ) {
            Text("Finish", color = Color.White)
        }
    }
}
