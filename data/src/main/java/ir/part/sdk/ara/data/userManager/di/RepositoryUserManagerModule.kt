package ir.part.sdk.ara.data.userManager.di

import dagger.Binds
import dagger.Module
import ir.part.sdk.ara.base.di.FeatureDataScope
import ir.part.sdk.ara.data.userManager.repositories.UserManagerRepositoryImp
import ir.part.sdk.ara.domain.user.repository.UserManagerRepository

@Module
abstract class RepositoryUserManagerModule {
    @FeatureDataScope
    @Binds
    abstract fun provideUserManagerRepository(userManagerRepositoryImp: UserManagerRepositoryImp): UserManagerRepository

}