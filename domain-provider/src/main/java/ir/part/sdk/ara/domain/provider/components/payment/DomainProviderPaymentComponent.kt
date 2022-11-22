package ir.part.sdk.ara.domain.provider.components.payment

import dagger.Component
import ir.part.sdk.ara.base.di.BaseComponent
import ir.part.sdk.ara.base.di.BasicComponent
import ir.part.sdk.ara.base.di.ComponentProviderActivity
import ir.part.sdk.ara.base.di.ComponentsKey
import ir.part.sdk.ara.base.di.scopes.DomainScope
import ir.part.sdk.ara.data.payment.di.DataPaymentComponent
import ir.part.sdk.ara.di.DataComponent
import ir.part.sdk.ara.domain.payment.interactors.GetPaymentRemote


@DomainScope
@Component(
    dependencies = [
        BaseComponent::class,
        DataComponent::class,
        DataPaymentComponent::class
    ]
)
interface DomainProviderPaymentComponent : BasicComponent {

    @Component.Factory
    interface Factory {
        fun create(
            baseComponent: BaseComponent,
            dataComponent: DataComponent,
            dataPaymentComponent: DataPaymentComponent
        ): DomainProviderPaymentComponent
    }

    fun injectGetPaymentUrl(): GetPaymentRemote

    companion object {
        fun builder(componentProvider: ComponentProviderActivity): DomainProviderPaymentComponent {
            return (componentProvider.provideComponent(
                ComponentsKey.DOMAIN_PROVIDER_PAYMENT,
                DaggerDomainProviderPaymentComponent.factory().create(
                    baseComponent = BaseComponent.builder(componentProvider),
                    dataComponent = DataComponent.builder(componentProvider),
                    dataPaymentComponent = DataPaymentComponent.builder(componentProvider)
                )
            )) as DomainProviderPaymentComponent
        }
    }
}