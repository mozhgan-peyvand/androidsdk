package ir.part.sdk.ara.ui.document.submitDocument

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import ir.part.sdk.ara.base.util.TasksName
import ir.part.sdk.ara.common.ui.view.*
import ir.part.sdk.ara.common.ui.view.api.PublicState
import ir.part.sdk.ara.common.ui.view.common.ProcessLoadingAndErrorState
import ir.part.sdk.ara.common.ui.view.theme.*
import ir.part.sdk.ara.common.ui.view.utils.dialog.DimensionResource
import ir.part.sdk.ara.common.ui.view.utils.dialog.DrawableResource
import ir.part.sdk.ara.common.ui.view.utils.dialog.getSuccessDialog
import ir.part.sdk.ara.ui.document.R
import ir.part.sdk.ara.ui.document.submitDocument.model.PersonalInfoClubView
import ir.part.sdk.ara.ui.document.submitDocument.model.PersonalInfoSubmitDocumentView
import ir.part.sdk.ara.ui.document.submitDocument.model.SubmitDocumentView
import ir.part.sdk.ara.ui.document.submitDocument.model.SubmitResponseValidationView
import ir.part.sdk.ara.ui.shared.feature.screens.task.TasksManagerViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SubmitDocumentScreen(
    viewModel: SubmitDocumentViewModel,
    navigateToFileList: () -> Unit,
    tasksManagerViewModel: TasksManagerViewModel
) {

    var submitDocumentResponseState: SubmitResponseValidationView? by remember {
        mutableStateOf(null)
    }
    var selectedUnion: PersonalInfoClubView? by remember { mutableStateOf(null) }
    var isUserAgreementCheckBoxChecked by remember { mutableStateOf(false) }
    var userAgreementDrawBorder by remember { mutableStateOf(false) }
    var unionSelectionDrawBorder by remember { mutableStateOf(false) }

    val bottomSheetState =
        rememberModalBottomSheetState(ModalBottomSheetValue.Hidden, skipHalfExpanded = true)
    val scrollState = rememberScrollState()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    val dataState =
        rememberFlowWithLifecycle(viewModel.state).collectAsState(initial = SubmitDocumentView.Empty)

    val taskLoadingErrorState =
        rememberFlowWithLifecycle(flow = tasksManagerViewModel.loadingAndMessageState).collectAsState(
            initial = PublicState.Empty
        )

    val submitDocumentLoadingErrorState =
        rememberFlowWithLifecycle(flow = viewModel.loadingAndMessageState).collectAsState(
            initial = PublicState.Empty
        )

    ProcessLoadingAndErrorState(
        taskLoadingErrorState.value,
        submitDocumentLoadingErrorState.value
    )

    SubmitDocumentRequestSuccessHandler(response = submitDocumentResponseState) {
        if (tasksManagerViewModel.getDoingTaskName == TasksName.START_NEW_DOCUMENT) {
            tasksManagerViewModel.done()
        } else {
            navigateToFileList.invoke()
        }
        submitDocumentResponseState = null
    }

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetContent = {
            val unions = dataState.value.personalInfoClubView
            UnionSelection(
                userUnions = unions,
                onUnionSelectionCloseIconClicked = {
                    scope.launch {
                        bottomSheetState.hide()
                    }
                },
                onSaveButtonClicked = {
                    selectedUnion = it
                    scope.launch {
                        bottomSheetState.hide()
                    }
                }
            )
        }
    ) {
        Scaffold(
            scaffoldState = scaffoldState,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(
                    color = MaterialTheme.colors.background
                ),
            topBar = {
                ScreenTopBar()
            },
            bottomBar = {
                ScreenBottomBar(
                    onSubmitButtonClicked = {
                        // handle validation
                        if (selectedUnion == null) {
                            scope.launch { scrollState.animateScrollTo(0) }
                            unionSelectionDrawBorder = true
                        } else if (!isUserAgreementCheckBoxChecked) {
                            scope.launch { scrollState.animateScrollTo(scrollState.maxValue) }
                            userAgreementDrawBorder = true
                        } else {
                            // request if validated
                            selectedUnion?.id?.let { id ->
                                viewModel.submitReqValidation(unionId = id) {
                                    if (it?.documentSerialNumber != null) {
                                        submitDocumentResponseState = it
                                    }
                                }
                            }
                        }
                    })
            },
            content = { paddingValues ->
                Box(modifier = Modifier.padding(paddingValues)) {
                    Column(
                        modifier = Modifier
                            .verticalScroll(scrollState)
                            .padding(
                                top = dimensionResource(id = DimensionResource.spacing_9x),
                                bottom = dimensionResource(id = DimensionResource.spacing_4x)
                            )
                    ) {
                        ScreenContent(
                            onUserAgreementCheckBoxCheckChange = { isChecked ->
                                userAgreementDrawBorder = false
                                isUserAgreementCheckBoxChecked = isChecked
                            },
                            onUnionSelectionTextClicked = {
                                unionSelectionDrawBorder = false
                                scope.launch {
                                    if (!bottomSheetState.isVisible) {
                                        bottomSheetState.animateTo(ModalBottomSheetValue.Expanded)
                                    } else bottomSheetState.hide()
                                }
                            },
                            data = dataState.value.personalInfoSubmitDocumentView,
                            selectedUnion = selectedUnion,
                            userAgreementDrawBorder = userAgreementDrawBorder,
                            isUserAgreementCheckBoxChecked = isUserAgreementCheckBoxChecked,
                            drawBorder = unionSelectionDrawBorder,
                            modifier = Modifier
                                .padding(
                                    start = dimensionResource(id = DimensionResource.spacing_4x),
                                    end = dimensionResource(id = DimensionResource.spacing_4x)
                                )
                        )
                    }
                }
            })
    }
}

