package ir.part.sdk.ara.ui.menu.screens.rahyar

import ir.part.sdk.ara.domain.document.entities.Constant
import ir.part.sdk.ara.domain.document.entities.PersonalInfoConstants
import ir.part.sdk.ara.domain.document.entities.ProvincesAndCity

fun PersonalInfoConstants.toPersonalInfoConstantsView() = PersonalInfoConstantsView(
    provincesAndCities = provincesAndCities.map { it.toProvincesAndCityView() },
)

fun Constant.toConstantView() = ConstantView(
    id = id,
    name = name
)

fun ProvincesAndCity.toProvincesAndCityView() = ProvincesAndCityView(
    cities = cities?.map { it.toConstantView() },
    provinceId = provinceId,
    provinceName = provinceName,
    paymentAmount = paymentAmount
)
