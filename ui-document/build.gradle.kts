plugins {
    id(BuildPlugins.MODULE_PLUGIN)
    id(BuildPlugins.KOTLIN_KAPT)
}

dependencies {
    implementation(project(mapOf("path" to ":ui-shared-feature")))
    addDependency(
        listOf(
            BuildModules.Libraries.BASE,
            BuildModules.Libraries.DOMAIN,
            BuildModules.Libraries.DATA,
            BuildModules.Libraries.COMMON_UI_VIEW,
            BuildModules.Libraries.UI_SHARED_FEATURE

        )
    )

    addKotshi()
    addCoroutine()
    addDagger2()
    addArchitectureComponents()
    addAndroidMaterial()
    addConstrainLayout()
    addAndroidMaterial()
    addNavigationComponent()
    addAppCompat()
    addJsonLogic()
    addLottie()
    addSwipeRefresh()
    addPager()

}