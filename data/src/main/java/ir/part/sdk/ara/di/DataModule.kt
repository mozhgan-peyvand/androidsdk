package ir.part.sdk.ara.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import ir.part.sdk.ara.base.di.DataScope

@Suppress("unused")
@Module
class DataModule {
    @DataScope
    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences =
        context.applicationContext.getSharedPreferences("ara", Context.MODE_PRIVATE)

}