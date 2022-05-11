package ir.part.sdk.ara.builder.entities

//import ir.part.sdk.ara.domain.domain.barjavand.entities.SendRequest


data class SendRequestView(
    val serverInfo: ServerInfoView,
    val schemaInfo: SchemaInfoView,
    val tags: Map<String, List<String>>,
    val id: String,
    val data: Map<String, Any?>? = null
) {

//    fun toSendRequest() = SendRequest(
//        serverInfo = serverInfo.toServerInfo(),
//        schemaInfo = schemaInfo.toSchemaInfo(),
//        tags = tags,
//        id = id,
//        data = data
//    )
}

