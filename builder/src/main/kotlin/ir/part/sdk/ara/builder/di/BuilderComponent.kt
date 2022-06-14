package ir.part.sdk.ara.builder.di

import dagger.Component
import ir.part.sdk.ara.base.di.*
import ir.part.sdk.ara.builder.ui.view.HomeActivity
import ir.part.sdk.ara.common.ui.view.di.CommonUiComponent
import ir.part.sdk.ara.data.barjavand.di.BarjavandComponent
import ir.part.sdk.ara.data.dashboard.di.DataDashboardComponent
import ir.part.sdk.ara.data.state.di.DataStateComponent
import ir.part.sdk.ara.data.userManager.di.DataUserManagerComponent
import ir.part.sdk.ara.domain.document.di.DomainDocumentComponent
import ir.part.sdk.ara.domain.tasks.di.DomainTaskComponent
import ir.part.sdk.ara.domain.user.di.DomainUserManagerComponent
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
        DomainDocumentComponent::class,
        DomainTaskComponent::class,
        UserComponent::class

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
            domainDocumentComponent: DomainDocumentComponent,
            domainTaskComponent: DomainTaskComponent,
            userComponent: UserComponent
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
                    domainDocumentComponent = DomainDocumentComponent.builder(componentProvider),
                    domainTaskComponent = DomainTaskComponent.builder(componentProvider),
                    userComponent = UserComponent.builder(componentProvider)
                )
            )) as BuilderComponent
        }
    }

}