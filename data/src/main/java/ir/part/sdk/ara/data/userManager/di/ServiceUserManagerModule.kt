package ir.part.sdk.ara.data.userManager.di

import dagger.Module
import dagger.Provides
//import ir.part.sdk.ara.base.di.FeatureDataScope
import ir.part.sdk.ara.data.userManager.repositories.UserManagerService
import retrofit2.Retrofit

@Module
class ServiceUserManagerModule {

    //    @FeatureDataScope
    @Provides
    fun provideUserManagerService(retrofit: Retrofit): UserManagerService {
        return retrofit.create(UserManagerService::class.java)
    }

}