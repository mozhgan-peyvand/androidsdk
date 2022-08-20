package ir.part.sdk.ara.ui.document.submitDocument.mapper

import ir.part.sdk.ara.domain.document.entities.Constant
import ir.part.sdk.ara.domain.document.entities.PersonalInfoConstants
import ir.part.sdk.ara.domain.document.entities.ProvincesAndCity
import ir.part.sdk.ara.domain.document.entities.StatusParam
import ir.part.sdk.ara.ui.document.submitDocument.model.ConstantView
import ir.part.sdk.ara.ui.document.submitDocument.model.PersonalInfoConstantsView
import ir.part.sdk.ara.ui.document.submitDocument.model.ProvincesAndCityView
import ir.part.sdk.ara.ui.document.submitDocument.model.StatusParamView

fun PersonalInfoConstants.toPersonalInfoConstantsView() = PersonalInfoConstantsView(
    businessLicenseStatus = businessLicenseStatus.map { it.toConstantView() },
    contractType = contractType.map { it.toConstantView() },
    educationLevel = educationLevel.map { it.toConstantView() },
    gender = gender.map { it.toConstantView() },
    jobType = jobType.map { it.toConstantView() },
    maritalStatus = maritalStatus.map { it.toConstantView() },
    ownershipStatus = ownershipStatus.map { it.toConstantView() },
    provincesAndCities = provincesAndCities.map { it.toProvincesAndCityView() },
    residenceStatus = residenceStatus.map { it.toConstantView() },
    documentStatusNameEntity = documentStatusNameEntity?.mapValues { it.value?.toStatusParamView() }
)

fun Constant.toConstantView() = ConstantView(
    id = id,
    name = name
)

fun ProvincesAndCity.toProvincesAndCityView() = ProvincesAndCityView(
    cities = cities?.map { it.toConstantView() },
    provinceId = provinceId,
    provinceName = provinceName,
)

fun StatusParam.toStatusParamView() = StatusParamView(
    name = name,
    title = title
)