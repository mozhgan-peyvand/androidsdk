package ir.part.sdk.ara.builder.di

import dagger.Component
import ir.part.sdk.ara.base.di.BaseComponent
import ir.part.sdk.ara.base.di.BasicComponent
import ir.part.sdk.ara.base.di.ComponentProviderActivity
import ir.part.sdk.ara.base.di.ComponentsKey
import ir.part.sdk.ara.base.di.scopes.MainScope
import ir.part.sdk.ara.builder.ui.view.HomeActivity
import ir.part.sdk.ara.common.ui.view.di.CommonUiComponent
import ir.part.sdk.ara.data.barjavand.di.DataBarjavandComponent
import ir.part.sdk.ara.data.dashboard.di.DataDashboardComponent
import ir.part.sdk.ara.data.state.di.DataStateComponent
import ir.part.sdk.ara.data.userManager.di.DataUserManagerComponent
import ir.part.sdk.ara.domain.provider.components.barjavand.DomainProviderBarjavandComponent
import ir.part.sdk.ara.domain.provider.components.dashboard.DomainProviderDashboardComponent
import ir.part.sdk.ara.domain.provider.components.payment.DomainProviderPaymentComponent
import ir.part.sdk.ara.domain.provider.components.state.DomainProviderStateComponent
import ir.part.sdk.ara.domain.provider.components.userManager.DomainProviderUserManagerComponent
import ir.part.sdk.ara.home.di.HomeComponent
import ir.part.sdk.ara.ui.document.di.DocumentComponent
import ir.part.sdk.ara.ui.menu.di.MenuComponent
import ir.part.sdk.ara.ui.shared.feature.di.SharedFeatureComponent
import ir.part.sdk.ara.ui.user.di.UserComponent

@MainScope
@Component(
    dependencies = [
        BaseComponent::class,
        CommonUiComponent::class,
        DataUserManagerComponent::class,
        DataBarjavandComponent::class,
        DataDashboardComponent::class,
        DataStateComponent::class,
        DocumentComponent::class,
        UserComponent::class,
        SharedFeatureComponent::class,
        MenuComponent::class,
        HomeComponent::class,
        DomainProviderUserManagerComponent::class,
        DomainProviderStateComponent::class,
        DomainProviderDashboardComponent::class,
        DomainProviderPaymentComponent::class,
        DomainProviderBarjavandComponent::class
    ],
    modules = [
        ViewModelModule::class
    ]
)
interface BuilderComponent : BasicComponent {

    @Component.Factory
    interface Factory {
        fun create(
            baseComponent: BaseComponent,
            commonUiComponent: CommonUiComponent,
            dataUserManagerComponent: DataUserManagerComponent,
            barjavandComponent: DataBarjavandComponent,
            dataDashboardComponent: DataDashboardComponent,
            dataStateComponent: DataStateComponent,
            DocumentComponent: DocumentComponent,
            userComponent: UserComponent,
            sharedFeatureComponent: SharedFeatureComponent,
            menuScreenComponent: MenuComponent,
            homeComponent: HomeComponent,
            domainProviderUserManagerComponent: DomainProviderUserManagerComponent,
            domainProviderStateComponent: DomainProviderStateComponent,
            domainProviderDashboardComponent: DomainProviderDashboardComponent,
            domainProviderPaymentComponent: DomainProviderPaymentComponent,
            domainProviderBarjavandComponent: DomainProviderBarjavandComponent
        ): BuilderComponent
    }

    fun inject(homeActivity: HomeActivity)

    companion object {
        fun builder(componentProvider: ComponentProviderActivity): BuilderComponent {
            return (componentProvider.provideComponent(
                ComponentsKey.Ara,
                DaggerBuilderComponent.factory().create(
                    baseComponent = BaseComponent.builder(componentProvider),
                    commonUiComponent = CommonUiComponent.builder(componentProvider),
                    dataUserManagerComponent = DataUserManagerComponent.builder(componentProvider),
                    barjavandComponent = DataBarjavandComponent.builder(componentProvider),
                    dataDashboardComponent = DataDashboardComponent.builder(componentProvider),
                    dataStateComponent = DataStateComponent.builder(componentProvider),
                    DocumentComponent = DocumentComponent.builder(componentProvider),
                    userComponent = UserComponent.builder(componentProvider),
                    sharedFeatureComponent = SharedFeatureComponent.builder(componentProvider),
                    menuScreenComponent = MenuComponent.builder(componentProvider),
                    homeComponent = HomeComponent.builder(componentProvider),
                    domainProviderUserManagerComponent = DomainProviderUserManagerComponent.builder(
                        componentProvider
                    ),
                    domainProviderStateComponent = DomainProviderStateComponent.builder(
                        componentProvider
                    ),
                    domainProviderDashboardComponent = DomainProviderDashboardComponent.builder(
                        componentProvider
                    ),
                    domainProviderPaymentComponent = DomainProviderPaymentComponent.builder(
                        componentProvider
                    ),
                    domainProviderBarjavandComponent = DomainProviderBarjavandComponent.builder(
                        componentProvider
                    )
                )
            )) as BuilderComponent
        }
    }

}