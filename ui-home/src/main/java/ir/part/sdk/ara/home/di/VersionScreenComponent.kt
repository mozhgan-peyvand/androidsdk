package ir.part.sdk.ara.home.di

import dagger.Component
import ir.part.sdk.ara.base.di.*
import ir.part.sdk.ara.data.barjavand.di.BarjavandComponent
import ir.part.sdk.ara.domain.version.di.DomainHomeComponent
import ir.part.sdk.ara.home.version.VersionViewModel

@FeatureScope
@Component(
    dependencies = [
        BaseComponent::class,
        DomainHomeComponent::class,
        BarjavandComponent::class],
)
interface VersionScreenComponent : BasicComponent {

    @Component.Factory
    interface Factory {
        fun create(
            baseComponent: BaseComponent,
            barjavandComponent: BarjavandComponent,
            domainHomeComponent: DomainHomeComponent,
        ): VersionScreenComponent
    }

    fun getVersion(): VersionViewModel

    companion object {
        fun builder(componentProvider: ComponentProviderActivity): VersionScreenComponent {
            return (componentProvider.provideComponent(
                ComponentsKey.UI_HOME,
                DaggerVersionScreenComponent.factory().create(
                    baseComponent = BaseComponent.builder(componentProvider),
                    barjavandComponent = BarjavandComponent.builder(componentProvider),
                    domainHomeComponent = DomainHomeComponent.builder(componentProvider)
                )
            )) as VersionScreenComponent
        }
    }
}

