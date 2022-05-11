package ir.part.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ir.part.sdk.ara.builder.Ara

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val builder = Ara.Builder()
            .setContext(this)
            .setAppId(BuildConfig.APPLICATION_ID)
            .build()
        builder.start()

        finish()
    }
}