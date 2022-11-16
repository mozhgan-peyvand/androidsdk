package ir.part.sdk.ara.home.di

import dagger.Component
import ir.part.sdk.ara.base.di.BaseComponent
import ir.part.sdk.ara.base.di.BasicComponent
import ir.part.sdk.ara.base.di.ComponentProviderActivity
import ir.part.sdk.ara.base.di.ComponentsKey
import ir.part.sdk.ara.common.ui.view.AraViewModelFactory
import ir.part.sdk.ara.domain.provider.components.barjavand.DomainProviderBarjavandComponent
import ir.part.sdk.ara.home.version.VersionViewModel

//@FeatureScope
@Component(
    dependencies = [
        BaseComponent::class,
        DomainProviderBarjavandComponent::class
    ]
)
interface HomeComponent : BasicComponent {

    @Component.Factory
    interface Factory {
        fun create(
            baseComponent: BaseComponent,
            domainProviderBarjavandComponent: DomainProviderBarjavandComponent
        ): HomeComponent
    }

    fun getVersion(): AraViewModelFactory<VersionViewModel>

    companion object {
        fun builder(componentProvider: ComponentProviderActivity): HomeComponent {
            return (componentProvider.provideComponent(
                ComponentsKey.UI_HOME,
                DaggerHomeComponent.factory().create(
                    baseComponent = BaseComponent.builder(componentProvider),
                    domainProviderBarjavandComponent = DomainProviderBarjavandComponent.builder(
                        componentProvider
                    )
                )
            )) as HomeComponent
        }
    }
}

