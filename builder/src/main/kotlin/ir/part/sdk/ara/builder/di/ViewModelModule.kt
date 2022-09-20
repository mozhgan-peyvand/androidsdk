package ir.part.sdk.ara.builder.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ir.part.sdk.ara.base.di.MainScope
import ir.part.sdk.ara.builder.ui.view.HomeViewModel
import ir.part.sdk.ara.common.ui.view.utils.validation.ViewModelKey
import ir.part.sdk.ara.ui.shared.feature.screens.task.TasksManagerViewModel

@Module
abstract class ViewModelModule {

    @MainScope
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelProviderFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(TasksManagerViewModel::class)
    abstract fun bindTaskManagerViewModel(homeActivityViewModel: TasksManagerViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(homeActivityViewModel: HomeViewModel): ViewModel

}