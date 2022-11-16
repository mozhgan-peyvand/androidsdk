package ir.part.sdk.ara.ui.document.di

import dagger.Component
import ir.part.sdk.ara.base.di.BaseComponent
import ir.part.sdk.ara.base.di.BasicComponent
import ir.part.sdk.ara.base.di.ComponentProviderActivity
import ir.part.sdk.ara.base.di.ComponentsKey
import ir.part.sdk.ara.common.ui.view.AraViewModelFactory
import ir.part.sdk.ara.common.ui.view.di.CommonUiComponent
import ir.part.sdk.ara.domain.provider.components.barjavand.DomainProviderBarjavandComponent
import ir.part.sdk.ara.domain.provider.components.payment.DomainProviderPaymentComponent
import ir.part.sdk.ara.ui.document.overviewDocument.DocumentSharedViewModel
import ir.part.sdk.ara.ui.document.submitDocument.SubmitDocumentViewModel


//@FeatureScope
@Component(
    dependencies = [
        BaseComponent::class,
        CommonUiComponent::class,
        DomainProviderPaymentComponent::class,
        DomainProviderBarjavandComponent::class
    ]
)
interface DocumentComponent : BasicComponent {

    @Component.Factory
    interface Factory {
        fun create(
            baseComponent: BaseComponent,
            commonUiComponent: CommonUiComponent,
            domainProviderPaymentComponent: DomainProviderPaymentComponent,
            domainProviderBarjavandComponent: DomainProviderBarjavandComponent
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
                    commonUiComponent = CommonUiComponent.builder(componentProvider),
                    domainProviderPaymentComponent = DomainProviderPaymentComponent.builder(
                        componentProvider
                    ),
                    domainProviderBarjavandComponent = DomainProviderBarjavandComponent.builder(
                        componentProvider
                    )
                )
            )) as DocumentComponent
        }
    }
}
