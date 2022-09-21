import org.gradle.api.Project
import org.gradle.kotlin.dsl.add
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.provideDelegate


fun Project.addJsonLogic() {
    dependencies {
        add("implementation", "io.github.jamsesso:json-logic-java:1.0.7")
    }
}

fun Project.addCompose() {
    dependencies {

        val composeVersion = "1.2.1"
        add("implementation", "androidx.compose.ui:ui:$composeVersion")
        add("implementation", "androidx.compose.material:material:$composeVersion")
        add("implementation", "androidx.compose.material:material-icons-extended:$composeVersion")
        add("implementation", "androidx.compose.ui:ui-tooling-preview:$composeVersion")
        add("debugImplementation", "androidx.compose.ui:ui-tooling:$composeVersion")
        add("implementation", "androidx.activity:activity-compose:1.3.1")
        add("implementation", "androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
        add("implementation", "androidx.navigation:navigation-compose:2.5.0-beta01")
    }
}

fun Project.addDagger2() {
    dependencies {
        val dagger2Version = "2.38.1"
        add("api", "com.google.dagger:dagger:$dagger2Version")
        add("kapt", "com.google.dagger:dagger-compiler:$dagger2Version")
    }
}

fun Project.addSwipeRefreshLayout() {
    dependencies {
        add("implementation", "androidx.swiperefreshlayout:swiperefreshlayout:1.2.0-alpha01")
    }
}

fun Project.addNamabar() {
    dependencies {
        add("implementation", "ir.part.sdk.namabar:builder-dev-debug:0.0.0.7")
    }
}

fun Project.addAndroidMaterial() {
    dependencies {
        add("implementation", "com.google.android.material:material:1.4.0")
    }
}

fun Project.addConstrainLayout() {
    dependencies {
        add("implementation", "androidx.constraintlayout:constraintlayout-compose:1.0.1")
    }
}

fun Project.addCoroutine() {
    dependencies {
        add("implementation", "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.1")
        add("implementation", "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.1")
    }
}

fun Project.addPersianDate() {
    dependencies {
        add("implementation", "com.github.samanzamani:PersianDate:1.5.4")
        add("implementation", "com.github.aliab:Persian-Date-Picker-Dialog:1.8.0")
    }
}

fun Project.addKotshi() {
    dependencies {
        add("implementation", "se.ansman.kotshi:api:2.7.0")
        add("kapt", "se.ansman.kotshi:compiler:2.7.0")
    }
}

fun Project.addSecurity() {
    dependencies {
        add("implementation", "org.bouncycastle:bcpkix-jdk15on:1.61")

    }
}

fun Project.addMoshi() {
    dependencies {
        add("kapt", "com.squareup.moshi:moshi-kotlin-codegen:1.13.0")
        add("implementation", "com.squareup.moshi:moshi:1.13.0")
        add("implementation", "com.squareup.moshi:moshi-adapters:1.13.0")
    }
}

fun Project.addAppCompat() {
    dependencies {
        add("implementation", "androidx.appcompat:appcompat:1.4.0")
    }
}

fun Project.addRecyclerView() {
    dependencies {
        add("implementation", "androidx.recyclerview:recyclerview:1.1.0")
    }
}

fun Project.addArchitectureComponents() {
    dependencies {
        add("implementation", "androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.0")
        add("implementation", "androidx.lifecycle:lifecycle-livedata-ktx:2.4.0")
        add("implementation", "androidx.lifecycle:lifecycle-runtime-ktx:2.4.0")
        add("implementation", "androidx.lifecycle:lifecycle-common-java8:2.4.0")
        add("testImplementation", "androidx.arch.core:core-testing:2.1.0")
    }
}

fun Project.addTimber() {
    dependencies {
        add("implementation", "com.jakewharton.timber:timber:4.7.1")
    }
}

fun Project.addNavigationComponent() {
    dependencies {
        add("implementation", "androidx.navigation:navigation-fragment-ktx:2.3.1")
        add("implementation", "androidx.navigation:navigation-ui-ktx:2.3.1")
        add("implementation", "androidx.navigation:navigation-common-ktx:2.3.1")
        add("implementation", "androidx.navigation:navigation-runtime-ktx:2.3.1")
        add(
            "androidTestImplementation",
            "android.arch.navigation:navigation-testing-ktx:1.0.0-alpha06"
        )

    }
}

fun Project.addPinentryEditText() {
    dependencies {
        add("implementation", "com.alimuzaffar.lib:pinentryedittext:2.0.6")
    }
}


fun Project.addRetrofit() {
    dependencies {
        add("implementation", "com.squareup.retrofit2:retrofit:2.9.0")
        add("implementation", "com.squareup.retrofit2:converter-moshi:2.9.0")
        add("implementation", "com.squareup.okhttp3:logging-interceptor:3.12.2")
        add("implementation", "com.squareup.okhttp3:okhttp:3.12.2") {
            version {
                strictly("3.12.2")
            }
        }
    }
}

fun Project.addGlide() {
    dependencies {
        add("implementation", "com.github.bumptech.glide:glide:4.11.0")
        add("implementation", "com.github.bumptech.glide:annotations:4.11.0")
        add("implementation", "com.github.bumptech.glide:okhttp3-integration:4.11.0")
        add("kapt", "com.github.bumptech.glide:compiler:4.11.0")
    }
}

fun Project.addLottie() {
    dependencies {
        add("implementation", "com.airbnb.android:lottie-compose:5.2.0")
    }
}

fun Project.addSqlLite() {
    dependencies {
        add("implementation", "androidx.sqlite:sqlite-ktx:2.1.0")
    }
}

fun Project.addRoom() {
    dependencies {
        add("implementation", "androidx.room:room-runtime:2.2.5")
        add("implementation", "androidx.room:room-ktx:2.2.5")
        add("kapt", "androidx.room:room-compiler:2.2.5")
        add("implementation", "com.commonsware.cwac:saferoom:1.2.1")
        add("testImplementation", "androidx.room:room-testing:2.2.5")
    }
}

fun Project.addSwipeRefresh() {
    dependencies {
        add("implementation", "com.google.accompanist:accompanist-swiperefresh:0.20.3")
    }
}

fun Project.addPager() {
    dependencies {
        add("implementation", "com.google.accompanist:accompanist-pager:0.23.1")
        add("implementation", "com.google.accompanist:accompanist-pager-indicators:0.23.1")
    }
}


fun Project.addDependency(
    libraryNames: List<BuildModules.Libraries>
) {
    val usePublishDependencyInGradle: String by project
    dependencies {
        libraryNames.forEach { libraryName ->
            if (usePublishDependencyInGradle == "true") {

                add(
                    "devImplementation",
                    libraryName.implementationValue + "-dev-release:" + generateVersionName()
                )

                add(
                    "prodtestImplementation",
                    libraryName.implementationValue + "-prodtest-release:" + generateVersionName()
                )

                add(
                    "prodImplementation",
                    libraryName.implementationValue + "-prod-release:" + generateVersionName()
                )
            } else
                add(
                    "implementation",
                    project(mapOf("path" to libraryName.libraryValue))
                )
        }
    }
}

