package ir.part.sdk.ara.data.userManager.di

import dagger.Module
import dagger.Provides
import ir.part.sdk.ara.base.di.FeatureDataScope
import ir.part.sdk.ara.data.userManager.repositories.UserManagerDao
import ir.part.sdk.ara.db.AraDb

@Module
class DaoModule {

    @FeatureDataScope
    @Provides
    fun provideBankDao(db: AraDb): UserManagerDao = db.userManagerDao()
}