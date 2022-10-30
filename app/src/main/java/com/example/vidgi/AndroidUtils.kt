package com.example.vidgi

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

object AndroidUtils {
    fun isInternetDisponivel(): Boolean {
        val context = VidgiApplication.getInstance().applicationContext
        val conexao = context.getSystemService(Context.CONNECTIVITY_SERVICE)  as ConnectivityManager

        val capabilities =
            conexao.getNetworkCapabilities(conexao.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    return true
                }
            }
        }
        return false
    }
}