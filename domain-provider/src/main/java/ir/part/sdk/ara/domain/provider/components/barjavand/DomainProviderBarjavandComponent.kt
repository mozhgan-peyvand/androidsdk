package ir.part.sdk.ara.domain.provider.components.barjavand

import dagger.Component
import ir.part.sdk.ara.base.di.BaseComponent
import ir.part.sdk.ara.base.di.BasicComponent
import ir.part.sdk.ara.base.di.ComponentProviderActivity
import ir.part.sdk.ara.base.di.ComponentsKey
import ir.part.sdk.ara.base.di.scopes.DomainScope
import ir.part.sdk.ara.data.barjavand.di.DataBarjavandComponent
import ir.part.sdk.ara.di.DataComponent
import ir.part.sdk.ara.domain.document.interacors.*
import ir.part.sdk.ara.domain.menu.interactors.GetRahyarRemote
import ir.part.sdk.ara.domain.menu.interactors.SubmitCommentRemote
import ir.part.sdk.ara.domain.version.interactors.GetLastShownUpdateVersion
import ir.part.sdk.ara.domain.version.interactors.GetVersionRemote
import ir.part.sdk.ara.domain.version.interactors.SaveLastShownUpdateVersion


@DomainScope
@Component(
    dependencies = [
        BaseComponent::class,
        DataComponent::class,
        DataBarjavandComponent::class,
    ]
)
interface DomainProviderBarjavandComponent : BasicComponent {

    @Component.Factory
    interface Factory {
        fun create(
            baseComponent: BaseComponent,
            dataComponent: DataComponent,
            dataBarjavandComponent: DataBarjavandComponent
        ): DomainProviderBarjavandComponent
    }

    fun injectGetPersonalDocumentRemote(): GetPersonalDocumentRemote
    fun injectGetPersonalInfoConstantsRemote(): GetPersonalInfoConstantsRemote
    fun injectGetRejectRequestByUserRemote(): SetRemoveDocumentRemote
    fun injectSetHasUnreadMessageRemote(): SetHasUnreadMessageRemote
    fun injectGetPersonalInfoClubRemote(): GetPersonalInfoClubRemote
    fun injectGetApplicantInformationRemote(): GetApplicantInformationRemote
    fun injectSubmitCommentRemote(): SubmitCommentRemote
    fun injectRahyarRemote(): GetRahyarRemote
    fun injectGetVersionRemote(): GetVersionRemote
    fun injectGetLastShownUpdateVersion(): GetLastShownUpdateVersion
    fun injectSaveLastShownUpdateVersion(): SaveLastShownUpdateVersion


    companion object {
        fun builder(componentProvider: ComponentProviderActivity): DomainProviderBarjavandComponent {
            return (componentProvider.provideComponent(
                ComponentsKey.DOMAIN_PROVIDER_BARJAVAND,
                DaggerDomainProviderBarjavandComponent.factory().create(
                    baseComponent = BaseComponent.builder(componentProvider),
                    dataComponent = DataComponent.builder(componentProvider),
                    dataBarjavandComponent = DataBarjavandComponent.builder(componentProvider)
                )
            )) as DomainProviderBarjavandComponent
        }
    }
}