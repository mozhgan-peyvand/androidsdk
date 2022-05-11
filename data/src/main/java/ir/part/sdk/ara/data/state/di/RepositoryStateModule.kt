package ir.part.sdk.ara.data.state.di

import dagger.Binds
import dagger.Module
import ir.part.sdk.ara.base.di.FeatureDataScope
import ir.part.sdk.ara.data.state.repositories.BaseStateRepositoryImpl
import ir.part.sdk.ara.domain.tasks.repository.BaseStateRepository


@Module
abstract class RepositoryStateModule {
    @FeatureDataScope
    @Binds
    abstract fun provideBaseStateRepository(baseStateRepositoryImpl: BaseStateRepositoryImpl): BaseStateRepository
}