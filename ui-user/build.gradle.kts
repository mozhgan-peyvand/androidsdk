plugins {
    id(BuildPlugins.MODULE_PLUGIN)
    id(BuildPlugins.KOTLIN_KAPT)
}

android{
    composeOptions {
        kotlinCompilerExtensionVersion = "1.0.1"
    }
    buildFeatures {
        compose = true
    }
}
dependencies {
    addDependency(
        listOf(
            BuildModules.Libraries.BASE,
            BuildModules.Libraries.DOMAIN,
            BuildModules.Libraries.COMMON_UI_VIEW,
            BuildModules.Libraries.DATA
        )
    )

    addKotshi()
    addCoroutine()
    addDagger2()
    addCompose()
    addArchitectureComponents()
    addAndroidMaterial()
    addNavigationComponent()
}