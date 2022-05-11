package ir.part.sdk.ara.builder.entities


data class ServerInfoView(
    val url: String,
    val headerUserName: String,
    val headerPassword: String,
) {
//    fun toServerInfo() = ServerInfo(
//        url = url,
//        headerUserName = headerUserName,
//        headerPassword = headerPassword
//    )
}