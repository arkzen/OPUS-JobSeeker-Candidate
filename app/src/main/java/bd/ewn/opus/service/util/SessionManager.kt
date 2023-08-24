package bd.ewn.opus.service.util

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import java.util.Locale

object SessionManager {
    private const val PREFS_NAME = "OPUS_EMPLOYER"
    private const val KEY_LANGUAGE = "language"
    private const val KEY_ACCESS_TOKEN = "access_token"

    //Initialize SharedPreferences
    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    // Save the access token to SharedPreferences------------------------
    fun saveAccessToken(context: Context, accessToken: String) {
        saveAccessTokenToSharedPreferences(context, accessToken)
    }

    private fun saveAccessTokenToSharedPreferences(context: Context, accessToken: String) {
        val editor = getSharedPreferences(context).edit()
        editor.putString(KEY_ACCESS_TOKEN, accessToken)
        editor.apply()
    }

    // Get the access token from SharedPreferences
    fun getAccessToken(context: Context): String? {
        return getSharedPreferences(context).getString(KEY_ACCESS_TOKEN, null)
    }

    // Check if the user is logged in based on the presence of the access token
    fun isLoggedIn(context: Context): Boolean {
        //  return getAccessToken(context) != null
        return getSharedPreferences(context).getString(KEY_ACCESS_TOKEN, null) != null
    }
//    fun whichLanguage()

    //set app language
    fun setAppLanguage(context: Context, languageCode: String) {
        val editor = getSharedPreferences(context).edit()
        editor.putString(KEY_LANGUAGE, languageCode)
        editor.apply()
        updateAppLanguage(context, languageCode)
    }

    fun getAppLanguage(context: Context): String? {
        return getSharedPreferences(context).getString(KEY_LANGUAGE, Locale.getDefault().language)
    }

    /* fun applyAppLanguage(context: Context) {
         val languageCode = getAppLanguage(context)
         updateAppLanguage(context, languageCode)
     }*/

    private fun updateAppLanguage(context: Context, languageCode: String) {

        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)

    }

}