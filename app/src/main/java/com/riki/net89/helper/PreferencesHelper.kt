package com.riki.net89.helper

import android.content.Context
import android.content.SharedPreferences

class PreferencesHelper(context : Context) {

    private val PREFS_NAME ="userpref123"
    private val sharedpref:SharedPreferences
    val editor: SharedPreferences.Editor

    init {
        sharedpref = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE)
        editor =sharedpref.edit()
    }

    fun simpanDataStr(key : String, value : String) {
        editor.putString(key, value)
            .apply()
    }

    fun getDataStr(key: String) : String? {
        return sharedpref.getString(key, null)
    }

    fun simpanBool(key : String, value : Boolean){
        editor.putBoolean(key, value)
            .apply()
    }

    fun getDataBool(key: String) : Boolean {
        return sharedpref.getBoolean(key, false)
    }

    fun clear(){
        editor.clear()
            .apply()
    }
}