@Composable
private fun ScreenContent(
    modifier: Modifier = Modifier,
    onUserAgreementCheckBoxCheckChange: (Boolean) -> Unit,
    onUnionSelectionTextClicked: () -> Unit,
    userAgreementDrawBorder: Boolean,
    isUserAgreementCheckBoxChecked: Boolean,
    data: PersonalInfoSubmitDocumentView?,
    selectedUnion: PersonalInfoClubView?,
    drawBorder: Boolean
) {

    val borderWidth = if (drawBorder) 1 else 0

    Text(
        text = stringResource(
            id = R.string.ara_label_insert_request_validation
        ),
        style = MaterialTheme.typography.h6Bold(),
        modifier = modifier
    )

    Text(
        text = stringResource(
            id = R.string.ara_msg_insert_request_validation
        ),
        style = MaterialTheme.typography.subtitle2TextSecondary(),
        modifier = modifier
    )

    Text(
        modifier = modifier.padding(top = dimensionResource(id = DimensionResource.spacing_8x)),
        text = stringResource(id = R.string.ara_label_union),
        style = MaterialTheme.typography.captionBoldTextPrimary()
    )

    TextField(
        value = selectedUnion?.clubName
            ?: selectedUnion?.id
            ?: stringResource(R.string.ara_label_choose_union),
        textStyle = MaterialTheme.typography.subtitle2TextSecondary(),
        onValueChange = { },
        enabled = false,
        modifier = modifier
            .clickable {
                onUnionSelectionTextClicked()
            }
            .fillMaxWidth()
            .border(
                width = borderWidth.dp,
                color = if (borderWidth == 0) {
                    Color.Transparent
                } else {
                    Color.Red
                }
            ),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.background,
            unfocusedIndicatorColor = MaterialTheme.colors.disabled()

        )
    )

    Spacer(modifier = modifier.padding(top = dimensionResource(id = DimensionResource.spacing_5x)))

    UserProperties(
        name = data?.name,
        lastName = data?.lastname,
        nationalCode = data?.nationalCode,
        unionId = selectedUnion?.id ?: " "
    )

    UserAgreementCheckbox(
        name = data?.name,
        lastName = data?.lastname,
        onCheckChange = {
            onUserAgreementCheckBoxCheckChange(it)
        },
        drawBorder = userAgreementDrawBorder,
        isChecked = isUserAgreementCheckBoxChecked
    )

}

@Composable
private fun ScreenBottomBar(onSubmitButtonClicked: () -> Unit) {
    ButtonBlue(
        onClick = {
            onSubmitButtonClicked()
        }, text = stringResource(
            id = R.string.ara_label_request_validation
        )
    )
}

@Composable
private fun ScreenTopBar() {
    Spacer(
        modifier = Modifier
            .background(Color.Transparent)
            .height(dimensionResource(id = DimensionResource.divider_height))
            .fillMaxWidth()
    )
}

