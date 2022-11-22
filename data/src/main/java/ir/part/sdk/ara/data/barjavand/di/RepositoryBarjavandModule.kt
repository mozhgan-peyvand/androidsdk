package ir.part.sdk.ara.data.barjavand.di

import dagger.Binds
import dagger.Module
import ir.part.sdk.ara.base.di.scopes.FeatureDataScope
import ir.part.sdk.ara.data.barjavand.repositories.BarjavandRepositoryImp
import ir.part.sdk.ara.domain.document.repository.BarjavandRepository


@Module
abstract class RepositoryBarjavandModule {
    @FeatureDataScope
    @Binds
    abstract fun provideBarjavandRepository(barjavandRepositoryImp: BarjavandRepositoryImp): BarjavandRepository
}
