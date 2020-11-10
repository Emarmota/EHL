package mx.tec.EHL.Helper

import android.content.SharedPreferences
import android.content.Context

class PreferencesHelper (context: Context){
    private val PREFS_NAME = "sharedpreferences"
    private val sharedpref: SharedPreferences
    val editor: SharedPreferences.Editor

    init {
        sharedpref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        editor = sharedpref.edit()
    }

    fun put(key: String, value: String){
        editor.putString( key, value)
            .apply()
    }

    fun getString(key: String): String?{
        return  sharedpref.getString(key, null)
    }


    fun put(key: String, value: Boolean){
        editor.putBoolean( key, value)
            .apply()
    }

    fun getBoolean(key: String): Boolean{
        return  sharedpref.getBoolean(key, false)
    }

    fun clear(){
        editor.clear()
            .apply()
    }
}