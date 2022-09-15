package ir.part.sdk.ara.base.util

enum class TasksName(val value: String) {
    CHANG_PASS("change-pass"),
    COMPLETE_INFO("complete-info"),
    START_NEW_DOCUMENT("start-new-document"),
    NO_TASK("no-task");

    companion object {
        fun enumValueOf(name: String?): TasksName {
            enumValues<TasksName>().forEach {
                if (it.value == name)
                    return it
            }
            return NO_TASK
        }
    }
}