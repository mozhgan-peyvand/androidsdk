package ir.part.sdk.ara.ui.document.overviewDocument

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import ir.part.sdk.ara.common.ui.view.*
import ir.part.sdk.ara.common.ui.view.api.PublicState
import ir.part.sdk.ara.common.ui.view.api.UiMessage
import ir.part.sdk.ara.common.ui.view.theme.*
import ir.part.sdk.ara.common.ui.view.utils.dialog.getErrorDialog
import ir.part.sdk.ara.common.ui.view.utils.dialog.getInfoDialog
import ir.part.sdk.ara.common.ui.view.utils.dialog.getLoadingDialog
import ir.part.sdk.ara.ui.document.R
import ir.part.sdk.ara.ui.document.submitDocument.model.DocumentsStatusView
import ir.part.sdk.ara.ui.document.submitDocument.model.PersonalDocumentsView
import ir.part.sdk.ara.ui.document.submitDocument.model.StatusParamView
import ir.part.sdk.ara.ui.document.utils.common.AraFileDoesNotExist
import ir.part.sdk.ara.ui.document.utils.common.AraFileNotFound
import ir.part.sdk.ara.ui.document.utils.common.AraRetryLayout

typealias StringRes = R.string
typealias DrawableRes = R.drawable
typealias DimenRes = R.dimen


//todo : check merat dialog and stuff when clicked on payment icon
//todo : in status codes add colors too !!
//todo : names need refactoring !!

@Composable
fun FileListScreen(
    viewModel: DocumentSharedViewModel?,
    navigateToDetail: () -> Unit,
    navigateToValidationResult: () -> Unit
) {

    var setHasUnreadMessageResponse: Boolean? by remember {
        mutableStateOf(null)
    }

    val validationResultErrorDialog = getErrorDialog(
        title = stringResource(id = R.string.label_file_validation_result),
        description = stringResource(
            id = R.string.msg_file_validation_result
        )
    ) {}

    var docList: List<PersonalDocumentsView>? by remember {
        mutableStateOf(listOf())
    }

    var documentStatusConstants: Map<String, StatusParamView?>? by remember {
        mutableStateOf(mapOf())
    }

    val loadingErrorState = viewModel?.let {
        rememberFlowWithLifecycle(flow = viewModel.loadingAndMessageState).collectAsState(
            initial = PublicState.Empty
        )
    }


    docList = viewModel?.documents?.collectAsState()?.value

    documentStatusConstants =
        viewModel?.overviewDocumentState?.collectAsState(initial = null)?.value?.personalInfoConstantsItem?.documentStatusNameEntity

    SetHasUnreadMessageRequestSuccessHandler(response = setHasUnreadMessageResponse) {
        setHasUnreadMessageResponse = null
        navigateToValidationResult.invoke()
    }

    ProcessLoadingAndErrorState(input = loadingErrorState?.value)

    if (loadingErrorState != null) {
        ScreenContent(
            documentStatusConstants = documentStatusConstants,
            docList = docList,
            refresh = {
                viewModel.refreshFileListScreenRequest()
            },
            isRefreshing = loadingErrorState.value.refreshing,
            onDocumentSelect = {
                viewModel.itemPersonalDocument.value = it
                navigateToDetail()
            },
            uiErrorMessage = loadingErrorState.value.message,
            onDocumentValidationIconClick = { statusCode, id, document ->

                viewModel.itemPersonalDocument.value = document
                id?.let {
                    viewModel.setHasUnreadMessage(false, id.toString()) {
                        setHasUnreadMessageResponse = it
                    }
                }

                // todo : use status code when it is ready
//            when (statusCode) {
//                DocumentsStatusView.Code_200 -> {
//                    validationResultErrorDialog.setSubmitAction {
//                        validationResultErrorDialog.dismiss()
//                    }.show()
//                }
//                DocumentsStatusView.Code_21,
//                DocumentsStatusView.Code_31 -> {
//                    id?.let {
//                        viewModel.setDisableCustomerFlag(id.toString(), ReadMessage(false))
//                    }
//                    navigateToValidationResult()
//                }
//                else -> {
//                    //nothing
//                }
//            }
            },
            onDocumentRemoveIconClick = {
                //todo : add this when delete api is ready
            },
            onDocumentPaymentIconClick = {
                //todo : add this when payment api is ready
            }
        )
    }
}

