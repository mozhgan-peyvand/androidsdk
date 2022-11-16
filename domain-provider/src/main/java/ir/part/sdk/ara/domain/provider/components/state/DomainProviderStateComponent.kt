package ir.part.sdk.ara.domain.provider.components.state

import dagger.Component
import ir.part.sdk.ara.base.di.BaseComponent
import ir.part.sdk.ara.base.di.BasicComponent
import ir.part.sdk.ara.base.di.ComponentProviderActivity
import ir.part.sdk.ara.base.di.ComponentsKey
import ir.part.sdk.ara.data.state.di.DataStateComponent
import ir.part.sdk.ara.di.DataComponent
import ir.part.sdk.ara.domain.tasks.interacors.GetBaseStateRemote
import ir.part.sdk.ara.domain.tasks.interacors.GetProcessInstanceId


//@FeatureDataScope
@Component(
    dependencies = [
        BaseComponent::class,
        DataComponent::class,
        DataStateComponent::class
    ],

    )
interface DomainProviderStateComponent : BasicComponent {

    @Component.Factory
    interface Factory {
        fun create(
            baseComponent: BaseComponent,
            dataComponent: DataComponent,
            dataStateComponent: DataStateComponent
        ): DomainProviderStateComponent
    }

    fun injectGetBaseStateRemote(): GetBaseStateRemote
    fun injectGetProcessInstanceId(): GetProcessInstanceId

    companion object {
        fun builder(componentProvider: ComponentProviderActivity): DomainProviderStateComponent {
            return (componentProvider.provideComponent(
                ComponentsKey.DOMAIN_PROVIDER_STATE,
                DaggerDomainProviderStateComponent.factory().create(
                    baseComponent = BaseComponent.builder(componentProvider),
                    dataComponent = DataComponent.builder(componentProvider),
                    dataStateComponent = DataStateComponent.builder(componentProvider)
                )
            )) as DomainProviderStateComponent
        }
    }
}