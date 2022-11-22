package ir.part.sdk.ara.base.di

import dagger.Module
import dagger.Provides
import ir.part.sdk.ara.base.di.scopes.BaseScope
import ir.part.sdk.ara.base.util.CoroutinesDispatcherProvider
import kotlinx.coroutines.Dispatchers

@Module
object BaseModule {

    @BaseScope
    @Provides
    fun provideCoroutinesDispatcherProvider(): CoroutinesDispatcherProvider =
        CoroutinesDispatcherProvider(
            Dispatchers.Main,
            Dispatchers.Default,
            Dispatchers.IO
        )

}