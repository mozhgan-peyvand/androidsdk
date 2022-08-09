plugins {
    id(BuildPlugins.MODULE_PLUGIN)
}
dependencies {
    addDependency(
        listOf(
            BuildModules.Libraries.COMMON_UI_VIEW
        )
    )
    addLottie()
    addConstrainLayout()
}