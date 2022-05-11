package ir.part.sdk.ara.builder.util

import android.content.Context
import java.util.*


fun localizedContext(baseContext: Context, locale: Locale = Locale("fa")): Context {
    val configuration = baseContext.resources.configuration
    configuration.setLocale(locale)
    return baseContext.createConfigurationContext(configuration)
}
