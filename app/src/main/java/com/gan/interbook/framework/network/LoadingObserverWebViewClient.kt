package com.gan.interbook.framework.network

import android.graphics.Bitmap
import android.webkit.WebView
import android.webkit.WebViewClient

class LoadingObserverWebViewClient(private val requestObserver: RequestObserver) : WebViewClient() {
    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)

        requestObserver.markRequestStarted()
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)

        requestObserver.markRequestFinished()
    }
}