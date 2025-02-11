plugins {
    id(BuildPlugins.MODULE_PLUGIN)
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
            BuildModules.Libraries.COMMON_UI_VIEW,
            BuildModules.Libraries.DOMAIN,
            BuildModules.Libraries.BASE,
            BuildModules.Libraries.DOMAIN_PROVIDER
        )
    )
    addLottie()
    addConstrainLayout()
    addDagger2()
    addKotshi()
    addCoroutine()
    addArchitectureComponents()
    addAndroidMaterial()
    addNavigationComponent()
}