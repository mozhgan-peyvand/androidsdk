class BuildModules {
    enum class Libraries(val libraryValue: String, val implementationValue: String) {
        BASE(":base", "${BuildAndroidConfig.LIBRARY_PACKAGE_NAME}:base"),
        UI_USER(":ui-user", "${BuildAndroidConfig.LIBRARY_PACKAGE_NAME}:ui-user"),
        UI_MENU(":ui-menu", "${BuildAndroidConfig.LIBRARY_PACKAGE_NAME}:ui-menu"),
        UI_HOME(":ui-home", "${BuildAndroidConfig.LIBRARY_PACKAGE_NAME}:ui-home"),
        AraLibrary(":builder", "${BuildAndroidConfig.LIBRARY_PACKAGE_NAME}:builder"),
        DATA(":data", "${BuildAndroidConfig.LIBRARY_PACKAGE_NAME}:data"),
        DOMAIN(":domain", "${BuildAndroidConfig.LIBRARY_PACKAGE_NAME}:domain"),
        COMMON_UI_RESOURCE(
            ":common-ui-resource",
            "${BuildAndroidConfig.LIBRARY_PACKAGE_NAME}:common-ui-resource"
        ),
        COMMON_UI_VIEW(
            ":common-ui-view",
            "${BuildAndroidConfig.LIBRARY_PACKAGE_NAME}:common-ui-view"
        ),
    }
}


