package ir.part.sdk.ara.data.barjavand.di

import dagger.Module
import dagger.Provides
import ir.part.sdk.ara.base.di.scopes.FeatureDataScope
import ir.part.sdk.ara.data.barjavand.repositories.BarjavandService
import retrofit2.Retrofit

@Module
class ServiceBarjavandModule {

    @FeatureDataScope
    @Provides
    fun provideBarjavandService(retrofit: Retrofit): BarjavandService {
        return retrofit.create(BarjavandService::class.java)
    }

}