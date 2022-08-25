package ir.part.sdk.ara.ui.menu.di

import dagger.Component
import ir.part.sdk.ara.base.di.BaseComponent
import ir.part.sdk.ara.base.di.BasicComponent
import ir.part.sdk.ara.base.di.ComponentProviderActivity
import ir.part.sdk.ara.base.di.ComponentsKey
import ir.part.sdk.ara.data.barjavand.di.BarjavandComponent
import ir.part.sdk.ara.data.userManager.di.DataUserManagerComponent
import ir.part.sdk.ara.domain.menu.di.DomainMenuComponent
import ir.part.sdk.ara.ui.menu.screens.comment.SubmitCommentViewModel


@SubmitCommentScreenScope
@Component(
    dependencies = [
        BaseComponent::class,
        DomainMenuComponent::class,
        BarjavandComponent::class,
        DataUserManagerComponent::class
    ]
)
interface SubmitCommentScreenComponent : BasicComponent {

    @Component.Factory
    interface Factory {
        fun create(
            baseComponent: BaseComponent,
            domainMenuComponent: DomainMenuComponent,
            barjavandComponent: BarjavandComponent,
            dataUserManagerComponent: DataUserManagerComponent,
        ): SubmitCommentScreenComponent
    }

    fun getViewModel(): SubmitCommentViewModel

    companion object {
        fun builder(componentProvider: ComponentProviderActivity): SubmitCommentScreenComponent {
            return (componentProvider.provideComponent(
                ComponentsKey.UI_MENU_COMMENT,
                DaggerSubmitCommentScreenComponent.factory().create(
                    baseComponent = BaseComponent.builder(componentProvider),
                    domainMenuComponent = DomainMenuComponent.builder(componentProvider),
                    barjavandComponent = BarjavandComponent.builder(componentProvider),
                    dataUserManagerComponent = DataUserManagerComponent.builder(componentProvider)
                )
            )) as SubmitCommentScreenComponent
        }
    }
}

