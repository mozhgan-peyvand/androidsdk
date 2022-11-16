package ir.part.sdk.ara.data.payment.di

import dagger.Component
import ir.part.sdk.ara.base.di.BaseComponent
import ir.part.sdk.ara.base.di.BasicComponent
import ir.part.sdk.ara.base.di.ComponentProviderActivity
import ir.part.sdk.ara.base.di.ComponentsKey
import ir.part.sdk.ara.di.DataComponent
import ir.part.sdk.ara.domain.payment.repository.PaymentRepository


//@FeatureDataScope
@Component(
    dependencies = [
        BaseComponent::class,
        DataComponent::class
    ],
    modules = [
        RepositoryPaymentModule::class,
        ServicePaymentModule::class
    ]
)
interface DataPaymentComponent : BasicComponent {

    @Component.Factory
    interface Factory {
        fun create(
            baseComponent: BaseComponent,
            dataComponent: DataComponent
        ): DataPaymentComponent
    }

    fun injectPaymentRepository(): PaymentRepository

    companion object {
        fun builder(componentProvider: ComponentProviderActivity): DataPaymentComponent {
            return (componentProvider.provideComponent(
                ComponentsKey.DATA_PAYMENT,
                DaggerDataPaymentComponent.factory().create(
                    baseComponent = BaseComponent.builder(componentProvider),
                    dataComponent = DataComponent.builder(componentProvider)
                )
            )) as DataPaymentComponent
        }
    }
}