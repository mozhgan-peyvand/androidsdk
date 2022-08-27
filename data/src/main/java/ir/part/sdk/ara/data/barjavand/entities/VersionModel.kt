package ir.part.sdk.ara.data.barjavand.entities

import ir.part.sdk.ara.domain.version.entities.VersionDetail
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class VersionDetailModel(
    val isForce: String?,
    val name: String?,
    val versionNumber: Int?
) {
    fun toVersionDetail() =
        VersionDetail(name = name, versionNumber = versionNumber, isForce = isForce == "1")
}