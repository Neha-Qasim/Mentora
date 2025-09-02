package com.neha.mentora.auth.onboarding

import android.util.Log
import androidx.compose.runtime.*
import androidx.navigation.NavController
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

@Composable
fun OnboardingScreen(navController: NavController) {
    var step by remember { mutableStateOf(1) }
    var interstitialAd by remember { mutableStateOf<InterstitialAd?>(null) }
    val context = navController.context

    // Load Interstitial once when entering onboarding
    LaunchedEffect(Unit) {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            context,
            "ca-app-pub-3940256099942544/1033173712", // ✅ test interstitial ID
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    interstitialAd = ad
                    Log.d("Ads", "Interstitial loaded")
                }

                override fun onAdFailedToLoad(adError: LoadAdError) {
                    interstitialAd = null
                    Log.e("Ads", "Interstitial failed: ${adError.message}")
                }
            }
        )
    }

    when (step) {
        1 -> Onboard1 { step = 2 }
        2 -> Onboard2 { step = 3 }
        3 -> Onboard3(interstitialAd, navController)
    }
}
