package ir.part.sdk.ara.common.ui.view.api

data class PublicState(var refreshing: Boolean = false, var message: UiMessage? = null) {
    companion object {
        val Empty = PublicState()
    }
}