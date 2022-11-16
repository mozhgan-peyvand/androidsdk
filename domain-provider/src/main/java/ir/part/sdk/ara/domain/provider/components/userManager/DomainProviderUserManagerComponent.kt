package ir.part.sdk.ara.domain.provider.components.userManager

import dagger.Component
import ir.part.sdk.ara.base.di.BaseComponent
import ir.part.sdk.ara.base.di.BasicComponent
import ir.part.sdk.ara.base.di.ComponentProviderActivity
import ir.part.sdk.ara.base.di.ComponentsKey
import ir.part.sdk.ara.data.userManager.di.DataUserManagerComponent
import ir.part.sdk.ara.di.DataComponent
import ir.part.sdk.ara.domain.user.interacors.*

//@FeatureDataScope
@Component(
    dependencies = [
        BaseComponent::class,
        DataComponent::class,
        DataUserManagerComponent::class,
    ]
)

interface DomainProviderUserManagerComponent : BasicComponent {

    @Component.Factory
    interface Factory {
        fun create(
            baseComponent: BaseComponent,
            dataComponent: DataComponent,
            dataUserManagerComponent: DataUserManagerComponent
        ): DomainProviderUserManagerComponent
    }

    fun injectGetForgetPasswordRemote(): GetForgetPasswordRemote
    fun injectGetForgetPasswordVerificationRemote(): GetForgetPasswordVerificationRemote
    fun injectGetLoginRemote(): GetLoginRemote
    fun injectGetNationalCode(): GetNationalCode
    fun injectGetPhoneNumber(): GetPhoneNumber
    fun injectGetRegisterRemote(): GetRegisterRemote
    fun injectGetChangePasswordRemote(): GetChangePasswordRemote
    fun injectGetCaptchaRemote(): GetCaptchaRemote
    fun injectLogout(): Logout
    fun injectGetToken(): GetToken

    companion object {
        fun builder(componentProvider: ComponentProviderActivity): DomainProviderUserManagerComponent {
            return (componentProvider.provideComponent(
                ComponentsKey.DOMAIN_PROVIDER_USER_MANAGER,
                DaggerDomainProviderUserManagerComponent.factory().create(
                    baseComponent = BaseComponent.builder(componentProvider),
                    dataComponent = DataComponent.builder(componentProvider),
                    dataUserManagerComponent = DataUserManagerComponent.builder(componentProvider)
                )
            )) as DomainProviderUserManagerComponent
        }
    }
}