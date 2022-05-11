package ir.part.sdk.ara.builder

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.annotation.Keep
import ir.part.sdk.ara.builder.ui.view.HomeActivity


@Keep
class Ara(
    private val context: Context? = null,
    private val appId: String,
) {

    private val activityResultRequestCode = 10

    private constructor(builder: Builder) : this(
        context = builder.context,
        appId = builder.appId,
    )

    @Keep
    class Builder {

        var context: Context? = null
            private set

        var appId: String = ""
            private set

        fun setContext(context: Context) = apply { this.context = context }

        fun setAppId(appId: String) = apply { this.appId = appId }

        fun build() = Ara(this)
    }

    fun start() {
        HomeActivity.appId = appId

        Intent(context, HomeActivity::class.java).apply {
            (context as Activity).startActivityForResult(this, activityResultRequestCode)
        }
    }

}