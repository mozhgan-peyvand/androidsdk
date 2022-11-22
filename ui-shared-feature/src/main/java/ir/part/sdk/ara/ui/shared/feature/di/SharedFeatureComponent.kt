package ir.part.sdk.ara.ui.shared.feature.di

import dagger.Component
import ir.part.sdk.ara.base.di.BaseComponent
import ir.part.sdk.ara.base.di.BasicComponent
import ir.part.sdk.ara.base.di.ComponentProviderActivity
import ir.part.sdk.ara.base.di.ComponentsKey
import ir.part.sdk.ara.base.di.scopes.featureScopes.UiSharedFeatureScope
import ir.part.sdk.ara.common.ui.view.AraViewModelFactory
import ir.part.sdk.ara.domain.provider.components.userManager.DomainProviderUserManagerComponent
import ir.part.sdk.ara.ui.shared.feature.screens.captcha.CaptchaViewModel

@UiSharedFeatureScope
@Component(
    dependencies = [
        BaseComponent::class,
        DomainProviderUserManagerComponent::class
    ]
)
interface SharedFeatureComponent : BasicComponent {

    @Component.Factory
    interface Factory {
        fun create(
            baseComponent: BaseComponent,
            domainProviderUserManagerComponent: DomainProviderUserManagerComponent
        ): SharedFeatureComponent
    }

    fun getCaptchaViewModel(): AraViewModelFactory<CaptchaViewModel>

    companion object {
        fun builder(componentProvider: ComponentProviderActivity): SharedFeatureComponent {
            return (componentProvider.provideComponent(
                ComponentsKey.UI_SHARED_FEATURE,
                DaggerSharedFeatureComponent.factory().create(
                    baseComponent = BaseComponent.builder(componentProvider),
                    domainProviderUserManagerComponent = DomainProviderUserManagerComponent.builder(
                        componentProvider
                    )
                )
            )) as SharedFeatureComponent
        }
    }

}