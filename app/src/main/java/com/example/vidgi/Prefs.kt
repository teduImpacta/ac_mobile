package com.example.vidgi

import android.content.SharedPreferences

object Prefs {
    val PREF_ID = "VIDGI"

    private fun prefs(): SharedPreferences {
        val context = VidgiApplication.getInstance().applicationContext
        return context.getSharedPreferences(PREF_ID, 0)
    }

    fun setBoolean(flag: String, valor: Boolean) = prefs().edit().putBoolean(flag, valor).apply()
    fun getBoolean(flag: String) = prefs().getBoolean(flag, false)
    fun setString(flag: String, valor: String) = prefs().edit().putString(flag, valor).apply()
    fun getString(flag: String) = prefs().getString(flag, "")
}