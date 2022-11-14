package ir.part.sdk.ara.ui.menu.screens.rahyar

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.constraintlayout.compose.ConstraintLayout
import ir.part.sdk.ara.common.ui.view.api.PublicState
import ir.part.sdk.ara.common.ui.view.common.CustomTextField
import ir.part.sdk.ara.common.ui.view.divider
import ir.part.sdk.ara.common.ui.view.rememberFlowWithLifecycle
import ir.part.sdk.ara.common.ui.view.theme.*
import ir.part.sdk.ara.common.ui.view.utils.dialog.DimensionResource
import ir.part.sdk.ara.common.ui.view.utils.dialog.DrawableResource
import ir.part.sdk.ara.common.ui.view.utils.dialog.getErrorDialog
import ir.part.sdk.ara.common.ui.view.utils.dialog.getLoadingDialog
import ir.part.sdk.merat.ui.menu.R
import kotlinx.coroutines.launch

@Composable
fun RahyarScreen(rahyarViewModel: RahyarViewModel, backButtonAction: () -> Unit) {
    var constantItems: PersonalInfoConstantsView? by remember {
        mutableStateOf(null)
    }
    constantItems =
        rahyarViewModel.getRahyarAndConstantState.collectAsState().value.constant

    var rahyarItems: List<RahyarView>? by remember {
        mutableStateOf(null)
    }
    rahyarItems =
        rahyarViewModel.getRahyarAndConstantState.collectAsState().value.rahyar

    val loadingErrorState =
        rememberFlowWithLifecycle(flow = rahyarViewModel.loadingAndMessageState).collectAsState(
            initial = PublicState.Empty
        )
    ProcessLoadingAndErrorState(input = loadingErrorState.value)

    RahyarScreenElement(
        constantItems,
        rahyarItems,
        backButtonAction = backButtonAction
    ) { province ->
        rahyarViewModel.getRahyar(province)
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun RahyarScreenElement(
    constant: PersonalInfoConstantsView?,
    rahyar: List<RahyarView>?,
    backButtonAction: () -> Unit,
    newRahyarRequest: (String?) -> Unit
) {
    val context = LocalContext.current

    var rahyarList: List<RahyarView> by remember {
        mutableStateOf(listOf())
    }
    rahyarList = rahyar.orEmpty()

    var filteredRahyarList: List<RahyarView> by remember {
        mutableStateOf(listOf())
    }

    var listHasFilter by remember {
        mutableStateOf(false)
    }
    val coroutineScope = rememberCoroutineScope()
    var selectedProvince: ProvincesAndCityView? by remember { mutableStateOf(null) }

    val bottomSheetState =
        rememberModalBottomSheetState(ModalBottomSheetValue.Hidden, skipHalfExpanded = true)

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetContent = {
            val province = constant?.provincesAndCities?.toMutableList()
                ?.apply {
                    add(
                        0, ProvincesAndCityView(
                            listOf(), "-1", "", itemLabel = stringResource(
                                id = R.string.ara_label_choose_all_city
                            )
                        )
                    )
                }
            ProvinceSelection(
                province = province as List<ProvincesAndCityView>?,
                onProvinceSelectionCloseIconClicked = {
                    coroutineScope.launch {
                        bottomSheetState.hide()
                    }
                },
                onSaveButtonClicked = {
                    selectedProvince = it
                    newRahyarRequest(selectedProvince?.provinceName)
                    coroutineScope.launch {
                        bottomSheetState.hide()
                    }
                }
            )
        }
    ) {
        Scaffold(topBar = {
            RahyarToolbar({ searchText ->
                if (searchText.isNotEmpty()) {
                    listHasFilter = true
                    filteredRahyarList = rahyarList.filter {
                        it.address.contains(searchText)
                    }
                } else {
                    listHasFilter = false
                }
            }, onCloseClick = {
                listHasFilter = false
            }, backButtonAction = backButtonAction)
        }) { padding ->
            Column(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = stringResource(id = R.string.ara_label_choose_city),
                    style = MaterialTheme.typography.captionTextPrimary(),
                    modifier = Modifier.padding(
                        end = dimensionResource(id = R.dimen.spacing_4x),
                        start = dimensionResource(id = R.dimen.spacing_4x), top = dimensionResource(
                            id = R.dimen.spacing_2x
                        )
                    )
                )
                CustomTextField(
                    value = selectedProvince?.itemLabel
                        ?: stringResource(R.string.ara_label_choose_your_city),
                    textStyle = MaterialTheme.typography.subtitle2TextSecondary(),
                    onValueChange = { },
                    enabled = false,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            coroutineScope.launch {
                                if (!bottomSheetState.isVisible) {
                                    bottomSheetState.animateTo(ModalBottomSheetValue.Expanded)
                                } else bottomSheetState.hide()
                            }
                        }
                        .padding(
                            end = dimensionResource(id = R.dimen.spacing_4x),
                            start = dimensionResource(id = R.dimen.spacing_4x)
                        ),
                    colors = TextFieldDefaults.textFieldColors(
                        disabledTextColor = Color.Red,
                        backgroundColor = MaterialTheme.colors.background,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = MaterialTheme.colors.divider()
                    )
                )
                Text(
                    text = stringResource(id = R.string.ara_label_address),
                    style = MaterialTheme.typography.h6BoldTextPrimary(),
                    modifier = Modifier.padding(
                        end = dimensionResource(id = R.dimen.spacing_4x),
                        start = dimensionResource(id = R.dimen.spacing_4x),
                        top = dimensionResource(id = R.dimen.spacing_2x)
                    )
                )
                LazyColumn(
                    modifier = Modifier.padding(
                        end = dimensionResource(id = R.dimen.spacing_4x),
                        start = dimensionResource(id = R.dimen.spacing_4x)
                    )
                ) {
                    items(
                        items = if (!listHasFilter) rahyarList else filteredRahyarList,
                        key = { item ->
                            item.name
                        },
                        itemContent = { items ->
                            RahyarListElement(items) {
                                val intent = Intent(Intent.ACTION_DIAL)
                                intent.data = Uri.parse("tel:$it")
                                context.startActivity(intent)
                            }
                        })
                }
            }
        }
    }
}

