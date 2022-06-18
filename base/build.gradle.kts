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
}