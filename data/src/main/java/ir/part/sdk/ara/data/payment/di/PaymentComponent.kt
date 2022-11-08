package ir.part.sdk.ara.data.payment.di

import dagger.Component
import ir.part.sdk.ara.base.di.*
import ir.part.sdk.ara.di.DataComponent
import ir.part.sdk.ara.domain.payment.di.DomainPaymentComponent
import ir.part.sdk.ara.domain.payment.interactors.GetPaymentRemote


@FeatureDataScope
@Component(
    dependencies = [
        BaseComponent::class,
        DataComponent::class,
        DomainPaymentComponent::class
    ],
    modules = [
        RepositoryPaymentModule::class,
        ServicePaymentModule::class
    ]
)
interface PaymentComponent : BasicComponent {

    @Component.Factory
    interface Factory {
        fun create(
            baseComponent: BaseComponent,
            dataComponent: DataComponent,
            domainPaymentComponent: DomainPaymentComponent,
        ): PaymentComponent
    }

    fun injectGetPaymentUrl(): GetPaymentRemote

    companion object {
        fun builder(componentProvider: ComponentProviderActivity): PaymentComponent {
            return (componentProvider.provideComponent(
                ComponentsKey.DATA_PAYMENT,
                DaggerPaymentComponent.factory().create(
                    baseComponent = BaseComponent.builder(componentProvider),
                    dataComponent = DataComponent.builder(componentProvider),
                    domainPaymentComponent = DomainPaymentComponent.builder(componentProvider)
                )
            )) as PaymentComponent
        }
    }
}