@Composable
private fun RahyarListElement(rahyarItems: RahyarView, doingIntent: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = rahyarItems.name, modifier =
            Modifier.padding(
                start = dimensionResource(id = R.dimen.spacing_2x),
                top = dimensionResource(id = R.dimen.spacing_2x),
                bottom = dimensionResource(id = R.dimen.spacing_2x)
            ),
            style = MaterialTheme.typography.subtitle2TextPrimary()
        )
        Text(
            text = rahyarItems.province,
            modifier =
            Modifier.padding(
                start = dimensionResource(id = R.dimen.spacing_2x),
                top = dimensionResource(id = R.dimen.spacing_2x),
                bottom = dimensionResource(id = R.dimen.spacing_2x)
            ),
            style = MaterialTheme.typography.subtitle2TextPrimary()
        )
        Text(
            text = rahyarItems.address,
            modifier =
            Modifier.padding(
                start = dimensionResource(id = R.dimen.spacing_2x),
                top = dimensionResource(id = R.dimen.spacing_2x),
                bottom = dimensionResource(id = R.dimen.spacing_2x)
            ),
            style = MaterialTheme.typography.captionTextSecondary()
        )
        Column() {
            rahyarItems.telephone.split("\n").forEach {
                Row(verticalAlignment = CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.ara_ic_telephone),
                        contentDescription = "ic_telephone",
                        modifier =
                        Modifier.padding(
                            start = dimensionResource(id = R.dimen.spacing_2x),
                            top = dimensionResource(id = R.dimen.spacing_2x),
                            bottom = dimensionResource(id = R.dimen.spacing_2x)
                        )
                    )
                    Text(
                        text = it, modifier =
                        Modifier
                            .padding(
                                start = dimensionResource(id = R.dimen.spacing_2x),
                                top = dimensionResource(id = R.dimen.spacing_2x),
                                bottom = dimensionResource(id = R.dimen.spacing_2x)
                            )
                            .clickable {
                                doingIntent(it)
                            }, style = MaterialTheme.typography.subtitle2PrimaryVariant(),
                        textDecoration = TextDecoration.Underline
                    )
                }
            }
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(
                    dimensionResource(
                        id = R.dimen.divider_height
                    )
                )
                .background(
                    ColorGrayLighter
                )
                .padding(top = dimensionResource(id = R.dimen.spacing_2x))
        )
    }
}

@Composable
private fun RahyarToolbar(
    onInputChange: (String) -> Unit,
    onCloseClick: () -> Unit,
    backButtonAction: () -> Unit
) {

    val searchEnable = remember {
        mutableStateOf(false)
    }
    var inputTextValue by remember { mutableStateOf("") }

    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = ColorWhite
    ) {
        if (!searchEnable.value) {
            ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
                val (
                    backButton, labelText, icSearch) = createRefs()
                Image(
                    painter = painterResource(id = R.drawable.ara_ic_back),
                    contentDescription = "ic_back", modifier = Modifier
                        .padding(
                            start = dimensionResource(
                                id = R.dimen.spacing_4x
                            )
                        )
                        .clickable {
                            backButtonAction()
                        }
                        .constrainAs(backButton) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }
                )
                Text(
                    text = stringResource(id = R.string.ara_label_rahyar_menu),
                    modifier = Modifier
                        .constrainAs(labelText) {
                            end.linkTo(icSearch.start)
                            top.linkTo(parent.top)
                            start.linkTo(backButton.end)
                            bottom.linkTo(parent.bottom)
                        },
                    style = MaterialTheme.typography.h6BoldTextPrimary()
                )
                Image(
                    painter = painterResource(id = R.drawable.ara_ic_zoom_out),
                    contentDescription = "ic_search",
                    modifier = Modifier
                        .padding(end = dimensionResource(id = R.dimen.spacing_4x))
                        .constrainAs(icSearch) {
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }
                        .clickable {
                            searchEnable.value = true
                        }
                )
            }
        } else if (searchEnable.value) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                CenterVertically
            ) {
                TextField(
                    value = inputTextValue,
                    onValueChange = {
                        inputTextValue = it
                        onInputChange(inputTextValue)
                    },
                    modifier = Modifier.weight(5f),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        backgroundColor = ColorWhite,
                        unfocusedIndicatorColor = Color.Transparent,
                    ), placeholder = {
                        Text(
                            text = stringResource(id = R.string.ara_label_search_word),
                            style = MaterialTheme.typography.subtitle2TextSecondary()
                        )
                    },
                    maxLines = 1
                )
                Image(
                    painter = painterResource(id = R.drawable.ara_ic_close),
                    contentDescription = "",
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            onCloseClick()
                            searchEnable.value = false
                            inputTextValue = ""
                        }
                )
            }
        }
    }
}

