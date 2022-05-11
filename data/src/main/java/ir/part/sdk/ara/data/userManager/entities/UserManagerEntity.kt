package ir.part.sdk.ara.data.userManager.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import ir.part.sdk.ara.domain.user.entities.User
import ir.part.sdk.ara.util.TN
import se.ansman.kotshi.JsonSerializable

@Entity(primaryKeys = ["unc"], tableName = TN.UserEntity)

@JsonSerializable
data class UserManagerEntity(
    @ColumnInfo(name = "unc")
    var nationalCode: String,
    @ColumnInfo(name = "ut")
    var token: String,
    @ColumnInfo(name = "uc")
    var cookie: String
) {
    fun toUser() = User(
        nationalCode = nationalCode,
        token = token,
        cookie = cookie
    )
}