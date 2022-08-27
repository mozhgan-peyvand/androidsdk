package ir.part.sdk.ara.data.barjavand.di

import dagger.Component
import ir.part.sdk.ara.base.di.*
import ir.part.sdk.ara.data.dashboard.di.RepositoryDashboardModule
import ir.part.sdk.ara.data.dashboard.di.ServiceDashboardModule
import ir.part.sdk.ara.di.DataComponent
import ir.part.sdk.ara.domain.document.interacors.*
import ir.part.sdk.ara.domain.menu.di.DomainMenuComponent
import ir.part.sdk.ara.domain.menu.interactors.GetRahyarRemote
import ir.part.sdk.ara.domain.menu.interactors.SubmitCommentRemote
import ir.part.sdk.ara.domain.version.interactors.GetVersionRemote


@FeatureDataScope
@Component(
    dependencies = [
        BaseComponent::class,
        DataComponent::class,
        DomainMenuComponent::class
    ],
    modules = [
        RepositoryBarjavandModule::class,
        ServiceBarjavandModule::class,
        RepositoryDashboardModule::class,
        ServiceDashboardModule::class,
        MenuRepositoryBarjavandModule::class
    ]
)
interface BarjavandComponent : BasicComponent {

    @Component.Factory
    interface Factory {
        fun create(
            baseComponent: BaseComponent,
            dataComponent: DataComponent,
            domainMenuComponent: DomainMenuComponent,
        ): BarjavandComponent
    }

    fun injectGetPersonalDocumentRemote(): GetPersonalDocumentRemote
    fun injectGetPersonalInfoConstantsRemote(): GetPersonalInfoConstantsRemote
    fun injectGetRejectRequestByUserRemote(): SetRemoveDocumentRemote
    fun injectSetHasUnreadMessageRemote(): SetHasUnreadMessageRemote
    fun injectSubmitReqValidationRemote(): SubmitReqValidationRemote
    fun injectGetPersonalInfoClubRemote(): GetPersonalInfoClubRemote
    fun injectGetApplicantInformationRemote(): GetApplicantInformationRemote
    fun injectSubmitCommentRemote(): SubmitCommentRemote
    fun injectRahyarRemote(): GetRahyarRemote
    fun injectGetVersionRemote(): GetVersionRemote


    companion object {
        fun builder(componentProvider: ComponentProviderActivity): BarjavandComponent {
            return (componentProvider.provideComponent(
                ComponentsKey.DATA_BARJAVAND,
                DaggerBarjavandComponent.factory().create(
                    baseComponent = BaseComponent.builder(componentProvider),
                    dataComponent = DataComponent.builder(componentProvider),
                    domainMenuComponent = DomainMenuComponent.builder(componentProvider)
                )
            )) as BarjavandComponent
        }
    }
}