package ir.part.sdk.ara.data.state.di

import dagger.Component
import ir.part.sdk.ara.base.di.BaseComponent
import ir.part.sdk.ara.base.di.BasicComponent
import ir.part.sdk.ara.base.di.ComponentProviderActivity
import ir.part.sdk.ara.base.di.ComponentsKey
import ir.part.sdk.ara.di.DataComponent
import ir.part.sdk.ara.domain.tasks.repository.BaseStateRepository


//@FeatureDataScope
@Component(
    dependencies = [
        BaseComponent::class,
        DataComponent::class
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
            dataComponent: DataComponent
        ): DataStateComponent
    }

    fun injectBaseStateRepository(): BaseStateRepository

    companion object {
        fun builder(componentProvider: ComponentProviderActivity): DataStateComponent {
            return (componentProvider.provideComponent(
                ComponentsKey.DATA_STATE,
                DaggerDataStateComponent.factory().create(
                    baseComponent = BaseComponent.builder(componentProvider),
                    dataComponent = DataComponent.builder(componentProvider)
                )
            )) as DataStateComponent
        }
    }
}