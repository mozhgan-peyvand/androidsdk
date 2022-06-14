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
        minSdk = Ext.minimumSdkVersion
        targetSdk = BuildAndroidConfig.TARGET_SDK_VERSION
        vectorDrawables.useSupportLibrary = BuildAndroidConfig.SUPPORT_LIBRARY_VECTOR_DRAWABLES
//        versionCode = generateVersionCode()
//        versionName = generateVersionName()
    }
    sourceSets {
        getByName("main").java.srcDir("src/main/kotlin")
        getByName("main").java.srcDir(file("$buildDir/generated/source/kapt/main"))
        getByName("test").java.srcDir("src/test/kotlin")
        getByName("androidTest").java.srcDir("src/androidTest/kotlin")
    }
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

dependencies {
    addDependency(
        listOf(
            BuildModules.Libraries.BASE,
            BuildModules.Libraries.DOMAIN,
            BuildModules.Libraries.DATA,
            BuildModules.Libraries.COMMON_UI_RESOURCE,
            BuildModules.Libraries.COMMON_UI_VIEW,
            BuildModules.Libraries.UI_USER
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
    addCompose()
}


