plugins {
    id(BuildPlugins.MODULE_PLUGIN)
    id(BuildPlugins.KOTLIN_KAPT)
}

dependencies {
    addDependency(
        listOf(
            BuildModules.Libraries.BASE
        )
    )

    addKotshi()
    addCoroutine()
    addDagger2()
    addArchitectureComponents()
    addAndroidMaterial()
}