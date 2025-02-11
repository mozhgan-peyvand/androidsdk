package ir.part.sdk.ara.ui.document.detailDocument


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import ir.part.sdk.ara.common.ui.view.api.PublicState
import ir.part.sdk.ara.common.ui.view.common.ProcessLoadingAndErrorState
import ir.part.sdk.ara.common.ui.view.divider
import ir.part.sdk.ara.common.ui.view.rememberFlowWithLifecycle
import ir.part.sdk.ara.common.ui.view.theme.h6BoldTextPrimary
import ir.part.sdk.ara.common.ui.view.utils.dialog.DimensionResource
import ir.part.sdk.ara.ui.document.R
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

    ProcessLoadingAndErrorState(loadingErrorState.value, removeErrorsFromStates = {
        viewModel.clearAllMessage()
    })

    DocumentDetail(
        personalInfoSubmitDocumentView,
        selectedDocument
    )
}


@Composable
private fun DocumentDetail(
    personalInfoSubmitDocumentView: PersonalInfoSubmitDocumentView?,
    selectedDocument: PersonalDocumentsView?
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = stringResource(id = stringRec.ara_label_file_detail_request_title),
            style = MaterialTheme.typography.h6BoldTextPrimary(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = dimensionResource(id = dimensRec.spacing_6x),
                    end = dimensionResource(id = dimensRec.spacing_4x),
                    start = dimensionResource(id = dimensRec.spacing_4x),
                    bottom = dimensionResource(id = dimensRec.spacing_5x)
                ),
            textAlign = TextAlign.Start
        )
        DocumentGenericLayout(
            title = stringRec.ara_label_file_number,
            selectedDocument?.fileId.toString(),
            drawableRec.merat_ic_folder_simple, Modifier
        )
        Divider(
            color = MaterialTheme.colors.divider(),
            thickness = dimensionResource(id = DimensionResource.divider_height),
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = dimensionResource(id = DimensionResource.spacing_base),
                    start = dimensionResource(id = DimensionResource.spacing_4x),
                    end = dimensionResource(id = DimensionResource.spacing_4x)
                )
        )
        DocumentGenericLayout(
            title = stringRec.ara_label_customer_name,
            personalInfoSubmitDocumentView?.name + " " + personalInfoSubmitDocumentView?.lastname,
            drawableRec.merat_ic_single, Modifier
                .padding(top = dimensionResource(id = DimensionResource.spacing_base))
        )
        Divider(
            color = MaterialTheme.colors.divider(),
            thickness = dimensionResource(id = DimensionResource.divider_height),
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = dimensionResource(id = DimensionResource.spacing_base),
                    start = dimensionResource(id = DimensionResource.spacing_4x),
                    end = dimensionResource(id = DimensionResource.spacing_4x)
                )
        )
        DocumentGenericLayout(
            title = stringRec.ara_label_club_id,
            selectedDocument?.unionId.toString(),
            drawableRec.merat_ic_multiple, Modifier
                .padding(top = dimensionResource(id = DimensionResource.spacing_base))
        )
        Divider(
            color = MaterialTheme.colors.divider(),
            thickness = dimensionResource(id = DimensionResource.divider_height),
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = dimensionResource(id = DimensionResource.spacing_base),
                    start = dimensionResource(id = DimensionResource.spacing_4x),
                    end = dimensionResource(id = DimensionResource.spacing_4x)
                )
        )
        DocumentGenericLayout(
            title = stringRec.ara_label_files_messages_list_title,
            value = selectedDocument?.status?.messageId?.let { stringResource(id = it) } ?: "-",
            icon = drawableRec.merat_ic_folder_close,
            modifier = Modifier
                .padding(top = dimensionResource(id = DimensionResource.spacing_base)),
            color = selectedDocument?.status?.color
        )
        Divider(
            color = MaterialTheme.colors.divider(),
            thickness = dimensionResource(id = DimensionResource.divider_height),
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = dimensionResource(id = DimensionResource.spacing_base),
                    start = dimensionResource(id = DimensionResource.spacing_4x),
                    end = dimensionResource(id = DimensionResource.spacing_4x)
                )
        )
        DocumentGenericLayout(
            title = stringRec.ara_label_file_date_request,
            selectedDocument?.requestDateView.toString(),
            drawableRec.merat_ic_calendar_event, Modifier
                .padding(top = dimensionResource(id = DimensionResource.spacing_base))
        )
        Divider(
            color = MaterialTheme.colors.divider(),
            thickness = dimensionResource(id = DimensionResource.divider_height),
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = dimensionResource(id = DimensionResource.spacing_base),
                    start = dimensionResource(id = DimensionResource.spacing_4x),
                    end = dimensionResource(id = DimensionResource.spacing_4x)
                )
        )
        DocumentGenericLayout(
            title = stringRec.ara_label_file_date_completion,
            selectedDocument?.completionDateView.toString(),
            drawableRec.merat_ic_calendar_date, Modifier
                .padding(top = dimensionResource(id = DimensionResource.spacing_base))
        )
    }

}

