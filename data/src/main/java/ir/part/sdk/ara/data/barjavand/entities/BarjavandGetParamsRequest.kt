package ir.part.sdk.ara.data.barjavand.entities

data class BarjavandGetParamsRequest(
    val schemaName: String,
    val schemaVersion: String,
    val dataId: String? = null,
    val tags: String? = null,
    val spec: String? = null,
    val id: String? = null
) {
    fun toHashMap(): HashMap<String, String> {
        val options: HashMap<String, String> = HashMap()
        options["schemaName"] = schemaName
        options["schemaVersion"] = schemaVersion
        dataId?.let { options["dataId"] = it }
        tags?.let { options["tags"] = it }
        spec?.let { options["spec"] = it }
        id?.let { options["id"] = it }
        return options
    }
}