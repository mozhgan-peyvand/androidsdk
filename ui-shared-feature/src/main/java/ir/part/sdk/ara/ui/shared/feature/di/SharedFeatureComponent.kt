package ir.part.sdk.ara.ui.shared.feature.di

import dagger.Component
import ir.part.sdk.ara.base.di.*
import ir.part.sdk.ara.data.userManager.di.DataUserManagerComponent
import ir.part.sdk.ara.domain.user.di.DomainUserManagerComponent
import ir.part.sdk.ara.ui.shared.feature.screens.captcha.CaptchaViewModel

@FeatureScope
@Component(
    dependencies = [
        BaseComponent::class,
        DomainUserManagerComponent::class,
        DataUserManagerComponent::class
    ],
)
interface SharedFeatureComponent : BasicComponent {

    @Component.Factory
    interface Factory {
        fun create(
            baseComponent: BaseComponent,
            domainUserManagerComponent: DomainUserManagerComponent,
            dataUserManagerComponent: DataUserManagerComponent
        ): SharedFeatureComponent
    }

    fun getCaptchaViewModel(): CaptchaViewModel

    companion object {
        fun builder(componentProvider: ComponentProviderActivity): SharedFeatureComponent {
            return (componentProvider.provideComponent(
                ComponentsKey.UI_SHARED_FEATURE,
                DaggerSharedFeatureComponent.factory().create(
                    baseComponent = BaseComponent.builder(componentProvider),
                    domainUserManagerComponent = DomainUserManagerComponent.builder(
                        componentProvider
                    ),
                    dataUserManagerComponent = DataUserManagerComponent.builder(componentProvider)
                )
            )) as SharedFeatureComponent
        }
    }

}