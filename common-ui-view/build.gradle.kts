plugins {
    id(BuildPlugins.MODULE_PLUGIN)
    id(BuildPlugins.PARCELIZE_Plugins)
    id(BuildPlugins.KOTLIN_KAPT)
}

android {
    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs.plus("-Xjvm-default=enable")
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
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
    addPersianDate()
    addNavigationComponent()
    addGlide()
    addAppCompat()
}