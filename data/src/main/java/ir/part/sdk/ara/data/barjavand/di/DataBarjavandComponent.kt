package ir.part.sdk.ara.data.barjavand.di

import dagger.Component
import ir.part.sdk.ara.base.di.BaseComponent
import ir.part.sdk.ara.base.di.BasicComponent
import ir.part.sdk.ara.base.di.ComponentProviderActivity
import ir.part.sdk.ara.base.di.ComponentsKey
import ir.part.sdk.ara.base.di.scopes.FeatureDataScope
import ir.part.sdk.ara.data.dashboard.di.RepositoryDashboardModule
import ir.part.sdk.ara.data.dashboard.di.ServiceDashboardModule
import ir.part.sdk.ara.di.DataComponent
import ir.part.sdk.ara.domain.document.repository.BarjavandRepository
import ir.part.sdk.ara.domain.menu.repository.MenuBarjavandRepository


@FeatureDataScope
@Component(
    dependencies = [
        BaseComponent::class,
        DataComponent::class
    ],
    modules = [
        RepositoryBarjavandModule::class,
        ServiceBarjavandModule::class,
        RepositoryDashboardModule::class,
        ServiceDashboardModule::class,
        MenuRepositoryBarjavandModule::class
    ]
)
interface DataBarjavandComponent : BasicComponent {

    @Component.Factory
    interface Factory {
        fun create(
            baseComponent: BaseComponent,
            dataComponent: DataComponent
        ): DataBarjavandComponent
    }

    fun injectMenuBarjavandRepository(): MenuBarjavandRepository
    fun injectBarjavandRepository(): BarjavandRepository

    companion object {
        fun builder(componentProvider: ComponentProviderActivity): DataBarjavandComponent {
            return (componentProvider.provideComponent(
                ComponentsKey.DATA_BARJAVAND,
                DaggerDataBarjavandComponent.factory().create(
                    baseComponent = BaseComponent.builder(componentProvider),
                    dataComponent = DataComponent.builder(componentProvider)
                )
            )) as DataBarjavandComponent
        }
    }
}