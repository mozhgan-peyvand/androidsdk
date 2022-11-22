package ir.part.sdk.ara.data.userManager.di

import dagger.Component
import ir.part.sdk.ara.base.di.BaseComponent
import ir.part.sdk.ara.base.di.BasicComponent
import ir.part.sdk.ara.base.di.ComponentProviderActivity
import ir.part.sdk.ara.base.di.ComponentsKey
import ir.part.sdk.ara.base.di.scopes.FeatureDataScope
import ir.part.sdk.ara.di.DataComponent
import ir.part.sdk.ara.domain.user.repository.UserManagerRepository

@FeatureDataScope
@Component(
    dependencies = [
        BaseComponent::class,
        DataComponent::class,
    ],
    modules = [
        RepositoryUserManagerModule::class,
        ServiceUserManagerModule::class
    ]
)

interface DataUserManagerComponent : BasicComponent {

    @Component.Factory
    interface Factory {
        fun create(
            baseComponent: BaseComponent,
            dataComponent: DataComponent
        ): DataUserManagerComponent
    }

    fun injectUserManagerRepository(): UserManagerRepository

    companion object {
        fun builder(componentProvider: ComponentProviderActivity): DataUserManagerComponent {
            return (componentProvider.provideComponent(
                ComponentsKey.DATA_USER_MANAGER,
                DaggerDataUserManagerComponent.factory().create(
                    baseComponent = BaseComponent.builder(componentProvider),
                    dataComponent = DataComponent.builder(componentProvider)
                )
            )) as DataUserManagerComponent
        }
    }
}