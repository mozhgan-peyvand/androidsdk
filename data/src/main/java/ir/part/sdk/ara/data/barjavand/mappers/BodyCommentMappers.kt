package ir.part.sdk.ara.data.barjavand.mappers

import ir.part.sdk.ara.data.barjavand.entities.BodyCommentEntity
import ir.part.sdk.ara.data.barjavand.entities.DataCommentEntity
import ir.part.sdk.ara.data.barjavand.entities.SchemaEntity
import ir.part.sdk.ara.data.barjavand.entities.TagsEntity
import ir.part.sdk.ara.domain.menu.entities.BodyComment
import ir.part.sdk.ara.domain.menu.entities.DataComment


fun BodyComment.toBodyCommentEntity() =
    BodyCommentEntity(schema = SchemaEntity(name = "comment", version = "1.0.0"),
        tags = TagsEntity(userType = "visitor", mobile = data.mobile),
        data = this.data.toDataCommentEntity())

fun DataComment.toDataCommentEntity() = DataCommentEntity(
    description = description,
    email = email.ifEmpty { null },
    family = family,
    mobile = mobile,
    name = name
)