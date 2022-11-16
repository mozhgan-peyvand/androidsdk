package ir.part.sdk.ara.data.dashboard.di

import dagger.Module
import dagger.Provides
//import ir.part.sdk.ara.base.di.FeatureDataScope
import ir.part.sdk.ara.data.dashboard.repositories.DashboardService
import retrofit2.Retrofit

@Module
class ServiceDashboardModule {

    //    @FeatureDataScope
    @Provides
    fun provideDashboardService(retrofit: Retrofit): DashboardService {
        return retrofit.create(DashboardService::class.java)
    }

}