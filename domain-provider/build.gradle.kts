plugins {
    id(BuildPlugins.MODULE_PLUGIN)
    id(BuildPlugins.KOTLIN_KAPT)
}

dependencies {
    addDependency(
        listOf(
            BuildModules.Libraries.BASE,
            BuildModules.Libraries.DOMAIN,
            BuildModules.Libraries.DATA
        )
    )

    addDagger2()
}