package ir.part.sdk.ara.builder.di

import dagger.Component
import ir.part.sdk.ara.base.di.*
import ir.part.sdk.ara.builder.ui.view.HomeActivity
import ir.part.sdk.ara.common.ui.view.di.CommonUiComponent
import ir.part.sdk.ara.data.barjavand.di.BarjavandComponent
import ir.part.sdk.ara.data.dashboard.di.DataDashboardComponent
import ir.part.sdk.ara.data.state.di.DataStateComponent
import ir.part.sdk.ara.data.userManager.di.DataUserManagerComponent
import ir.part.sdk.ara.domain.menu.di.DomainMenuComponent
import ir.part.sdk.ara.domain.tasks.di.DomainTaskComponent
import ir.part.sdk.ara.domain.user.di.DomainUserManagerComponent
import ir.part.sdk.ara.ui.menu.di.MenuComponent
import ir.part.sdk.ara.ui.document.di.DocumentComponent
import ir.part.sdk.ara.ui.user.di.UserComponent

@MainScope
@Component(
    dependencies = [
        BaseComponent::class,
        CommonUiComponent::class,
        DomainUserManagerComponent::class,
        DataUserManagerComponent::class,
        BarjavandComponent::class,
        DataDashboardComponent::class,
        DataStateComponent::class,
        DocumentComponent::class,
        DomainTaskComponent::class,
        UserComponent::class,
        DomainMenuComponent::class,
        MenuComponent::class
    ],
)
interface BuilderComponent : BasicComponent {

    @Component.Factory
    interface Factory {
        fun create(
            baseComponent: BaseComponent,
            commonUiComponent: CommonUiComponent,
            domainUserManagerComponent: DomainUserManagerComponent,
            dataUserManagerComponent: DataUserManagerComponent,
            barjavandComponent: BarjavandComponent,
            dataDashboardComponent: DataDashboardComponent,
            dataStateComponent: DataStateComponent,
            domainDocumentComponent: DocumentComponent,
            domainTaskComponent: DomainTaskComponent,
            userComponent: UserComponent,
            menuComponent: MenuComponent,
            domainMenuComponent: DomainMenuComponent
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
                    domainUserManagerComponent = DomainUserManagerComponent.builder(
                        componentProvider
                    ),
                    dataUserManagerComponent = DataUserManagerComponent.builder(componentProvider),
                    barjavandComponent = BarjavandComponent.builder(componentProvider),
                    dataDashboardComponent = DataDashboardComponent.builder(componentProvider),
                    dataStateComponent = DataStateComponent.builder(componentProvider),
                    domainDocumentComponent = DocumentComponent.builder(componentProvider),
                    domainTaskComponent = DomainTaskComponent.builder(componentProvider),
                    userComponent = UserComponent.builder(componentProvider),
                    menuComponent = MenuComponent.builder(componentProvider),
                    domainMenuComponent = DomainMenuComponent.builder(componentProvider)
                )
            )) as BuilderComponent
        }
    }

}