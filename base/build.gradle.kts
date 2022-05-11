plugins {
    id(BuildPlugins.MODULE_PLUGIN)
    id(BuildPlugins.KOTLIN_KAPT)
}
dependencies {
    addCoroutine()
    addDagger2()
    addMoshi()
    addKotshi()
    addSecurity()
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

