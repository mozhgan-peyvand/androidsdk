package ir.part.sample

import android.app.Application
import android.content.Context
import timber.log.Timber

class Sample : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant()
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

}
