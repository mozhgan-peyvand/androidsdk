package ir.part.sdk.ara.data.dashboard.di

import dagger.Binds
import dagger.Module
import ir.part.sdk.ara.base.di.FeatureDataScope
import ir.part.sdk.ara.data.dashboard.repositories.DashboardRepositoryImpl
import ir.part.sdk.ara.domain.document.repository.DashboardRepository


@Module
abstract class RepositoryDashboardModule {
    @FeatureDataScope
    @Binds
    abstract fun provideDashboardRepository(dashboardRepositoryImpl: DashboardRepositoryImpl): DashboardRepository


}