package bd.ewn.opus.service.util

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import java.util.Locale

class LanguageManager(private val context: Context)  {
    companion object {
        private const val PREFS_NAME = "MyAppPreferences"
        private const val KEY_LANGUAGE = "selectedLanguage"
    }

    val prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setAppLanguage(languageCode: String) {
        prefs.edit().putString(KEY_LANGUAGE, languageCode).apply()
        updateAppLanguage(languageCode)
    }

    private fun getAppLanguage(): String {
        return prefs.getString(KEY_LANGUAGE, "") ?: ""
    }


    fun applyAppLanguage() {
        val languageCode = getAppLanguage()
        updateAppLanguage(languageCode)
    }

    private fun updateAppLanguage(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        config.setLayoutDirection(locale);
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }

}