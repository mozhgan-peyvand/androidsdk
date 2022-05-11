package ir.part.sdk.ara.data.dashboard.di

import dagger.Component
import ir.part.sdk.ara.base.di.*
import ir.part.sdk.ara.di.DataComponent
import ir.part.sdk.ara.domain.tasks.di.DomainTaskComponent
import ir.part.sdk.ara.domain.tasks.interacors.DoingTaskRemote
import ir.part.sdk.ara.domain.tasks.interacors.DoneTaskRemote
import ir.part.sdk.ara.domain.tasks.interacors.GetTaskRemote


@FeatureDataScope
@Component(
    dependencies = [
        BaseComponent::class,
        DataComponent::class,
        DomainTaskComponent::class,
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
            dataComponent: DataComponent,
            domainTaskComponent: DomainTaskComponent
        ): DataDashboardComponent
    }

    //    fun injectGetBaseStateRemote(): GetBaseStateRemote
    fun injectGetTaskRemote(): GetTaskRemote
    fun injectDoingTaskRemote(): DoingTaskRemote
    fun injectDoneTaskRemote(): DoneTaskRemote

    companion object {
        fun builder(componentProvider: ComponentProviderActivity): DataDashboardComponent {
            return (componentProvider.provideComponent(
                ComponentsKey.DATA_DASHBOARD,
                DaggerDataDashboardComponent.factory().create(
                    baseComponent = BaseComponent.builder(componentProvider),
                    dataComponent = DataComponent.builder(componentProvider),
                    domainTaskComponent = DomainTaskComponent.builder(componentProvider)
                )
            )) as DataDashboardComponent
        }
    }
}