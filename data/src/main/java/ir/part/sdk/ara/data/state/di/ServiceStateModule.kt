package ir.part.sdk.ara.data.state.di

import dagger.Module
import dagger.Provides
import ir.part.sdk.ara.base.di.scopes.FeatureDataScope
import ir.part.sdk.ara.data.state.repositories.BaseStateService
import retrofit2.Retrofit

@Module
class ServiceStateModule {

    @FeatureDataScope
    @Provides
    fun provideStateService(retrofit: Retrofit): BaseStateService {
        return retrofit.create(BaseStateService::class.java)
    }

}