package com.example.webviewapp

import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

class MyWebViewClient : WebViewClient() {

    @TargetApi(Build.VERSION_CODES.N)
    override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {

        val sharedPreferences: SharedPreferences =
            view.context.getSharedPreferences("lastPage", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putString("url", request.url.toString())
        editor.apply()

        if (request.url.toString().startsWith("https://yandex.ru/maps")) {
            val uri: Uri = Uri.parse(request.url.toString())
            val intent = Intent(Intent.ACTION_VIEW, uri)

            val pm: PackageManager = view.context.packageManager
            val appStartIntent = pm.getLaunchIntentForPackage("ru.yandex.yandexmaps")

            if (null != appStartIntent) {
                view.context.startActivity(intent)
            } else {
                view.loadUrl(request.url.toString())
            }
            
        } else if (request.url.toString().startsWith("https://yandex.ru/pogoda")) {
            val pm: PackageManager = view.context.packageManager
            val appStartIntent = pm.getLaunchIntentForPackage("ru.yandex.weatherplugin")
            if (null != appStartIntent) {
                view.context.startActivity(appStartIntent)
            } else {
                view.loadUrl(request.url.toString())
            }
        } else {
            view.loadUrl(request.url.toString())
        }
        return true
    }

    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {

        val sharedPreferences: SharedPreferences =
            view.context.getSharedPreferences("lastPage", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putString("url", url)
        editor.apply()

        if (url.startsWith("https://yandex.ru/maps")) {
            val uri: Uri = Uri.parse(url)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            try {
                view.context.startActivity(intent)
            } catch (e: Exception) {
                view.loadUrl(url)
            }
        } else if (url.startsWith("https://yandex.ru/pogoda")) {
            val pm: PackageManager = view.context.packageManager
            val appStartIntent = pm.getLaunchIntentForPackage("ru.yandex.weatherplugin")
            if (null != appStartIntent) {
                view.context.startActivity(appStartIntent)
            } else {
                view.loadUrl(url)
            }
        } else {
            view.loadUrl(url)
        }
        return true
    }
}