package ir.part.sdk.ara.domain.tasks.di

import dagger.Component
import ir.part.sdk.ara.base.di.*


@DomainScope
@Component(
    dependencies = [
        BaseComponent::class
    ]
)
interface DomainTaskComponent : BasicComponent {
    @Component.Factory
    interface Factory {
        fun create(
            baseComponent: BaseComponent
        ): DomainTaskComponent
    }
    companion object {
        fun builder(componentProvider: ComponentProviderActivity): DomainTaskComponent {
            return (componentProvider.provideComponent(
                ComponentsKey.DOMAIN_TASK,
                DaggerDomainTaskComponent.factory().create(
                    baseComponent = BaseComponent.builder(componentProvider)
                )
            )) as DomainTaskComponent
        }
    }
}