package ir.part.sdk.ara.data.state.di

import dagger.Component
import ir.part.sdk.ara.base.di.*
import ir.part.sdk.ara.di.DataComponent
import ir.part.sdk.ara.domain.tasks.di.DomainTaskComponent
import ir.part.sdk.ara.domain.tasks.interacors.GetBaseStateRemote


@FeatureDataScope
@Component(
    dependencies = [
        BaseComponent::class,
        DataComponent::class,
        DomainTaskComponent::class,
    ],
    modules = [
        RepositoryStateModule::class,
        ServiceStateModule::class
    ]
)
interface DataStateComponent : BasicComponent {

    @Component.Factory
    interface Factory {
        fun create(
            baseComponent: BaseComponent,
            dataComponent: DataComponent,
            domainTaskComponent: DomainTaskComponent
        ): DataStateComponent
    }

    fun injectGetBaseStateRemote(): GetBaseStateRemote

    companion object {
        fun builder(componentProvider: ComponentProviderActivity): DataStateComponent {
            return (componentProvider.provideComponent(
                ComponentsKey.DATA_STATE,
                DaggerDataStateComponent.factory().create(
                    baseComponent = BaseComponent.builder(componentProvider),
                    dataComponent = DataComponent.builder(componentProvider),
                    domainTaskComponent = DomainTaskComponent.builder(componentProvider)
                )
            )) as DataStateComponent
        }
    }
}