package bd.ewn.opus.service.util

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import java.util.Locale

object SharedPrefDataProvider {
    private const val PREF_NAME = "OpusPrefs"
    private const val KEY_LOGIN_STATE = "loginState"
    private const val KEY_ACCESS_TOKEN = "accessToken"
    private const val KEY_RESETPASS_EMAIL = "resetPassEmail"
    private const val KEY_RESETPASS_OTP = "resetPassOtp"
    private const val KEY_LANGUAGE = "language"

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    fun initialize(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }

    fun setResetPassEmail(resetPassEmail: String) {
        editor.putString(KEY_RESETPASS_EMAIL, resetPassEmail)
        editor.apply()
    }

    fun getResetPassEmail(): String? {
        return sharedPreferences.getString(KEY_RESETPASS_EMAIL, null)
    }

    fun setResetPassOtp(resetPassOtp: Int) {
        editor.putInt(KEY_RESETPASS_OTP, resetPassOtp)
        editor.apply()
    }

    fun getResetPassOtp(): Int {
        return sharedPreferences.getInt(KEY_RESETPASS_OTP, 0)
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

    fun setAppLanguage(context: Context, languageCode: String) {

        editor.putString(KEY_LANGUAGE, languageCode)
        editor.apply()
        updateAppLanguage(context, languageCode)
    }

    fun getAppLanguage(context: Context): String? {
                return sharedPreferences.getString(KEY_LANGUAGE,Locale.getDefault().language)
    }


    private fun updateAppLanguage(context: Context, languageCode: String) {

        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)

    }

}