package ir.part.sdk.ara.domain.provider.components.dashboard

import dagger.Component
import ir.part.sdk.ara.base.di.BaseComponent
import ir.part.sdk.ara.base.di.BasicComponent
import ir.part.sdk.ara.base.di.ComponentProviderActivity
import ir.part.sdk.ara.base.di.ComponentsKey
import ir.part.sdk.ara.base.di.scopes.DomainScope
import ir.part.sdk.ara.data.dashboard.di.DataDashboardComponent
import ir.part.sdk.ara.di.DataComponent
import ir.part.sdk.ara.domain.document.interacors.SubmitReqValidationRemote
import ir.part.sdk.ara.domain.tasks.interacors.DoingRemote
import ir.part.sdk.ara.domain.tasks.interacors.DoneRemote
import ir.part.sdk.ara.domain.tasks.interacors.GetDoingTasksRemote
import ir.part.sdk.ara.domain.tasks.interacors.GetTaskInstanceId


@DomainScope
@Component(
    dependencies = [
        BaseComponent::class,
        DataComponent::class,
        DataDashboardComponent::class
    ]
)
interface DomainProviderDashboardComponent : BasicComponent {

    @Component.Factory
    interface Factory {
        fun create(
            baseComponent: BaseComponent,
            dataComponent: DataComponent,
            dataDashboardComponent: DataDashboardComponent
        ): DomainProviderDashboardComponent
    }

    fun injectSubmitReqValidationRemote(): SubmitReqValidationRemote
    fun injectGetTaskRemote(): GetDoingTasksRemote
    fun injectDoingTaskRemote(): DoingRemote
    fun injectDoneTaskRemote(): DoneRemote
    fun injectGetTaskInstanceId(): GetTaskInstanceId

    companion object {
        fun builder(componentProvider: ComponentProviderActivity): DomainProviderDashboardComponent {
            return (componentProvider.provideComponent(
                ComponentsKey.DOMAIN_PROVIDER_DASHBOARD,
                DaggerDomainProviderDashboardComponent.factory().create(
                    baseComponent = BaseComponent.builder(componentProvider),
                    dataComponent = DataComponent.builder(componentProvider),
                    dataDashboardComponent = DataDashboardComponent.builder(componentProvider)
                )
            )) as DomainProviderDashboardComponent
        }
    }
}