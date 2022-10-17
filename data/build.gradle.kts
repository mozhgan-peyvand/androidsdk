plugins {
    id(BuildPlugins.MODULE_PLUGIN)
    id(BuildPlugins.PARCELIZE_Plugins)
    id(BuildPlugins.KOTLIN_KAPT)
}
android {
    defaultConfig {
        buildConfigField("int", "VERSION_CODE", "${defaultConfig.versionCode}")
    }
}

dependencies {
    addDependency(
        listOf(
            BuildModules.Libraries.BASE,
            BuildModules.Libraries.DOMAIN
        )
    )
    addDagger2()
    addMoshi()
    addArchitectureComponents()
    addKotshi()
    addRetrofit()
    addCoroutine()
}