@Composable
private fun BottomSheetTopBar(
    onCloseIconClick: () -> Unit
) {
    Card(elevation = dimensionResource(id = R.dimen.card_elevation_normal)) {
        Image(
            painterResource(DrawableResource.ara_ic_remove),
            contentDescription = "",
            modifier = Modifier
                .padding(dimensionResource(id = DimensionResource.spacing_4x))
                .width(dimensionResource(id = DimensionResource.spacing_8x))
                .wrapContentHeight()
                .clickable {
                    onCloseIconClick()
                },
            colorFilter = ColorFilter.tint(
                Color.Black,
                BlendMode.Dst
            )
        )
        Divider(
            color = MaterialTheme.colors.divider(),
            thickness = dimensionResource(id = DimensionResource.divider_height),
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
private fun ProvinceSelection(
    onProvinceSelectionCloseIconClicked: () -> Unit,
    province: List<ProvincesAndCityView>? = listOf(),
    onSaveButtonClicked: (ProvincesAndCityView?) -> Unit
) {

    var selectedProvince by remember {
        mutableStateOf(province?.firstOrNull())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        horizontalAlignment = Alignment.Start
    ) {
        BottomSheetTopBar {
            onProvinceSelectionCloseIconClicked()
        }
        Text(
            modifier = Modifier.padding(
                end = dimensionResource(id = DimensionResource.spacing_4x),
                top = dimensionResource(id = DimensionResource.spacing_8x),
                start = dimensionResource(id = DimensionResource.spacing_4x)
            ),
            text = stringResource(id = R.string.ara_label_choose_city),
            style = MaterialTheme.typography.h6Bold(),
            textAlign = TextAlign.End
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(
                    end = dimensionResource(id = DimensionResource.spacing_4x),
                    top = dimensionResource(id = DimensionResource.spacing_2x),
                    start = dimensionResource(id = DimensionResource.spacing_4x)
                )
        ) {
            itemsIndexed(
                province ?: listOf(),
                key = { _, item ->
                    item.provinceId
                }
            ) { _, item ->
                Column(modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        selectedProvince = item
                    }) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(alignment = Alignment.Start),
                        verticalAlignment = CenterVertically
                    ) {
                        RadioButton(
                            selected = (item == selectedProvince),
                            onClick = {
                                selectedProvince = item
                            },
                            colors = RadioButtonDefaults.colors(ColorBlueDarker, ColorBlueDarker)
                        )
                        Text(
                            modifier = Modifier.weight(1f),
                            text = item.itemLabel,
                            style = MaterialTheme.typography.subtitle1TextPrimary(),
                            textAlign = TextAlign.Start
                        )
                    }
                    Divider(
                        color = MaterialTheme.colors.divider(),
                        thickness = dimensionResource(id = DimensionResource.spacing_quarter_base),
                        modifier = Modifier
                            .padding(
                                top = dimensionResource(id = DimensionResource.spacing_2x),
                            )
                            .fillMaxWidth()
                    )
                }
            }
        }

        OutlinedButton(
            modifier = Modifier
                .padding(
                    start = dimensionResource(id = DimensionResource.spacing_4x),
                    top = dimensionResource(id = DimensionResource.spacing_2x),
                    bottom = dimensionResource(id = DimensionResource.spacing_2x),
                    end = dimensionResource(id = DimensionResource.spacing_4x)
                )
                .align(Alignment.End),
            onClick = {
                onSaveButtonClicked(selectedProvince)
            },
            border = BorderStroke(
                width = dimensionResource(id = DimensionResource.spacing_half_base),
                color = MaterialTheme.colors.primaryVariant
            ),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.primaryVariant
            ),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(
                style = MaterialTheme.typography.subtitle2.copy(
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.onPrimary
                ),
                text = stringResource(id = R.string.ara_label_save),
            )
        }
    }
}

@Composable
private fun ProcessLoadingAndErrorState(input: PublicState?) {
    val loadingDialog = getLoadingDialog()
    val errorDialog = getErrorDialog(
        title = stringResource(id = R.string.ara_label_warning_title_dialog),
        description = "",
        submitAction = {}
    )
    if (input?.refreshing == true) {
        loadingDialog.show()
    } else {
        loadingDialog.dismiss()
        input?.message?.let { messageModel ->
            errorDialog.setDialogDetailMessage(messageModel.message).show()
        }
    }
}