package ir.part.sdk.ara.ui.menu.di

import dagger.Component
import ir.part.sdk.ara.base.di.*
import ir.part.sdk.ara.common.ui.view.AraViewModelFactory
import ir.part.sdk.ara.data.barjavand.di.BarjavandComponent
import ir.part.sdk.ara.data.userManager.di.DataUserManagerComponent
import ir.part.sdk.ara.domain.menu.di.DomainMenuComponent
import ir.part.sdk.ara.domain.user.di.DomainUserManagerComponent
import ir.part.sdk.ara.ui.menu.screens.MenuViewModel
import ir.part.sdk.ara.ui.menu.screens.comment.SubmitCommentViewModel
import ir.part.sdk.ara.ui.menu.screens.rahyar.RahyarViewModel

@FeatureScope
@Component(
    dependencies = [
        BaseComponent::class,
        DomainUserManagerComponent::class,
        DataUserManagerComponent::class,
        DomainMenuComponent::class,
        BarjavandComponent::class,
    ]
)
interface MenuComponent : BasicComponent {

    @Component.Factory
    interface Factory {
        fun create(
            baseComponent: BaseComponent,
            domainUserManagerComponent: DomainUserManagerComponent,
            dataUserManagerComponent: DataUserManagerComponent,
            domainMenuComponent: DomainMenuComponent,
            barjavandComponent: BarjavandComponent,
        ): MenuComponent
    }

    fun getMenuViewModel(): AraViewModelFactory<MenuViewModel>
    fun getRahyarViewModel(): AraViewModelFactory<RahyarViewModel>
    fun getSubmitCommentViewModel(): AraViewModelFactory<SubmitCommentViewModel>


    companion object {
        fun builder(componentProvider: ComponentProviderActivity): MenuComponent {
            return (componentProvider.provideComponent(
                ComponentsKey.UI_MENU,
                DaggerMenuComponent.factory().create(
                    baseComponent = BaseComponent.builder(componentProvider),
                    domainUserManagerComponent = DomainUserManagerComponent.builder(
                        componentProvider),
                    dataUserManagerComponent = DataUserManagerComponent.builder(componentProvider),
                    domainMenuComponent = DomainMenuComponent.builder(componentProvider),
                    barjavandComponent = BarjavandComponent.builder(componentProvider)
                )
            )) as MenuComponent
        }
    }
}