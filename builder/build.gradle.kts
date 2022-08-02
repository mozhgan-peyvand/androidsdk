plugins {
    id(BuildPlugins.MODULE_PLUGIN)
    id(BuildPlugins.KOTLIN_ANDROID)
    id(BuildPlugins.PARCELIZE_Plugins)
    id(BuildPlugins.KOTLIN_KAPT)
    id(BuildPlugins.NAVIGATION_SAFE_ARGS)
}
android {
    compileSdk = BuildAndroidConfig.COMPILE_SDK_VERSION
    defaultConfig {
        multiDexEnabled = true
    }
    sourceSets {
        getByName("main").java.srcDir("src/main/kotlin")
        getByName("main").java.srcDir(file("$buildDir/generated/source/kapt/main"))
        getByName("test").java.srcDir("src/test/kotlin")
        getByName("androidTest").java.srcDir("src/androidTest/kotlin")
    }
}

dependencies {
    addDependency(
        listOf(
            BuildModules.Libraries.BASE,
            BuildModules.Libraries.DOMAIN,
            BuildModules.Libraries.DATA,
            BuildModules.Libraries.COMMON_UI_RESOURCE,
            BuildModules.Libraries.COMMON_UI_VIEW,
            BuildModules.Libraries.UI_USER,
            BuildModules.Libraries.UI_MENU
        )
    )

    addDagger2()
    addConstrainLayout()
    addSecurity()
    addAndroidMaterial()
    addArchitectureComponents()
    addNavigationComponent()
    addAppCompat()
    addSqlLite()
    addRetrofit()
    addPinentryEditText()
}


