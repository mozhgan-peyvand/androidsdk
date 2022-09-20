package ir.part.sdk.ara.ui.document


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import ir.part.sdk.ara.common.ui.view.api.PublicState
import ir.part.sdk.ara.common.ui.view.rememberFlowWithLifecycle
import ir.part.sdk.ara.common.ui.view.theme.h6BoldTextPrimary
import ir.part.sdk.ara.common.ui.view.utils.dialog.getInfoDialog
import ir.part.sdk.ara.common.ui.view.utils.dialog.getLoadingDialog
import ir.part.sdk.ara.ui.document.overviewDocument.DocumentSharedViewModel
import ir.part.sdk.ara.ui.document.submitDocument.model.PersonalDocumentsView
import ir.part.sdk.ara.ui.document.submitDocument.model.PersonalInfoConstantsView
import ir.part.sdk.ara.ui.document.submitDocument.model.PersonalInfoSubmitDocumentView
import ir.part.sdk.ara.ui.document.utils.common.DocumentGenericLayout

typealias stringRec = R.string
typealias drawableRec = R.drawable
typealias dimensRec = R.dimen


@Composable
fun DocumentDetailsScreen(viewModel: DocumentSharedViewModel) {

    val loadingErrorState =
        rememberFlowWithLifecycle(flow = viewModel.loadingAndMessageState).collectAsState(
            initial = PublicState.Empty
        )

    var personalInfoConstantsItem: PersonalInfoConstantsView? by remember {
        mutableStateOf(null)
    }

    var personalInfoSubmitDocumentView: PersonalInfoSubmitDocumentView? by remember {
        mutableStateOf(null)
    }

    var selectedDocument: PersonalDocumentsView? by remember {
        mutableStateOf(null)
    }

    personalInfoConstantsItem =
        viewModel.overviewDocumentState.collectAsState().value.personalInfoConstantsItem
    personalInfoSubmitDocumentView =
        viewModel.overviewDocumentState.collectAsState().value.personalInfoSubmitDocumentView
    selectedDocument = viewModel.itemPersonalDocument.value

    ProcessLoadingAndErrorState(input = loadingErrorState.value)

    DocumentDetail(
        personalInfoConstantsItem,
        personalInfoSubmitDocumentView,
        selectedDocument
    )
}


@Composable
private fun DocumentDetail(
    personalInfoConstantsItem: PersonalInfoConstantsView?,
    personalInfoSubmitDocumentView: PersonalInfoSubmitDocumentView?,
    selectedDocument: PersonalDocumentsView?
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = stringResource(id = stringRec.label_file_detail_request_title),
            style = MaterialTheme.typography.h6BoldTextPrimary(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = dimensionResource(id = dimensRec.spacing_7x),
                    end = dimensionResource(id = dimensRec.spacing_4x),
                    start = dimensionResource(id = dimensRec.spacing_4x)
                ),
            textAlign = TextAlign.Start
        )
        DocumentGenericLayout(
            title = stringRec.label_file_number,
            selectedDocument?.fileId.toString(),
            drawableRec.merat_ic_folder_simple, Modifier
        )
        DocumentGenericLayout(
            title = stringRec.label_customer_name,
            personalInfoSubmitDocumentView?.name + " " + personalInfoSubmitDocumentView?.lastname,
            drawableRec.merat_ic_single, Modifier
        )
        DocumentGenericLayout(
            title = stringRec.label_club_id,
            selectedDocument?.unionId.toString(),
            drawableRec.merat_ic_multiple, Modifier
        )
        DocumentGenericLayout(
            title = stringRec.label_files_messages_list_title,
            personalInfoConstantsItem?.documentStatusNameEntity?.get(
                selectedDocument?.statusId?.value
            )?.title ?: "-",
            drawableRec.merat_ic_folder_close, Modifier,
            color = selectedDocument?.statusId?.color
        )
        DocumentGenericLayout(
            title = stringRec.label_file_date_request,
            selectedDocument?.requestDateView.toString(),
            drawableRec.merat_ic_calendar_event, Modifier
        )
        DocumentGenericLayout(
            title = stringRec.label_file_date_completion,
            selectedDocument?.completionDateView.toString(),
            drawableRec.merat_ic_calendar_date, Modifier
        )
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