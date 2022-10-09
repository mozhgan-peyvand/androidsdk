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
import ir.part.sdk.ara.domain.version.di.DomainHomeComponent
import ir.part.sdk.ara.home.di.HomeComponent
import ir.part.sdk.ara.ui.document.di.DocumentComponent
import ir.part.sdk.ara.ui.menu.di.MenuScreenComponent
import ir.part.sdk.ara.ui.menu.di.RahyarScreenComponent
import ir.part.sdk.ara.ui.menu.di.SubmitCommentScreenComponent
import ir.part.sdk.ara.ui.shared.feature.di.SharedFeatureComponent
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
        DomainHomeComponent::class,
        SharedFeatureComponent::class,
        SubmitCommentScreenComponent::class,
        RahyarScreenComponent::class,
        MenuScreenComponent::class,
        HomeComponent::class,
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
            domainUserManagerComponent: DomainUserManagerComponent,
            dataUserManagerComponent: DataUserManagerComponent,
            barjavandComponent: BarjavandComponent,
            dataDashboardComponent: DataDashboardComponent,
            dataStateComponent: DataStateComponent,
            domainDocumentComponent: DocumentComponent,
            domainTaskComponent: DomainTaskComponent,
            userComponent: UserComponent,
            domainMenuComponent: DomainMenuComponent,
            domainHomeComponent: DomainHomeComponent,
            sharedFeatureComponent: SharedFeatureComponent,
            menuComponent: SubmitCommentScreenComponent,
            rahyarComponent: RahyarScreenComponent,
            menuScreenComponent: MenuScreenComponent,
            homeComponent: HomeComponent,
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
                    domainMenuComponent = DomainMenuComponent.builder(componentProvider),
                    domainHomeComponent = DomainHomeComponent.builder(componentProvider),
                    sharedFeatureComponent = SharedFeatureComponent.builder(componentProvider),
                    menuComponent = SubmitCommentScreenComponent.builder(componentProvider),
                    rahyarComponent = RahyarScreenComponent.builder(componentProvider),
                    menuScreenComponent = MenuScreenComponent.builder(componentProvider),
                    homeComponent = HomeComponent.builder(componentProvider)
                )
            )) as BuilderComponent
        }
    }

}