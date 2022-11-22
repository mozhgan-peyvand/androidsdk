package ir.part.sdk.ara.base.di

import android.content.Context
import androidx.activity.ComponentActivity
import dagger.BindsInstance
import dagger.Component
import ir.part.sdk.ara.base.di.scopes.BaseScope
import ir.part.sdk.ara.base.util.CoroutinesDispatcherProvider

@BaseScope
@Component(
    modules = [
        BaseModule::class
    ]
)
interface BaseComponent : BasicComponent {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance applicationContext: Context
        ): BaseComponent
    }

    fun injectContext(): Context
    fun injectCoroutinesDispatcherProvider(): CoroutinesDispatcherProvider

    companion object {
        fun builder(componentProvider: ComponentProviderActivity): BaseComponent {
            return (componentProvider.provideComponent(
                ComponentsKey.BASE,
                DaggerBaseComponent.factory().create(
                    applicationContext = (componentProvider as ComponentActivity).applicationContext
                )
            )) as BaseComponent
        }
    }

}