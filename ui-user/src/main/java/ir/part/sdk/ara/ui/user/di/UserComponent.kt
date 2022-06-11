package ir.part.sdk.ara.ui.user.di

import ir.part.sdk.ara.ui.user.register.RegisterViewModel
import dagger.Component
import ir.part.sdk.ara.base.di.*
import ir.part.sdk.ara.data.dashboard.di.DataDashboardComponent
import ir.part.sdk.ara.data.state.di.DataStateComponent
import ir.part.sdk.ara.data.userManager.di.DataUserManagerComponent
import ir.part.sdk.ara.domain.tasks.di.DomainTaskComponent
import ir.part.sdk.ara.domain.user.di.DomainUserManagerComponent
import ir.part.sdk.ara.ui.user.captcha.CaptchaViewModel
import ir.part.sdk.ara.ui.user.changePassword.ChangePasswordViewModel
import ir.part.sdk.ara.ui.user.forgetPassword.ForgetPasswordViewModel
import ir.part.sdk.ara.ui.user.forgetPasswordVerification.ForgetPasswordVerificationViewModel
import ir.part.sdk.ara.ui.user.login.LoginViewModel

@FeatureScope
@Component(
    dependencies = [
        BaseComponent::class,
        DomainUserManagerComponent::class,
        DataUserManagerComponent::class,
        DataStateComponent::class,
        DataDashboardComponent::class,
        DomainTaskComponent::class
    ],
)
interface UserComponent : BasicComponent {

    @Component.Factory
    interface Factory {
        fun create(
            baseComponent: BaseComponent,
            domainUserManagerComponent: DomainUserManagerComponent,
            dataUserManagerComponent: DataUserManagerComponent,
            dataStateComponent: DataStateComponent,
            dataDashboardComponent: DataDashboardComponent,
            domainTaskComponent: DomainTaskComponent
        ): UserComponent
    }

    fun getRegisterViewModel(): RegisterViewModel
    fun getLoginViewModel(): LoginViewModel
    fun getForgetPasswordViewModel(): ForgetPasswordViewModel
    fun getForgetPasswordVerificationViewModel(): ForgetPasswordVerificationViewModel
    fun getChangePasswordViewModel(): ChangePasswordViewModel
    fun getCaptchaViewModel(): CaptchaViewModel

    companion object {
        fun builder(componentProvider: ComponentProviderActivity): UserComponent {
            return (componentProvider.provideComponent(
                ComponentsKey.UI_USER,
                DaggerUserComponent.factory().create(
                    baseComponent = BaseComponent.builder(componentProvider),
                    domainUserManagerComponent = DomainUserManagerComponent.builder(
                        componentProvider
                    ),
                    dataUserManagerComponent = DataUserManagerComponent.builder(componentProvider),
                    dataStateComponent = DataStateComponent.builder(componentProvider),
                    dataDashboardComponent = DataDashboardComponent.builder(componentProvider),
                    domainTaskComponent = DomainTaskComponent.builder(componentProvider)
                )
            )) as UserComponent
        }
    }

}