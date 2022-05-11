package ir.part.sdk.ara.data.dashboard.di

import dagger.Binds
import dagger.Module
import ir.part.sdk.ara.base.di.FeatureDataScope
import ir.part.sdk.ara.data.dashboard.repositories.DashboardRepositoryImpl
import ir.part.sdk.ara.domain.tasks.repository.TaskRepository


@Module
abstract class RepositoryTaskModule {
    @FeatureDataScope
    @Binds
    abstract fun provideTaskRepository(dashboardRepositoryImpl: DashboardRepositoryImpl): TaskRepository
}