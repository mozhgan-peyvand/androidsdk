package ir.part.sdk.ara.common.ui.view.di

import dagger.Component
import ir.part.sdk.ara.base.di.*
import ir.part.sdk.ara.di.DataComponent

@CommonUiScope
@Component(
    dependencies = [
        BaseComponent::class,
        DataComponent::class
    ]
)
interface CommonUiComponent : BasicComponent {

    @Component.Factory
    interface Factory {
        fun create(
            baseComponent: BaseComponent,
            dataComponent: DataComponent
        ): CommonUiComponent
    }

    companion object {
        fun builder(componentProvider: ComponentProviderActivity): CommonUiComponent {
            return (componentProvider.provideComponent(
                ComponentsKey.COMMON_UI,
                DaggerCommonUiComponent.factory().create(
                    baseComponent = BaseComponent.builder(componentProvider),
                    dataComponent = DataComponent.builder(componentProvider)
                )
            )) as CommonUiComponent
        }
    }
}