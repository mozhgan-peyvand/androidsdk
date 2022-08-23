// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        mavenLocal()
        mavenCentral()
        google()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.31")
    }

}

allprojects {
    group = BuildAndroidConfig.LIBRARY_PACKAGE_NAME
    version = generateVersionName()
    repositories {
        maven {
            url = uri("https://nexus.partdp.ir/repository/android-psg/")
            artifactUrls("https://nexus.partdp.ir/repository/android-psg/")
            credentials {
                username = "android-psg"
                password = "P}s%AYdg,4R{U8bY"
            }
        }
        mavenLocal()
        mavenCentral()
        google()
//        maven("https://mvnrepository.com/artifact/com.android.tools.lint/lint-gradle-api")
        maven("https://jitpack.io")
        maven("https://s3.amazonaws.com/repo.commonsware.com")
        maven("https://oss.sonatype.org/content/repositories/snapshots/")
    }
}
// register clean task
tasks.register("clean").configure {
    delete("build")
}

tasks.create("publishGroupStep1")
{
    dependsOn(
        "base:publishAAR", "common-ui-resource:publishAAR"
    )
}
tasks.create("publishGroupStep2")
{
    dependsOn(
        "data:publishAAR", "common-ui-view:publishAAR"
    )
}
tasks.create("publishGroupStep3")
{
    dependsOn(
        "domain:publishAAR"
    )
}
tasks.create("publishGroupStep4")
{
    dependsOn(
        "ui-shared-feature:publishAAR"
    )
}

tasks.create("publishGroupStep5")
{
    dependsOn("builder:publishAAR")
}
//you can run this task after run all group step one time
tasks.create("publishAllModulesStepByStep")
{
    dependsOn("publishGroupStep5")
}

tasks.findByPath("publishGroupStep2")?.dependsOn("publishGroupStep1")
tasks.findByPath("publishGroupStep3")?.dependsOn("publishGroupStep2")
tasks.findByPath("publishGroupStep4")?.dependsOn("publishGroupStep3")
tasks.findByPath("publishGroupStep5")?.dependsOn("publishGroupStep4")
