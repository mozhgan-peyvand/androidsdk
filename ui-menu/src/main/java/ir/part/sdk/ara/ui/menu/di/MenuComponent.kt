package ir.part.sdk.ara.ui.menu.di

import dagger.Component
import ir.part.sdk.ara.base.di.*
import ir.part.sdk.ara.common.ui.view.AraViewModelFactory
import ir.part.sdk.ara.data.barjavand.di.BarjavandComponent
import ir.part.sdk.ara.domain.menu.di.DomainMenuComponent
import ir.part.sdk.ara.ui.menu.screens.comment.SubmitCommentViewModel


@FeatureScope
@Component(
    dependencies = [
        BaseComponent::class,
        DomainMenuComponent::class,
        BarjavandComponent::class
    ]
)
interface MenuComponent : BasicComponent {

    @Component.Factory
    interface Factory {
        fun create(
            baseComponent: BaseComponent,
            domainMenuComponent: DomainMenuComponent,
            barjavandComponent: BarjavandComponent,
        ): MenuComponent
    }

    fun getSubmitCommentViewModelProvider(): AraViewModelFactory<SubmitCommentViewModel>

    companion object {
        fun builder(componentProvider: ComponentProviderActivity): MenuComponent {
            return (componentProvider.provideComponent(
                ComponentsKey.UI_MENU,
                DaggerMenuComponent.factory().create(
                    baseComponent = BaseComponent.builder(componentProvider),
                    domainMenuComponent = DomainMenuComponent.builder(componentProvider),
                    barjavandComponent = BarjavandComponent.builder(componentProvider)

                )
            )) as MenuComponent
        }
    }
}

