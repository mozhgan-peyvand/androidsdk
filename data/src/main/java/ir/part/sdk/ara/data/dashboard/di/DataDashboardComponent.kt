package ir.part.sdk.ara.data.dashboard.di

import dagger.Component
import ir.part.sdk.ara.base.di.*
import ir.part.sdk.ara.di.DataComponent
import ir.part.sdk.ara.domain.tasks.di.DomainTaskComponent
import ir.part.sdk.ara.domain.tasks.interacors.DoingRemote
import ir.part.sdk.ara.domain.tasks.interacors.DoneRemote
import ir.part.sdk.ara.domain.tasks.interacors.GetDoingTasksRemote
import ir.part.sdk.ara.domain.tasks.interacors.GetTaskInstanceId


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

    fun injectGetTaskRemote(): GetDoingTasksRemote
    fun injectDoingTaskRemote(): DoingRemote
    fun injectDoneTaskRemote(): DoneRemote
    fun injectGetTaskInstanceId(): GetTaskInstanceId

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