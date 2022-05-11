package ir.part.sdk.ara.domain.user.di

import dagger.Component
import ir.part.sdk.ara.base.di.*

@DomainScope
@Component(
    dependencies = [
        BaseComponent::class
    ]
)
interface DomainUserManagerComponent : BasicComponent {

    @Component.Factory
    interface Factory {
        fun create(
            baseComponent: BaseComponent
        ): DomainUserManagerComponent
    }


    companion object {
        fun builder(componentProvider: ComponentProviderActivity): DomainUserManagerComponent {
            return (componentProvider.provideComponent(
                ComponentsKey.DOMAIN_USER_MANAGER,
                DaggerDomainUserManagerComponent.factory().create(
                    baseComponent = BaseComponent.builder(componentProvider)
                )
            )) as DomainUserManagerComponent
        }
    }

}
