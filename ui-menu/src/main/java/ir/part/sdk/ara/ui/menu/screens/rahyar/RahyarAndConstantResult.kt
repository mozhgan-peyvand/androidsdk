package ir.part.sdk.ara.ui.menu.screens.rahyar

class RahyarAndConstantResult(
    val constant: PersonalInfoConstantsView? = null,
    val rahyar: List<RahyarView>? = null,
) {
    companion object {
        val Empty = RahyarAndConstantResult()
    }
}