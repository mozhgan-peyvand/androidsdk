package ir.part.sdk.ara.builder.ui.bottomnavigation

import ir.part.sdk.ara.builder.R

sealed class BottomNavigationItems(
    val route: String,
    val title: Int,
    val icon: Int
) {
    object Document : BottomNavigationItems(
        route = "ui-document://documentFileListScreen",
        title = R.string.label_menu_files,
        icon = R.drawable.merat_ic_icon_files
    )

    object PersonalInfo : BottomNavigationItems(
        route = "namabar-sdk://personalInformation",
        title = R.string.label_menu_personal_info,
        icon = R.drawable.merat_ic_single
    )

    object SubmitRequest : BottomNavigationItems(
        route = "ui-document://documentSubmitScreen",
        title = R.string.label_menu_validation_request,
        icon = R.drawable.merat_ic_circle_filled
    )

    object Menu : BottomNavigationItems(
        route = "ui-menu://mainMenuScreen",
        title = R.string.label_menu_others,
        icon = R.drawable.merat_ic_favorite_filled
    )
}
