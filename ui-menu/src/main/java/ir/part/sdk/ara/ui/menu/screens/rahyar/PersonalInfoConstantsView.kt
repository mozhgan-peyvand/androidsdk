package ir.part.sdk.ara.ui.menu.screens.rahyar

class PersonalInfoConstantsView(
    val provincesAndCities: List<ProvincesAndCityView>,
)

data class ConstantView(
    val id: String,
    val name: String
)

data class ProvincesAndCityView(
    val cities: List<ConstantView>?,
    val provinceId: String,
    var provinceName: String,
    var itemLabel: String = provinceName
)

