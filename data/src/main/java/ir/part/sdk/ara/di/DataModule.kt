package ir.part.sdk.ara.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides

@Suppress("unused")
@Module
class DataModule {
//    @DataScope
    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences =
        context.applicationContext.getSharedPreferences("ara", Context.MODE_PRIVATE)

}