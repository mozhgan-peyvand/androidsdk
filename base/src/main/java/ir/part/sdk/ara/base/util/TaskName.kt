package ir.part.sdk.ara.base.util

enum class TasksName(val value: String) {
    ChangePass("change-pass"),
    CompleteInfo("complete-info"),
    StartNewDocument("start-new-document"),
    DeleteAccount("delete-account");

    companion object {
        fun enumValueOf(name: String): TasksName? {
            enumValues<TasksName>().forEach {
                if (it.value == name)
                    return it
            }
            return null
        }

    }
}