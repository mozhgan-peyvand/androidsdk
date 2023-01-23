package ir.part.sdk.ara.base.util

enum class DocumentTasksName(val value: String) {

    DELETE_DOCUMENT("deleteDocument");


    companion object {
        fun enumValueOf(name: String?): TasksName {
            enumValues<TasksName>().forEach {
                if (it.value == name)
                    return it
            }
            return TasksName.NO_TASK
        }
    }
}