package ir.part.sdk.ara.common.ui.view.viewModel

import ir.part.sdk.ara.common.ui.view.api.UiMessage

data class PublicState(var refreshing: Boolean = false, var message: UiMessage? = null) {
    companion object {
        val Empty = PublicState()
    }
}