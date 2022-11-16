package ir.part.sdk.ara.ui.menu.di

import dagger.Component
import ir.part.sdk.ara.base.di.BaseComponent
import ir.part.sdk.ara.base.di.BasicComponent
import ir.part.sdk.ara.base.di.ComponentProviderActivity
import ir.part.sdk.ara.base.di.ComponentsKey
import ir.part.sdk.ara.common.ui.view.AraViewModelFactory
import ir.part.sdk.ara.domain.provider.components.barjavand.DomainProviderBarjavandComponent
import ir.part.sdk.ara.domain.provider.components.userManager.DomainProviderUserManagerComponent
import ir.part.sdk.ara.ui.menu.screens.MenuViewModel
import ir.part.sdk.ara.ui.menu.screens.comment.SubmitCommentViewModel
import ir.part.sdk.ara.ui.menu.screens.rahyar.RahyarViewModel

//@FeatureScope
@Component(
    dependencies = [
        BaseComponent::class,
        DomainProviderUserManagerComponent::class,
        DomainProviderBarjavandComponent::class
    ]
)
interface MenuComponent : BasicComponent {

    @Component.Factory
    interface Factory {
        fun create(
            baseComponent: BaseComponent,
            domainProviderBarjavandComponent: DomainProviderBarjavandComponent,
            domainProviderUserManagerComponent: DomainProviderUserManagerComponent
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
                    domainProviderBarjavandComponent = DomainProviderBarjavandComponent.builder(
                        componentProvider
                    ),
                    domainProviderUserManagerComponent = DomainProviderUserManagerComponent.builder(
                        componentProvider
                    )
                )
            )
                    ) as MenuComponent
        }
    }
}