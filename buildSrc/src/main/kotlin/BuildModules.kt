class BuildModules {
    enum class Libraries(val libraryValue: String, val implementationValue: String) {
        BASE(":base", "${BuildAndroidConfig.LIBRARY_PACKAGE_NAME}:base"),
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