@Composable
private fun UnionSelection(
    onUnionSelectionCloseIconClicked: () -> Unit,
    userUnions: List<PersonalInfoClubView>? = listOf(),
    onSaveButtonClicked: (PersonalInfoClubView?) -> Unit
) {

    var selectedUnion by remember {
        mutableStateOf(userUnions?.firstOrNull())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        horizontalAlignment = Alignment.Start
    ) {

        BottomSheetTopBar {
            onUnionSelectionCloseIconClicked()
        }

        Text(
            modifier = Modifier.padding(
                end = dimensionResource(id = DimensionResource.spacing_4x),
                top = dimensionResource(id = DimensionResource.spacing_8x),
                start = dimensionResource(id = DimensionResource.spacing_4x)
            ),
            text = stringResource(id = R.string.ara_label_select_union),
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
                userUnions ?: listOf(),
                key = { _, item ->
                    item.id
                }
            ) { _, item ->
                Column(modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        selectedUnion = item
                    }) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(alignment = Alignment.Start),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val annotatedString = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(fontWeight = FontWeight.Bold)
                            ) { append("  ${item.clubName ?: item.id}  ") }
                        }
                        RadioButton(
                            selected = (item == selectedUnion),
                            onClick = {
                                selectedUnion = item
                            },
                            colors = RadioButtonDefaults.colors(
                                unselectedColor = MaterialTheme.colors.primary,
                                selectedColor = MaterialTheme.colors.primary
                            )
                        )
                        Text(
                            modifier = Modifier.weight(1f),
                            text = annotatedString,
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
                onSaveButtonClicked(selectedUnion)
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
private fun UserProperties(
    name: String? = null,
    lastName: String? = null,
    nationalCode: String? = null,
    unionId: String? = null
) {
    Box(
        modifier = Modifier
            .background(ColorGrayLighter)
            .padding(
                top = dimensionResource(id = DimensionResource.spacing_4x),
                bottom = dimensionResource(id = DimensionResource.spacing_4x),
                start = dimensionResource(id = DimensionResource.spacing_4x),
                end = dimensionResource(id = DimensionResource.spacing_4x)
            )
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(color = MaterialTheme.colors.textSecondary())) {
                    append(
                        stringResource(
                            id = R.string.ara_msg_request_validation_agreement_part1
                        )
                    )
                }
                withStyle(
                    style = SpanStyle(
                        MaterialTheme.colors.textPrimary(),
                        textDecoration = TextDecoration.Underline
                    )
                ) {
                    append(" $name $lastName ")
                }
                withStyle(style = SpanStyle(color = MaterialTheme.colors.textSecondary())) {
                    append(
                        stringResource(
                            id = R.string.ara_msg_request_validation_agreement_part2
                        )
                    )
                }
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colors.textPrimary(),
                        textDecoration = TextDecoration.Underline
                    )
                ) {
                    append(" $nationalCode ")
                }
                withStyle(style = SpanStyle(color = MaterialTheme.colors.textSecondary())) {
                    append(
                        stringResource(
                            id = R.string.ara_msg_request_validation_agreement_part3
                        )
                    )
                }
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colors.textPrimary(),
                        textDecoration = TextDecoration.Underline
                    )
                ) {
                    append(" $unionId ")
                }
                withStyle(style = SpanStyle(color = MaterialTheme.colors.textSecondary())) {
                    append(
                        stringResource(
                            id = R.string.ara_msg_request_validation_agreement_part4
                        )
                    )
                }
            }
        )
    }
}

@Composable
private fun UserAgreementCheckbox(
    name: String? = null,
    lastName: String? = null,
    onCheckChange: (Boolean) -> Unit,
    drawBorder: Boolean,
    isChecked: Boolean
) {

    val borderWidth = if (drawBorder) 1 else 0

    Row(
        modifier = Modifier
            .padding(
                top = dimensionResource(id = DimensionResource.spacing_8x),
                start = dimensionResource(id = DimensionResource.spacing_base),
                end = dimensionResource(id = DimensionResource.spacing_base)
            )
            .border(
                width = borderWidth.dp,
                color = if (borderWidth == 0) {
                    Color.Transparent
                } else {
                    Color.Red
                }
            )
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = {
                onCheckChange(it)
            },
            enabled = true,
            colors = CheckboxDefaults.colors(
                uncheckedColor = MaterialTheme.colors.primary,
                checkedColor = MaterialTheme.colors.primary
            )
        )

        Text(
            modifier = Modifier
                .padding(
                    top = dimensionResource(id = DimensionResource.spacing_half_base),
                    end = dimensionResource(id = DimensionResource.spacing_3x)
                ),
            text =
            buildAnnotatedString {
                append(
                    stringResource(
                        id = R.string.ara_msg_request_validation_agreement_approve_part1
                    )
                )
                withStyle(
                    style = SpanStyle(
                        textDecoration = TextDecoration.Underline
                    )
                ) {
                    append(" $name $lastName ")
                }

                append(
                    stringResource(
                        id = R.string.ara_msg_request_validation_agreement_approve_part2
                    )
                )
            }
        )


    }
}


@Composable
private fun SubmitDocumentRequestSuccessHandler(
    response: SubmitResponseValidationView?,
    onValueChange: () -> Unit
) {
    val dialog = getSuccessDialog(
        title = stringResource(
            id = R.string.ara_label_request_validation
        ),
        description = ""
    ) {}

    if (response != null) {
        dialog.setDialogDetailMessage(
            stringResource(
                id = R.string.ara_msg_request_validation_success,
                response.documentSerialNumber
            )
        ).setSubmitAction {
            dialog.dismiss()
            onValueChange()
        }.show()
    }
}
