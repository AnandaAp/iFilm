package com.anlian.ifilm.controller

import android.app.Activity
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class SharedPreferencesData(activity: Activity) {
    private var sharedPreferences: SharedPreferences
    private val SHARED_PREFERENCE_CODE = "LOGIN SESSION"

    init {
        sharedPreferences = activity
            .getSharedPreferences(
                SHARED_PREFERENCE_CODE,
                MODE_PRIVATE
            )
    }

    fun save(key: String,value: String){
        val editor = sharedPreferences.edit()
        editor.putString(key,value)
        editor.apply()
    }
    fun save(key: String,value: Boolean){
        val editor = sharedPreferences.edit()
        editor.putBoolean(key,value)
        editor.apply()
    }
    fun getValueString(key: String): String{
        return sharedPreferences.getString(key,"").toString()
    }
    fun getValueBoolean(key: String): Boolean{
        return sharedPreferences.getBoolean(key,false)
    }
    fun clearSharedPreference() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
    fun removeValue(key: String) {
        val editor = sharedPreferences.edit()
        editor.remove(key)
        editor.apply()
    }

    companion object {
        const val SHARED_PREFERENCE_ID_KEY = "ID CODE"
        const val SHARED_PREFERENCE_EMAIL_KEY = "EMAIL CODE"
        const val SHARED_PREFERENCE_PASSWORD_KEY = "PASSWORD CODE"
        const val SHARED_PREFERENCE_FULLNAME_KEY = "FULLNAME CODE"
        const val SHARED_PREFERENCE_PICTURE_KEY = "PICTURE CODE"
        const val SHARED_PREFERENCE_SESSION_KEY = "SESSION CODE"
        const val SHARED_PREFERENCE_HARDWARE_KEY = "HARDWARE_CODE"
    }

    class HardwarePreferences(activity: Activity){
        val SHARED_PREFERENCE_CODE = "HARDWARE SESSION"
        private var hardwarePreferences: SharedPreferences
        init{
            hardwarePreferences = activity
                .getSharedPreferences(
                    SHARED_PREFERENCE_CODE,
                    MODE_PRIVATE
                )
        }
        fun save(key: String,value: String){
            val editor = hardwarePreferences.edit()
            editor.putString(key,value)
            editor.apply()
        }
        fun removeValue(key: String) {
            val editor = hardwarePreferences.edit()
            editor.remove(key)
            editor.apply()
        }
        fun getValueString(key: String): String{
            return hardwarePreferences.getString(key,"").toString()
        }
        fun clearSharedPreference() {
            val editor = hardwarePreferences.edit()
            editor.clear()
            editor.apply()
        }

    }
}