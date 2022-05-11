package ir.part.sdk.ara.builder.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ir.part.sdk.ara.base.di.MainScope
import ir.part.sdk.ara.base.util.ViewModelKey
import ir.part.sdk.ara.builder.ui.view.HomeActivityViewModel

@Module
abstract class ViewModelModule {

    @MainScope
    @BuilderModule
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelProviderFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(HomeActivityViewModel::class)
    abstract fun bindHomeActivityViewModel(homeActivityViewModel: HomeActivityViewModel): ViewModel

}