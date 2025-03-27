import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.LocaleList
import androidx.core.os.LocaleListCompat
import java.util.Locale

class LanguageManager(private val context: Context) {
    private val sharedPreferences = context.getSharedPreferences("Settings", Context.MODE_PRIVATE)

//    fun setLocale(languageCode: String) {
//        val locale = Locale(languageCode)
//        Locale.setDefault(locale)
//
//        val config = Configuration(context.resources.configuration)
//        config.setLocale(locale)
//
//        context.resources.updateConfiguration(config, context.resources.displayMetrics)
//
//        // Lưu vào SharedPreferences để app nhớ ngôn ngữ
//        sharedPreferences.edit().putString("LANGUAGE", languageCode).apply()
//    }

    fun setLocale(activity: Activity, languageCode: String) {
        val locale = Locale(languageCode)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Android 13+ (API 33): Sử dụng LocaleManager
            val localeList = LocaleList(locale) // Chuyển Locale thành LocaleList
            val localeManager = activity.getSystemService(android.app.LocaleManager::class.java)
            localeManager?.applicationLocales = localeList
        } else {
            // Android 12 trở xuống: Sử dụng Configuration
            val config = Configuration(activity.resources.configuration)
            config.setLocale(locale)
            activity.resources.updateConfiguration(config, activity.resources.displayMetrics)
        }

        // Yêu cầu reload UI
        activity.recreate()
    }
}
