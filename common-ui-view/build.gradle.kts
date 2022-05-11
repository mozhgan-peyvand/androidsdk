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
    addCompose()
}
android {
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    composeOptions {

        kotlinCompilerExtensionVersion = "1.0.1"
    }
    buildFeatures {
        compose = true
    }

}

