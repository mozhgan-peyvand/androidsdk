package ir.part.sdk.ara.domain.payment.di

import dagger.Component
import ir.part.sdk.ara.base.di.*


@DomainScope
@Component(
    dependencies = [
        BaseComponent::class
    ]
)
interface DomainPaymentComponent : BasicComponent {

    @Component.Factory
    interface Factory {
        fun create(
            baseComponent: BaseComponent,
        ): DomainPaymentComponent
    }

    companion object {
        fun builder(componentProvider: ComponentProviderActivity): DomainPaymentComponent {
            return (componentProvider.provideComponent(
                ComponentsKey.DOMAIN_PAYMENT,
                DaggerDomainPaymentComponent.factory().create(
                    baseComponent = BaseComponent.builder(componentProvider)
                )
            )) as DomainPaymentComponent
        }
    }
}