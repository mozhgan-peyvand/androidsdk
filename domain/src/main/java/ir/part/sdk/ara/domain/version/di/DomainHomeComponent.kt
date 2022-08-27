package ir.part.sdk.ara.domain.version.di

import dagger.Component
import ir.part.sdk.ara.base.di.*

@DomainScope
@Component(
    dependencies = [
        BaseComponent::class
    ]
)
interface DomainHomeComponent : BasicComponent {
    @Component.Factory
    interface Factory {
        fun create(
            baseComponent: BaseComponent
        ): DomainHomeComponent
    }

    companion object {
        fun builder(componentProvider: ComponentProviderActivity): DomainHomeComponent {
            return (componentProvider.provideComponent(
                ComponentsKey.DOMAIN_HOME,
                DaggerDomainHomeComponent.factory().create(
                    baseComponent = BaseComponent.builder(componentProvider)
                )
            )) as DomainHomeComponent
        }
    }
}