package ir.part.sdk.ara.ui.user.di

import dagger.Component
import ir.part.sdk.ara.base.di.BaseComponent
import ir.part.sdk.ara.base.di.BasicComponent
import ir.part.sdk.ara.base.di.ComponentProviderActivity
import ir.part.sdk.ara.base.di.ComponentsKey
import ir.part.sdk.ara.base.di.scopes.featureScopes.UiUserManagerScope
import ir.part.sdk.ara.common.ui.view.AraViewModelFactory
import ir.part.sdk.ara.domain.provider.components.dashboard.DomainProviderDashboardComponent
import ir.part.sdk.ara.domain.provider.components.state.DomainProviderStateComponent
import ir.part.sdk.ara.domain.provider.components.userManager.DomainProviderUserManagerComponent
import ir.part.sdk.ara.ui.user.screens.changePassword.ChangePasswordViewModel
import ir.part.sdk.ara.ui.user.screens.forgetPassword.ForgetPasswordViewModel
import ir.part.sdk.ara.ui.user.screens.forgetPasswordVerification.ForgetPasswordVerificationViewModel
import ir.part.sdk.ara.ui.user.screens.login.LoginViewModel
import ir.part.sdk.ara.ui.user.screens.register.RegisterViewModel

@UiUserManagerScope
@Component(
    dependencies = [
        BaseComponent::class,
        DomainProviderStateComponent::class,
        DomainProviderUserManagerComponent::class,
        DomainProviderDashboardComponent::class
    ],
)
interface UserComponent : BasicComponent {

    @Component.Factory
    interface Factory {
        fun create(
            baseComponent: BaseComponent,
            domainProviderStateComponent: DomainProviderStateComponent,
            domainProviderDashboardComponent: DomainProviderDashboardComponent,
            domainProviderUserManagerComponent: DomainProviderUserManagerComponent
        ): UserComponent
    }

    fun getRegisterViewModel(): AraViewModelFactory<RegisterViewModel>
    fun getLoginViewModel(): AraViewModelFactory<LoginViewModel>
    fun getForgetPasswordViewModel(): AraViewModelFactory<ForgetPasswordViewModel>
    fun getForgetPasswordVerificationViewModel(): AraViewModelFactory<ForgetPasswordVerificationViewModel>
    fun getChangePasswordViewModel(): AraViewModelFactory<ChangePasswordViewModel>

    companion object {
        fun builder(componentProvider: ComponentProviderActivity): UserComponent {
            return (componentProvider.provideComponent(
                ComponentsKey.UI_USER,
                DaggerUserComponent.factory().create(
                    baseComponent = BaseComponent.builder(componentProvider),
                    domainProviderStateComponent = DomainProviderStateComponent.builder(
                        componentProvider
                    ),
                    domainProviderDashboardComponent = DomainProviderDashboardComponent.builder(
                        componentProvider
                    ),
                    domainProviderUserManagerComponent = DomainProviderUserManagerComponent.builder(
                        componentProvider
                    )
                )
            )) as UserComponent
        }
    }

}