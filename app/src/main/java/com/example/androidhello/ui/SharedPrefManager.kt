package com.example.androidhello.ui

import android.content.Context

class SharedPrefManager(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    fun setSharedPreference(key: String, value: String) {
        editor.putString(key, value).commit()
    }

    fun getSharedPreference(key: String, value: String): String {
        return sharedPreferences.getString(key, value).toString()
    }

}