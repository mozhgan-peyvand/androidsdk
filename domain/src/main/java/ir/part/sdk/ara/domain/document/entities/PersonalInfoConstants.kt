package ir.part.sdk.ara.domain.document.entities

data class PersonalInfoConstants(
    val businessLicenseStatus: List<Constant>,
    val contractType: List<Constant>,
    val educationLevel: List<Constant>,
    val gender: List<Constant>,
    val jobType: List<Constant>,
    val maritalStatus: List<Constant>,
    val ownershipStatus: List<Constant>,
    val provincesAndCities: List<ProvincesAndCity>,
    val residenceStatus: List<Constant>,
    val documentStatusNameEntity: Map<String, StatusParam?>?
)

data class Constant(
    val id: String,
    val name: String
)


data class ProvincesAndCity(
    val cities: List<Constant>?,
    val provinceId: String,
    val provinceName: String,
    val paymentAmount: Long? = null
)

data class StatusParam(
    val name: String? = null,
    val title: String? = null
)
