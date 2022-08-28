package ir.part.sdk.ara.ui.menu.di

import dagger.Component
import ir.part.sdk.ara.base.di.BaseComponent
import ir.part.sdk.ara.base.di.BasicComponent
import ir.part.sdk.ara.base.di.ComponentProviderActivity
import ir.part.sdk.ara.base.di.ComponentsKey
import ir.part.sdk.ara.data.userManager.di.DataUserManagerComponent
import ir.part.sdk.ara.domain.user.di.DomainUserManagerComponent
import ir.part.sdk.ara.ui.menu.screens.MenuViewModel

@MenuScreenScope
@Component(
    dependencies = [
        BaseComponent::class,
        DomainUserManagerComponent::class,
        DataUserManagerComponent::class
    ]
)
interface MenuScreenComponent : BasicComponent {

    @Component.Factory
    interface Factory {
        fun create(
            baseComponent: BaseComponent,
            domainUserManagerComponent: DomainUserManagerComponent,
            dataUserManagerComponent: DataUserManagerComponent,
        ): MenuScreenComponent
    }

    fun getMenuViewModel(): MenuViewModel

    companion object {
        fun builder(componentProvider: ComponentProviderActivity): MenuScreenComponent {
            return (componentProvider.provideComponent(
                ComponentsKey.UI_MENU,
                DaggerMenuScreenComponent.factory().create(
                    baseComponent = BaseComponent.builder(componentProvider),
                    domainUserManagerComponent = DomainUserManagerComponent.builder(
                        componentProvider),
                    dataUserManagerComponent = DataUserManagerComponent.builder(componentProvider)
                )
            )) as MenuScreenComponent
        }
    }
}