package ir.part.sdk.ara.ui.document.submitDocument.model

class PersonalInfoConstantsView(
    val businessLicenseStatus: List<ConstantView>,
    val contractType: List<ConstantView>,
    val educationLevel: List<ConstantView>,
    val gender: List<ConstantView>,
    val jobType: List<ConstantView>,
    val maritalStatus: List<ConstantView>,
    val ownershipStatus: List<ConstantView>,
    val provincesAndCities: List<ProvincesAndCityView>,
    val residenceStatus: List<ConstantView>,
    val documentStatusNameEntity: Map<String, StatusParamView?>?
)


data class ConstantView(
    val id: String,
    val name: String
)


data class ProvincesAndCityView(
    val cities: List<ConstantView>?,
    val provinceId: String,
    val provinceName: String,
    val paymentAmount: Long? = null
)

data class StatusParamView(
    val name: String? = null,
    val title: String? = null
)
