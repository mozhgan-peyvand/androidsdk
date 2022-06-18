plugins {
    id(BuildPlugins.MODULE_PLUGIN)
    id(BuildPlugins.PARCELIZE_Plugins)
    id(BuildPlugins.KOTLIN_KAPT)
}

dependencies {
    addDependency(
        listOf(
            BuildModules.Libraries.BASE,
            BuildModules.Libraries.DATA,
            BuildModules.Libraries.COMMON_UI_RESOURCE
        )
    )

    addDagger2()
    addConstrainLayout()
    addAndroidMaterial()
    addArchitectureComponents()
    addNavigationComponent()
    addLottie()
    addGlide()
    addAppCompat()
}