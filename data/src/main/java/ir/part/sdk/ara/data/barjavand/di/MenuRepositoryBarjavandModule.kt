package ir.part.sdk.ara.data.barjavand.di

import dagger.Binds
import dagger.Module
import ir.part.sdk.ara.base.di.scopes.FeatureDataScope
import ir.part.sdk.ara.data.barjavand.repositories.BarjavandRepositoryImp
import ir.part.sdk.ara.domain.menu.repository.MenuBarjavandRepository

@Module
abstract class MenuRepositoryBarjavandModule {

    @FeatureDataScope
    @Binds
    abstract fun provideMenuBarjavandRepository(barjavandRepositoryImp: BarjavandRepositoryImp): MenuBarjavandRepository

}