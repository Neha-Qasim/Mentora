package com.neha.mentora.ads

import android.widget.FrameLayout
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

@Composable
fun BannerAdView(
    modifier: Modifier = Modifier,
    adUnitId: String = "ca-app-pub-3940256099942544/6300978111" // test id by default
) {
    val context = LocalContext.current
    AndroidView(
        modifier = modifier,
        factory = {
            AdView(context).apply {
                setAdSize(AdSize.BANNER)
                this.adUnitId = adUnitId
                val request = AdRequest.Builder().build()
                loadAd(request)
            }
        }
    )
}