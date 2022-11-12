package ir.part.sdk.ara.data.barjavand.entities


import com.squareup.moshi.Json
import ir.part.sdk.ara.domain.document.entities.Constant
import ir.part.sdk.ara.domain.document.entities.PersonalInfoConstants
import ir.part.sdk.ara.domain.document.entities.ProvincesAndCity
import se.ansman.kotshi.JsonSerializable


@JsonSerializable
data class PersonalInfoConstantsModel(
    @field:Json(name = "businessLicenseStatus")
    val businessLicenseStatus: List<ConstantEntity>,
    @field:Json(name = "contractType")
    val contractType: List<ConstantEntity>,
    @field:Json(name = "educationLevel")
    val educationLevel: List<ConstantEntity>,
    @field:Json(name = "gender")
    val gender: List<ConstantEntity>,
    @field:Json(name = "jobType")
    val jobType: List<ConstantEntity>,
    @field:Json(name = "maritalStatus")
    val maritalStatus: List<ConstantEntity>,
    @field:Json(name = "ownershipStatus")
    val ownershipStatus: List<ConstantEntity>,
    @field:Json(name = "provincesAndCities")
    val provincesAndCities: List<ProvincesAndCityEntity>,
    @field:Json(name = "residenceStatus")
    val residenceStatus: List<ConstantEntity>,
    @field:Json(name = "documentStatusName")
    val documentStatusNameEntity: Map<String, StatusModel?>?
) {
    fun toPersonalInfoConstants() = PersonalInfoConstants(
        businessLicenseStatus = businessLicenseStatus.map { it.toConstant() },
        residenceStatus = residenceStatus.map { it.toConstant() },
        contractType = contractType.map { it.toConstant() },
        educationLevel = educationLevel.map { it.toConstant() },
        gender = gender.map { it.toConstant() },
        jobType = jobType.map { it.toConstant() },
        maritalStatus = maritalStatus.map { it.toConstant() },
        ownershipStatus = ownershipStatus.map { it.toConstant() },
        provincesAndCities = provincesAndCities.map { it.toProvincesAndCity() },
        documentStatusNameEntity = documentStatusNameEntity?.mapValues { it.value?.toStatusParam() },
    )
}


@JsonSerializable
data class ConstantEntity(
    @field:Json(name = "id")
    val id: String,
    @field:Json(name = "name")
    val name: String
) {
    fun toConstant() = Constant(
        id = id,
        name = name
    )
}


@JsonSerializable
data class ProvincesAndCityEntity(
    @field:Json(name = "cities")
    val cities: List<ConstantEntity>?,
    @field:Json(name = "provinceId")
    val provinceId: String,
    @field:Json(name = "provinceName")
    val provinceName: String,
    val paymentAmount: Long? = null
) {
    fun toProvincesAndCity() = ProvincesAndCity(
        cities = cities?.map { it.toConstant() },
        provinceId = provinceId,
        provinceName = provinceName,
        paymentAmount = paymentAmount
    )
}
