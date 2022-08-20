package ir.part.sdk.ara.ui.document.utils.navigation

import ir.part.sdk.ara.common.ui.view.model.AraUiModules


val moduleName = AraUiModules.MODULE_UI_DOCUMENT.value

sealed class DocumentRouter(val router: String) {
    object DocumentGraph : DocumentRouter(router = "$moduleName://documentGraph")
    object DocumentSubmitScreen : DocumentRouter(router = "$moduleName://documentSubmitScreen")
    object DocumentFileListScreen : DocumentRouter(router = "$moduleName://documentFileListScreen")
    object DetailsScreenViewPager : DocumentRouter(router = "$moduleName://detailsScreenViewPager")
    object ResultValidationScreenViewPager :
        DocumentRouter(router = "$moduleName://validationResultScreen")
}