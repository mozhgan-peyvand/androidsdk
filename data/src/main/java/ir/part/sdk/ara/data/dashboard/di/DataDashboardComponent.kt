package ir.part.sdk.ara.data.dashboard.di

import dagger.Component
import ir.part.sdk.ara.base.di.BaseComponent
import ir.part.sdk.ara.base.di.BasicComponent
import ir.part.sdk.ara.base.di.ComponentProviderActivity
import ir.part.sdk.ara.base.di.ComponentsKey
import ir.part.sdk.ara.di.DataComponent
import ir.part.sdk.ara.domain.document.repository.DashboardRepository
import ir.part.sdk.ara.domain.tasks.repository.TaskRepository


//@FeatureDataScope
@Component(
    dependencies = [
        BaseComponent::class,
        DataComponent::class
    ],
    modules = [
        RepositoryTaskModule::class,
        ServiceDashboardModule::class,
        RepositoryDashboardModule::class
    ]
)
interface DataDashboardComponent : BasicComponent {

    @Component.Factory
    interface Factory {
        fun create(
            baseComponent: BaseComponent,
            dataComponent: DataComponent
        ): DataDashboardComponent
    }

    fun injectTaskRepository(): TaskRepository
    fun injectDashboardRepository(): DashboardRepository

    companion object {
        fun builder(componentProvider: ComponentProviderActivity): DataDashboardComponent {
            return (componentProvider.provideComponent(
                ComponentsKey.DATA_DASHBOARD,
                DaggerDataDashboardComponent.factory().create(
                    baseComponent = BaseComponent.builder(componentProvider),
                    dataComponent = DataComponent.builder(componentProvider)
                )
            )) as DataDashboardComponent
        }
    }
}