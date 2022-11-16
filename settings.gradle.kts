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
include(":ui-shared-feature")
include(":domain-provider")
pluginManagement {
    plugins {
        id("org.jetbrains.kotlin.android") version ("1.6.10")
        id("org.jetbrains.kotlin.jvm") version "1.5.31"
    }
}
include(":ui-home")
