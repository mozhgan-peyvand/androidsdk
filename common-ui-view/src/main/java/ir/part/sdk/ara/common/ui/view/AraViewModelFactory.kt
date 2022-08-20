package ir.part.sdk.ara.common.ui.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class AraViewModelFactory<T : ViewModel> @Inject constructor(val t: T) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return t as T
    }
}