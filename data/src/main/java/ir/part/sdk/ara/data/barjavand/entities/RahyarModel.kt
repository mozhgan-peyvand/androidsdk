package ir.part.sdk.ara.data.barjavand.entities

import ir.part.sdk.ara.domain.menu.entities.Rahyar
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class RahyarModel(
    val address: String,
    val name: String,
    val province: String,
    val record: Int,
    val telephone: String
) {
    fun toRahyar() = Rahyar(
        address = address, name = name, province = province, record = record, telephone = telephone
    )
}