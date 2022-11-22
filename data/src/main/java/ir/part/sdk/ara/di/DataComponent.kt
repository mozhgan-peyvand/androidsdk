package ir.part.sdk.ara.di

import android.content.SharedPreferences
import dagger.Component
import ir.part.sdk.ara.base.di.*
import ir.part.sdk.ara.base.di.scopes.DataScope
import retrofit2.Retrofit

@DataScope
@Component(
    dependencies = [
        BaseComponent::class
    ],
    modules = [
        NetworkModule::class,
        DataModule::class
    ]
)
interface DataComponent : BasicComponent {

    @Component.Factory
    interface Factory {
        fun create(
            baseComponent: BaseComponent
        ): DataComponent
    }

    @SK
    fun injectSK(): String
    fun injectSharedPref(): SharedPreferences
    fun injectRetrofit(): Retrofit
    @DK
    fun dK(): String

    companion object {
        fun builder(componentProvider: ComponentProviderActivity): DataComponent {
            return (componentProvider.provideComponent(
                ComponentsKey.DATA,
                DaggerDataComponent.factory().create(
                    baseComponent = BaseComponent.builder(componentProvider)
                )
            )) as DataComponent
        }
    }

}