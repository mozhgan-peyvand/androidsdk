package ir.part.sdk.ara.di

import android.content.Context
import android.content.SharedPreferences
import android.text.SpannableStringBuilder
import androidx.room.Room
import com.commonsware.cwac.saferoom.SafeHelperFactory
import dagger.Module
import dagger.Provides
import ir.part.sdk.ara.base.di.DK
import ir.part.sdk.ara.base.di.DataScope
import ir.part.sdk.ara.db.AraDb

@Suppress("unused")
@Module
class DataModule {
    @DataScope
    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences =
        context.applicationContext.getSharedPreferences("ara", Context.MODE_PRIVATE)


    @Provides
    fun provideDb(
        context: Context,
        @DK dk: String
    ) = Room.databaseBuilder(context, AraDb::class.java, "ara.db")
        .openHelperFactory(SafeHelperFactory.fromUser(SpannableStringBuilder(dk)))
        .fallbackToDestructiveMigration()
        .build()
}