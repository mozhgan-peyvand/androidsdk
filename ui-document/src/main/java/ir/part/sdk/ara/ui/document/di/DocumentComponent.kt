package ir.part.sdk.ara.ui.document.di

import dagger.Component
import ir.part.sdk.ara.base.di.*
import ir.part.sdk.ara.common.ui.view.AraViewModelFactory
import ir.part.sdk.ara.common.ui.view.di.CommonUiComponent
import ir.part.sdk.ara.data.barjavand.di.BarjavandComponent
import ir.part.sdk.ara.data.payment.di.PaymentComponent
import ir.part.sdk.ara.ui.document.overviewDocument.DocumentSharedViewModel
import ir.part.sdk.ara.ui.document.submitDocument.SubmitDocumentViewModel


@FeatureScope
@Component(
    dependencies = [
        BaseComponent::class,
        BarjavandComponent::class,
        CommonUiComponent::class,
        PaymentComponent::class
    ]
)
interface DocumentComponent : BasicComponent {

    @Component.Factory
    interface Factory {
        fun create(
            baseComponent: BaseComponent,
            barjavandComponent: BarjavandComponent,
            commonUiComponent: CommonUiComponent,
            paymentComponent: PaymentComponent
        ): DocumentComponent
    }

    fun providerDocumentSharedViewModel(): AraViewModelFactory<DocumentSharedViewModel>
    fun providerSubmitDocumentViewModel(): AraViewModelFactory<SubmitDocumentViewModel>

    companion object {
        fun builder(componentProvider: ComponentProviderActivity): DocumentComponent {
            return (componentProvider.provideComponent(
                ComponentsKey.UI_DOCUMENT,
                DaggerDocumentComponent.factory().create(
                    baseComponent = BaseComponent.builder(componentProvider),
                    barjavandComponent = BarjavandComponent.builder(componentProvider),
                    commonUiComponent = CommonUiComponent.builder(componentProvider),
                    paymentComponent = PaymentComponent.builder(componentProvider)
                )
            )) as DocumentComponent
        }
    }
}