@Composable
private fun ScreenContent(
    documentStatusConstants: Map<String, StatusParamView?>?,
    docList: List<PersonalDocumentsView>?,
    refresh: () -> Unit,
    isRefreshing: Boolean,
    onDocumentSelect: (PersonalDocumentsView) -> Unit,
    uiErrorMessage: UiMessage?,
    onDocumentValidationIconClick: (DocumentsStatusView?, Long?, PersonalDocumentsView?) -> Unit,
    onDocumentRemoveIconClick: () -> Unit,
    onDocumentPaymentIconClick: () -> Unit
) {

    var filteredDocList: List<PersonalDocumentsView>? by remember {
        mutableStateOf(listOf())
    }

    var listHasFilter by remember {
        mutableStateOf(false)
    }

    var inputTextValue by remember { mutableStateOf("") }

    val swipeRefreshState =
        rememberSwipeRefreshState(isRefreshing = isRefreshing)

    Column(
        modifier = Modifier
            .padding(
                top = dimensionResource(id = R.dimen.spacing_3x),
                start = dimensionResource(id = R.dimen.spacing_4x),
                end = dimensionResource(id = R.dimen.spacing_4x)
            )
    ) {

        SearchTextField(onInputChange = { searchText ->
            inputTextValue = searchText
            if (docList.isNullOrEmpty()) {
                listHasFilter = false
            } else {
                filteredDocList = docList.filter {
                    it.fileId.toString().contains(searchText)
                }
                listHasFilter = true
            }
        }, inputTextValue)

        Text(
            text = stringResource(id = R.string.label_documents_information),
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = dimensionResource(id = R.dimen.spacing_7x)
                ),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.h6BoldPrimary()
        )

        if (uiErrorMessage != null && !isRefreshing) {
            AraRetryLayout()
        }

        SwipeRefresh(modifier = Modifier
            .padding(top = dimensionResource(id = R.dimen.spacing_4x))
            .fillMaxSize(),
            state = swipeRefreshState,
            onRefresh = {
                inputTextValue = ""
                listHasFilter = false
                refresh()
            }) {

            when (listHasFilter) {
                false -> {
                    if (!docList.isNullOrEmpty()) {
                        DocumentsList(
                            documentStatusConstants = documentStatusConstants,
                            documents = docList,
                            onItemClick = { onDocumentSelect(it) },
                            onDocumentValidationIconClick = { statusCode, id, document ->
                                onDocumentValidationIconClick(statusCode, id, document)
                            },
                            onDocumentRemoveIconClick = { onDocumentRemoveIconClick() },
                            onDocumentPaymentIconClick = { onDocumentPaymentIconClick() })
                    } else {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(ScrollState(0)),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                AraFileDoesNotExist()
                            }
                        }
                    }
                }
                true -> {
                    if (!filteredDocList.isNullOrEmpty()) {
                        DocumentsList(
                            documentStatusConstants = documentStatusConstants,
                            documents = filteredDocList ?: listOf(),
                            onItemClick = { onDocumentSelect(it) },
                            onDocumentValidationIconClick = { statusCode, id, document ->
                                onDocumentValidationIconClick(statusCode, id, document)
                            },
                            onDocumentRemoveIconClick = { onDocumentRemoveIconClick() },
                            onDocumentPaymentIconClick = { onDocumentPaymentIconClick() })
                    } else {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(ScrollState(0)),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                AraFileNotFound()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SearchTextField(onInputChange: (String) -> Unit, inputTextValue: String) {

    val focusManager = LocalFocusManager.current

    Card(elevation = dimensionResource(id = R.dimen.card_elevation_normal)) {
        TextField(
            value = inputTextValue,
            onValueChange = {
                onInputChange(it)
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth(),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Number
            ),
            placeholder = {
                Text(
                    text = stringResource(id = R.string.label_files_search_hint),
                    color = MaterialTheme.colors.textSecondary(),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )
            },
            leadingIcon = {
                Image(
                    painter = painterResource(id = R.drawable.ara_ic_search),
                    contentDescription = "ic_search"
                )
            }
        )
    }
}

@Composable
private fun DocumentsList(
    documentStatusConstants: Map<String, StatusParamView?>?,
    documents: List<PersonalDocumentsView>,
    onItemClick: (PersonalDocumentsView) -> Unit,
    onDocumentValidationIconClick: (DocumentsStatusView?, Long?, PersonalDocumentsView?) -> Unit,
    onDocumentRemoveIconClick: () -> Unit,
    onDocumentPaymentIconClick: () -> Unit
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = dimensionResource(id = R.dimen.spacing_5x)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_4x)),
        contentPadding = PaddingValues(vertical = dimensionResource(id = R.dimen.spacing_2x))
    )
    {

        items(items = documents) { document ->
            DocumentListItem(
                documentStatusConstants = documentStatusConstants,
                document = document,
                onItemClick = {
                    onItemClick(it)
                },
                onDocumentRemoveIconClick = { onDocumentRemoveIconClick() },
                onDocumentPaymentIconClick = { onDocumentPaymentIconClick() },
                onDocumentValidationIconClick = { statusCode, id, selectedDocument ->
                    onDocumentValidationIconClick(statusCode, id, selectedDocument)
                })
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun DocumentListItem(
    documentStatusConstants: Map<String, StatusParamView?>?,
    document: PersonalDocumentsView,
    onItemClick: (PersonalDocumentsView) -> Unit,
    onDocumentValidationIconClick: (DocumentsStatusView?, Long?, PersonalDocumentsView?) -> Unit,
    onDocumentRemoveIconClick: () -> Unit,
    onDocumentPaymentIconClick: () -> Unit
) {

    val constraints = ConstraintSet {
        val imageBoxIcon = createRefFor("imageDoc")
        val status = createRefFor("status")
        val statusResultMessage = createRefFor("statusResultMessage")
        val documentName = createRefFor("documentName")
        val date = createRefFor("date")
        val dateDrawable = createRefFor("dateDrawable")
        val deleteIcon = createRefFor("deleteIcon")
        val validationFileDrawable = createRefFor("validationFileDrawable")
        val creditCardDrawable = createRefFor("creditCardDrawable")
        val line = createRefFor("line")

        constrain(imageBoxIcon) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
        }
        constrain(documentName) {
            top.linkTo(imageBoxIcon.top)
            start.linkTo(imageBoxIcon.end)
        }
        constrain(dateDrawable) {
            top.linkTo(documentName.bottom)
            start.linkTo(imageBoxIcon.end)
        }
        constrain(date) {
            top.linkTo(dateDrawable.top)
            start.linkTo(dateDrawable.end)
            bottom.linkTo(dateDrawable.bottom)
        }
        constrain(status) {
            top.linkTo(imageBoxIcon.bottom)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
        }
        constrain(statusResultMessage) {
            top.linkTo(status.top)
            start.linkTo(status.end)
            bottom.linkTo(status.bottom)
        }
        constrain(deleteIcon) {
            end.linkTo(parent.end)
            top.linkTo(imageBoxIcon.top)
            bottom.linkTo(imageBoxIcon.bottom)
        }
        constrain(line) {
            end.linkTo(deleteIcon.start)
            top.linkTo(imageBoxIcon.top)
            bottom.linkTo(imageBoxIcon.bottom)
        }
        constrain(validationFileDrawable) {
            end.linkTo(line.start)
            top.linkTo(imageBoxIcon.top)
            bottom.linkTo(imageBoxIcon.bottom)
        }
        constrain(creditCardDrawable) {
            end.linkTo(line.start)
            top.linkTo(imageBoxIcon.top)
            bottom.linkTo(imageBoxIcon.bottom)
        }
    }

    Card(
        elevation = dimensionResource(id = R.dimen.card_elevation_normal),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.radius_normal)),
        onClick = {
            onItemClick(document)
        }
    ) {
        ConstraintLayout(
            constraintSet = constraints,
            modifier = Modifier
                .padding(
                    top = dimensionResource(id = R.dimen.spacing_2x),
                    bottom = dimensionResource(id = R.dimen.spacing_2x),
                    start = dimensionResource(id = R.dimen.spacing_4x),
                    end = dimensionResource(id = R.dimen.spacing_2x)
                )
                .fillMaxWidth()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.radius_normal))
                )
        ) {
            Box(
                modifier = Modifier
                    .layoutId("imageDoc")
                    .background(
                        color = if (document.hasUnreadMessage) MaterialTheme.colors.errorBackground() else MaterialTheme.colors.highlightBackground(),
                        shape = RoundedCornerShape(dimensionResource(id = R.dimen.radius_big))
                    )
                    .width(dimensionResource(id = R.dimen.spacing_13x))
                    .height(dimensionResource(id = R.dimen.spacing_13x)),
                contentAlignment = Alignment.Center
            ) {

                Icon(
                    painter = painterResource(id = if (document.hasUnreadMessage) R.drawable.merat_ic_mail else R.drawable.merat_ic_newsletter),
                    contentDescription = "",
                    tint = if (document.hasUnreadMessage) MaterialTheme.colors.error() else MaterialTheme.colors.primaryVariant()
                )
            }

            Image(
                modifier = Modifier
                    .layoutId("dateDrawable")
                    .padding(
                        start = dimensionResource(id = R.dimen.spacing_2x),
                        top = dimensionResource(id = R.dimen.spacing_2x)
                    ),
                painter = painterResource(id = R.drawable.ara_ic_calendar),
                contentDescription = "ic_calendar"

            )

            Text(
                modifier = Modifier
                    .layoutId("documentName")
                    .padding(start = dimensionResource(id = R.dimen.spacing_2x)),
                text = stringResource(
                    id = R.string.label_file_number_title,
                    document.fileId.toString()
                ),
                style = MaterialTheme.typography.subtitle2PrimaryBold(),
                textAlign = TextAlign.End
            )

            Text(
                modifier = Modifier
                    .layoutId("date")
                    .padding(
                        start = dimensionResource(id = R.dimen.spacing_2x),
                        top = dimensionResource(id = R.dimen.spacing_2x)
                    ),
                text = document.requestDateView ?: "",
                style = MaterialTheme.typography.caption_Secondary()
            )

            Text(
                modifier = Modifier
                    .layoutId("status")
                    .padding(top = dimensionResource(id = R.dimen.spacing_2x)),
                text = stringResource(id = R.string.label_status) + " ",
                style = MaterialTheme.typography.captionPrimaryBold()
            )

            Text(
                modifier = Modifier
                    .layoutId("statusResultMessage")
                    .padding(
                        start = dimensionResource(id = R.dimen.spacing_base),
                        top = dimensionResource(id = R.dimen.spacing_2x)
                    ),
                text = documentStatusConstants?.get(document.statusId?.value)?.name ?: "",
                style = MaterialTheme.typography.captionPrimaryBoldSuccess(),
                color = document.statusId?.color ?: DocumentsStatusView.CODE_11.color
            )

            if (document.statusId == DocumentsStatusView.CODE_21 ||
                (document.statusId?.code != null &&
                        document.statusId?.code!! < 18f)
            ) {
                Icon(
                    modifier = Modifier
                        .layoutId("deleteIcon")
                        .padding(start = dimensionResource(id = R.dimen.spacing_base))
                        .clickable {
                            onDocumentRemoveIconClick()
                        },
                    painter = painterResource(id = R.drawable.ara_ic_bin),
                    contentDescription = "ara_ic_bin",
                    tint = MaterialTheme.colors.textSecondary()
                )

                Spacer(
                    modifier = Modifier
                        .layoutId("line")
                        .width(dimensionResource(id = R.dimen.divider_height))
                        .height(dimensionResource(id = R.dimen.spacing_7x))
                        .background(MaterialTheme.colors.textSecondary())
                )

            }

            if (document.statusId == DocumentsStatusView.CODE_18) {
                Icon(
                    modifier = Modifier
                        .layoutId("creditCardDrawable")
                        .width(dimensionResource(id = R.dimen.spacing_8x))
                        .clickable {
                            onDocumentPaymentIconClick()
                        },
                    painter = painterResource(id = R.drawable.ara_ic_credit_card),
                    contentDescription = "ara_ic_credit_card",
                    tint = MaterialTheme.colors.success()
                )
            }

            // todo : use status code when it is ready
//            if ((document.statusId == DocumentsStatusView.Code_200 && document.validationResult?.chartData != null) ||
//                document.statusId == DocumentsStatusView.Code_21 || document.statusId == DocumentsStatusView.Code_31
//            )
            if (!document.showValidationProperties
            ) {
                Icon(
                    modifier = Modifier
                        .layoutId("validationFileDrawable")
                        .clickable {
                            onDocumentValidationIconClick(
                                document.statusId,
                                document.fileId,
                                document
                            )
                        },
                    painter = painterResource(id = R.drawable.ara_ic_validation_file),
                    contentDescription = "ara_ic_validation_file",
                    tint = MaterialTheme.colors.primaryVariant()
                )
            }
        }
    }
}

@Composable
private fun ProcessLoadingAndErrorState(input: PublicState?) {
    val loadingDialog = getLoadingDialog()
    val errorDialog = getInfoDialog(
        title = stringResource(id = R.string.label_warning_title_dialog),
        description = ""
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

@Composable
private fun SetHasUnreadMessageRequestSuccessHandler(
    response: Boolean?,
    onValueChange: () -> Unit
) {
    if (response != null) {
        onValueChange()
    }
}