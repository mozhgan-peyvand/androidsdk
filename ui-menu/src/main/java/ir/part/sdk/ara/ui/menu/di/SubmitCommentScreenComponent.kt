package ir.part.sdk.ara.ui.menu.di

import dagger.Component
import ir.part.sdk.ara.base.di.BaseComponent
import ir.part.sdk.ara.base.di.BasicComponent
import ir.part.sdk.ara.base.di.ComponentProviderActivity
import ir.part.sdk.ara.base.di.ComponentsKey
import ir.part.sdk.ara.data.barjavand.di.BarjavandComponent
import ir.part.sdk.ara.domain.menu.di.DomainMenuComponent
import ir.part.sdk.ara.ui.menu.screens.comment.SubmitCommentViewModel


@SubmitCommentScreenScope
@Component(
    dependencies = [
        BaseComponent::class,
        DomainMenuComponent::class,
        BarjavandComponent::class
    ]
)
interface SubmitCommentScreenComponent : BasicComponent {

    @Component.Factory
    interface Factory {
        fun create(
            baseComponent: BaseComponent,
            domainMenuComponent: DomainMenuComponent,
            barjavandComponent: BarjavandComponent,
        ): SubmitCommentScreenComponent
    }

    fun getViewModel(): SubmitCommentViewModel

    companion object {
        fun builder(componentProvider: ComponentProviderActivity): SubmitCommentScreenComponent {
            return (componentProvider.provideComponent(
                ComponentsKey.UI_MENU,
                DaggerSubmitCommentScreenComponent.factory().create(
                    baseComponent = BaseComponent.builder(componentProvider),
                    domainMenuComponent = DomainMenuComponent.builder(componentProvider),
                    barjavandComponent = BarjavandComponent.builder(componentProvider)
                )
            )) as SubmitCommentScreenComponent
        }
    }
}

