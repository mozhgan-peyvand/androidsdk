package ir.part.sdk.ara.data.userManager.di

import dagger.Component
import ir.part.sdk.ara.base.di.*
import ir.part.sdk.ara.di.DataComponent
import ir.part.sdk.ara.domain.user.di.DomainUserManagerComponent
import ir.part.sdk.ara.domain.user.interacors.*

@FeatureDataScope
@Component(
    dependencies = [
        BaseComponent::class,
        DataComponent::class,
        DomainUserManagerComponent::class,
    ],
    modules = [
        RepositoryUserManagerModule::class,
        ServiceUserManagerModule::class,
        DaoModule::class
    ]
)

interface DataUserManagerComponent : BasicComponent {

    @Component.Factory
    interface Factory {
        fun create(
            baseComponent: BaseComponent,
            dataComponent: DataComponent,
            domainUserManagerComponent: DomainUserManagerComponent
        ): DataUserManagerComponent
    }

    fun injectClearAllTables(): ClearAllTables
    fun injectGetCurrentUser(): GetCurrentUser
    fun removeUser(): RemoveUser
    fun injectGetForgetPasswordRemote(): GetForgetPasswordRemote
    fun injectGetForgetPasswordVerificationRemote(): GetForgetPasswordVerificationRemote
    fun injectGetLoginRemote(): GetLoginRemote
    fun injectGetNationalCode(): GetNationalCode
    fun injectGetPhoneNumber(): GetPhoneNumber
    fun injectGetRegisterRemote(): GetRegisterRemote
    fun injectGetChangePasswordRemote(): GetChangePasswordRemote
    fun injectGetCaptchaRemote(): GetCaptchaRemote
    fun injectLogout(): Logout

    companion object {
        fun builder(componentProvider: ComponentProviderActivity): DataUserManagerComponent {
            return (componentProvider.provideComponent(
                ComponentsKey.DATA_USER_MANAGER,
                DaggerDataUserManagerComponent.factory().create(
                    baseComponent = BaseComponent.builder(componentProvider),
                    dataComponent = DataComponent.builder(componentProvider),
                    domainUserManagerComponent = DomainUserManagerComponent.builder(
                        componentProvider
                    )
                )
            )) as DataUserManagerComponent
        }
    }
}