package ir.part.sdk.ara.base.util

enum class TaskStatus(val value: String) {
    DOING("doing"),
    DONE("done"),
    UNDONE("undone"),
    NO_STATUS("no status");

    companion object {
        fun enumValueOf(name: String?): TaskStatus {
            enumValues<TaskStatus>().forEach {
                if (it.value == name)
                    return it
            }
            return NO_STATUS
        }
    }
}