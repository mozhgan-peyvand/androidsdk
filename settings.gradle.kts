include(":common-ui-view")
include(":common-ui-resource")
include(":base")
include(":data")
include(":domain")
include(":builder")
include(":sample")
include(":ui-document")
include(":ui-user")
include(":ui-menu")

pluginManagement {
    plugins {
        id("org.jetbrains.kotlin.android") version ("1.5.21")
        id("org.jetbrains.kotlin.jvm") version "1.5.30"
    }
}
