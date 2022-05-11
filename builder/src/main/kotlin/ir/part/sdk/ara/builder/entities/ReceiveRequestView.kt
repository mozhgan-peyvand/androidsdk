package ir.part.sdk.ara.builder.entities


data class ReceiveRequestView(
    val serverInfo: ServerInfoView,
    val schemaName: String,
    val schemaVersion: String,
    val dataId: String? = null,
    val tags: String? = null,
    val spec: String? = null
) {
//    fun toReceiveRequest() = ReceiveRequest(
//        serverInfo = serverInfo.toServerInfo(),
//        schemaName = schemaName,
//        schemaVersion = schemaVersion,
//        dataId = dataId,
//        tags = tags,
//        spec = spec
//    )
}