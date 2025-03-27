//import android.app.Activity
//import android.content.Context
//import android.content.res.Configuration
//import androidx.core.os.LocaleListCompat
//import java.util.Locale
//
//object LocaleHelper {
//
//    fun updateLocaleHelper(context: Context, language: String): Context{
//        val locale = Locale(language)
//        Locale.setDefault(locale)
//
//        val config = Configuration(context.resources.configuration)
//        config.setLocale(locale)
//
//        return context.createConfigurationContext(config)
//    }
//
//}