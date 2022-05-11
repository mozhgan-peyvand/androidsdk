package ir.part.sdk.ara.base.di

import androidx.activity.ComponentActivity

open class ComponentProviderActivity : ComponentActivity() {

    private val components: MutableMap<ComponentsKey, BasicComponent?> = mutableMapOf()

    fun removeComponent(key: ComponentsKey) {
        components.remove(key)
    }

    private fun getComponent(key: ComponentsKey): BasicComponent? = components[key]

    private fun addComponent(key: ComponentsKey, component: BasicComponent) = component.also {
        components[key] = component
    }

    fun provideComponent(key: ComponentsKey, component: BasicComponent?): BasicComponent? {
        return getComponent(key) ?: component?.let {
            addComponent(key, component)
        }
    }

//    fun provideComponent(key: ComponentsKey, component: () -> BasicComponent): BasicComponent? {
//        return getComponent(key) ?: synchronized(components) {
//            component().also { addComponent(key, it) }
//        }
//    }

}

