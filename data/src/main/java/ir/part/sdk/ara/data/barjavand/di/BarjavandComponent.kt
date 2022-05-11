package ir.part.sdk.ara.data.barjavand.di

import dagger.Component
import ir.part.sdk.ara.base.di.*
import ir.part.sdk.ara.data.dashboard.di.RepositoryDashboardModule
import ir.part.sdk.ara.data.dashboard.di.ServiceDashboardModule
import ir.part.sdk.ara.di.DataComponent
import ir.part.sdk.ara.domain.document.di.DomainDocumentComponent
import ir.part.sdk.ara.domain.document.interacors.*


@FeatureDataScope
@Component(
    dependencies = [
        BaseComponent::class,
        DataComponent::class,
        DomainDocumentComponent::class,
    ],
    modules = [
        RepositoryBarjavandModule::class,
        ServiceBarjavandModule::class,
        RepositoryDashboardModule::class,
        ServiceDashboardModule::class
    ]
)
interface BarjavandComponent : BasicComponent {

    @Component.Factory
    interface Factory {
        fun create(
            baseComponent: BaseComponent,
            dataComponent: DataComponent,
            domainDocumentComponent: DomainDocumentComponent
        ): BarjavandComponent
    }

    fun injectGetPersonalDocumentRemote(): GetPersonalDocumentRemote
    fun injectGetPersonalInfoConstantsRemote(): GetPersonalInfoConstantsRemote
    fun injectGetRejectRequestByUserRemote(): GetRejectRequestByUserRemote
    fun injectSetDisableCustomerFlagRemote(): SetDisableCustomerFlagRemote
    fun injectSubmitReqValidationRemote(): SubmitReqValidationRemote
    fun injectGetPersonalInfoClubRemote(): GetPersonalInfoClubRemote


    companion object {
        fun builder(componentProvider: ComponentProviderActivity): BarjavandComponent {
            return (componentProvider.provideComponent(
                ComponentsKey.DATA_BARJAVAND,
                DaggerBarjavandComponent.factory().create(
                    baseComponent = BaseComponent.builder(componentProvider),
                    dataComponent = DataComponent.builder(componentProvider),
                    domainDocumentComponent = DomainDocumentComponent.builder(componentProvider)
                )
            )) as BarjavandComponent
        }
    }
}