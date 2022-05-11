import java.io.ByteArrayOutputStream
import java.util.*

plugins {
    id(BuildPlugins.ANDROID_APPLICATION)
    id(BuildPlugins.ORG_JETBRAINS_KOTLIN_ANDROID)
}

apply(from = "projectDependencyGraphTask.gradle")

fun getGitHash(): String {
    val stdout = ByteArrayOutputStream()
    exec {
        commandLine("git", "rev-parse", "--short", "HEAD")
        standardOutput = stdout
    }
    return stdout.toString().trim()
}
android {
    compileSdk = BuildAndroidConfig.COMPILE_SDK_VERSION
    defaultConfig {
        applicationId = BuildAndroidConfig.APPLICATION_ID
        multiDexEnabled = true
        minSdk = Ext.minimumSdkVersion
        targetSdk = BuildAndroidConfig.TARGET_SDK_VERSION
        testInstrumentationRunner = BuildAndroidConfig.TEST_INSTRUMENTATION_RUNNER
        vectorDrawables.useSupportLibrary = BuildAndroidConfig.SUPPORT_LIBRARY_VECTOR_DRAWABLES
        versionCode = generateVersionCode()
        versionName = generateVersionName()
    }
    signingConfigs {
        create("release") {
            val props = file(".signing/signing.properties")
            val keystore = file(".signing/release.jks")
            if (props.exists()) {
                val signing = Properties()
                props.inputStream().use {
                    signing.apply { load(it) }
                }
                if (signing.containsKey("KEY_ALIAS"))
                    keyAlias = signing.getProperty("KEY_ALIAS")
                if (signing.containsKey("KEYSTORE_PASSWORD"))
                    storePassword = signing.getProperty("KEYSTORE_PASSWORD")
                if (signing.containsKey("KEY_PASSWORD"))
                    keyPassword = signing.getProperty("KEY_PASSWORD")
                storeFile = file(".signing-debug/debug.jks")
            }
            if (keystore.exists())
                storeFile = keystore
        }
        getByName("debug") {
            val props = file(".signing-debug/signing.properties")
            val keystore = file(".signing-debug/debug.jks")
            if (props.exists()) {
                val signing = Properties()
                props.inputStream().use {
                    signing.apply { load(it) }
                }
                if (signing.containsKey("KEYSTORE_PASSWORD"))
                    storePassword = signing.getProperty("KEYSTORE_PASSWORD")
                if (signing.containsKey("KEY_PASSWORD"))
                    keyPassword = signing.getProperty("KEY_PASSWORD")
                if (signing.containsKey("KEY_ALIAS"))
                    keyAlias = signing.getProperty("KEY_ALIAS")
            }
            if (keystore.exists())
                storeFile = keystore
        }
    }
    buildTypes {
        getByName("release") {
            isShrinkResources = true
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }
    flavorDimensions.add("mode")
    productFlavors {
        create("dev") {
            dimension = "mode"
            applicationIdSuffix = ".dev"
            versionName = "dev-build${getDate()}-g${getGitHash()}"
            // The following configuration limits the "dev" flavor to using
            // English string resources and xxhdpi screen-density resources.
            resourceConfigurations.addAll(listOf("en", "xxhdpi"))
            // Disable PNG crunching
            aaptOptions.cruncherEnabled = false
            // Disable Split apk in development
            splits {
                abi {
                    isEnable = false
                }
                density {
                    isEnable = false
                }
            }
        }
        create("prodtest") {
            dimension = "mode"
            applicationIdSuffix = ".prodtest"
            signingConfig = signingConfigs.getByName("release")
        }
        create("prod") {
            dimension = "mode"
            signingConfig = signingConfigs.getByName("release")
        }
    }

    sourceSets {
        getByName("main").java.srcDir("src/main/kotlin")
        getByName("main").java.srcDir(file("$buildDir/generated/source/kapt/main"))
        getByName("test").java.srcDir("src/test/kotlin")
        getByName("androidTest").java.srcDir("src/androidTest/kotlin")
    }
    buildFeatures {
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    composeOptions {

        kotlinCompilerExtensionVersion = "1.0.1"
    }

    applicationVariants.all {
        val variant = this
        variant.outputs
            .map { it as com.android.build.gradle.internal.api.BaseVariantOutputImpl }
            .forEach { output ->
                if (variant.flavorName == "prod" || variant.flavorName == "prodtest") {
                    val outputFileName =
                        "Sample-v${variant.versionName}(${variant.versionCode})-${variant.flavorName}-g${getGitHash()}-${variant.buildType.name}.apk"
                    output.outputFileName = outputFileName
                } else {
                    val outputFileName =
                        "Sample-build${getDate()}-${variant.flavorName}-g${getGitHash()}-${variant.buildType.name}.apk"
                    output.outputFileName = outputFileName
                }
            }
    }


}
dependencies {

    val usePublishDependencyInGradle: String by project
    if (usePublishDependencyInGradle == "true") {
        addDependency(listOf(BuildModules.Libraries.AraLibrary))
    } else {
        addSecurity()
        addArchitectureComponents()
        addNavigationComponent()
        addTimber()
        addRetrofit()
        addDependency(
            listOf(
                BuildModules.Libraries.BASE,
                BuildModules.Libraries.AraLibrary,
                BuildModules.Libraries.DOMAIN,
                BuildModules.Libraries.DATA,
                BuildModules.Libraries.COMMON_UI_RESOURCE,
                BuildModules.Libraries.COMMON_UI_VIEW
            )
        )
    }


    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar", "*.aar"))))

    addConstrainLayout()
    addAndroidMaterial()
    addAppCompat()
    addCompose()
}
