plugins {
    id(BuildPlugins.MODULE_PLUGIN)
}

dependencies {
    addAndroidMaterial()
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

