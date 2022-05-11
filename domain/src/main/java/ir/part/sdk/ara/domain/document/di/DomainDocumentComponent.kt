package ir.part.sdk.ara.domain.document.di

import dagger.Component
import ir.part.sdk.ara.base.di.*


@DomainScope
@Component(
    dependencies = [
        BaseComponent::class
    ]
)
interface DomainDocumentComponent : BasicComponent {
    @Component.Factory
    interface Factory {
        fun create(
            baseComponent: BaseComponent
        ): DomainDocumentComponent
    }
    companion object {
        fun builder(componentProvider: ComponentProviderActivity): DomainDocumentComponent {
            return (componentProvider.provideComponent(
                ComponentsKey.DOMAIN_FILE,
                DaggerDomainDocumentComponent.factory().create(
                    baseComponent = BaseComponent.builder(componentProvider)
                )
            )) as DomainDocumentComponent
        }
    }
}