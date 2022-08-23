package ir.part.sdk.ara.domain.menu.di

import dagger.Component
import ir.part.sdk.ara.base.di.*


@DomainScope
@Component(
    dependencies = [
        BaseComponent::class
    ]
)
interface DomainMenuComponent : BasicComponent {

    @Component.Factory
    interface Factory {
        fun create(
            baseComponent: BaseComponent,
        ): DomainMenuComponent
    }

    companion object {
        fun builder(componentProvider: ComponentProviderActivity): DomainMenuComponent {
            return (componentProvider.provideComponent(
                ComponentsKey.DOMAIN_MENU,
                DaggerDomainMenuComponent.factory().create(
                    baseComponent = BaseComponent.builder(componentProvider)
                )
            )) as DomainMenuComponent
        }
    }
}