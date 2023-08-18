package bd.ewn.opus.service.util

import android.content.Context
import android.content.SharedPreferences

object SharedPrefDataProvider {
    private const val PREF_NAME = "OpusPrefs"
    private const val KEY_LOGIN_STATE = "loginState"
    private const val KEY_ACCESS_TOKEN = "accessToken"

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    fun initialize(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }

    fun setLoginState(loginState: Boolean) {
        editor.putBoolean(KEY_LOGIN_STATE, loginState)
        editor.apply()
    }

    fun getLoginState(): Boolean {
        return sharedPreferences.getBoolean(KEY_LOGIN_STATE, false)
    }

    fun setAccessToken(accessToken: String) {
        editor.putString(KEY_ACCESS_TOKEN, accessToken)
        editor.apply()
    }

    fun getAccessToken(): String? {
        return sharedPreferences.getString(KEY_ACCESS_TOKEN, null)
    }
}