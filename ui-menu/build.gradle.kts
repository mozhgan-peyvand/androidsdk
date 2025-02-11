plugins {
    id(BuildPlugins.MODULE_PLUGIN)
    id(BuildPlugins.KOTLIN_KAPT)
}

dependencies {
    addDependency(
        listOf(
            BuildModules.Libraries.BASE,
            BuildModules.Libraries.DOMAIN,
            BuildModules.Libraries.COMMON_UI_VIEW,
            BuildModules.Libraries.UI_SHARED_FEATURE,
            BuildModules.Libraries.DOMAIN_PROVIDER
        )
    )

    addKotshi()
    addCoroutine()
    addDagger2()
    addArchitectureComponents()
    addAndroidMaterial()
    addNavigationComponent()
    addConstrainLayout()
}