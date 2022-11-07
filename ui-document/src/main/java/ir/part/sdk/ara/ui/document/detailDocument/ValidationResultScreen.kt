package ir.part.sdk.ara.ui.document.detailDocument

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import ir.part.sdk.ara.common.ui.view.divider
import ir.part.sdk.ara.common.ui.view.primaryDark
import ir.part.sdk.ara.common.ui.view.theme.body2BoldTextSecondary
import ir.part.sdk.ara.common.ui.view.theme.body2TextSecondary
import ir.part.sdk.ara.common.ui.view.theme.h6BoldTextPrimary
import ir.part.sdk.ara.ui.document.R
import ir.part.sdk.ara.ui.document.overviewDocument.DocumentSharedViewModel
import ir.part.sdk.ara.ui.document.submitDocument.model.PersonalDocumentsView
import ir.part.sdk.ara.ui.document.submitDocument.model.PersonalInfoSubmitDocumentView
import ir.part.sdk.ara.ui.document.utils.common.DocumentGenericLayout

typealias StringRes = R.string
typealias DimenRes = R.dimen

@Composable
fun ValidationResultScreen(viewModel: DocumentSharedViewModel? = null) {

    var selectedDocument: PersonalDocumentsView? by remember {
        mutableStateOf(null)
    }

    selectedDocument = viewModel?.itemPersonalDocument?.value

    var personalInfoSubmitDocumentView: PersonalInfoSubmitDocumentView? by remember {
        mutableStateOf(null)
    }

    personalInfoSubmitDocumentView =
        viewModel?.overviewDocumentState?.collectAsState()?.value?.personalInfoSubmitDocumentView

    ValidationResult(
        selectedDocument,
        personalInfoSubmitDocumentView,
        Modifier
    )
}

@Composable
private fun ValidationResult(
    selectedDocument: PersonalDocumentsView?,
    personalInfoSubmitDocumentView: PersonalInfoSubmitDocumentView?,
    modifier: Modifier
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        Text(
            text = stringResource(id = StringRes.ara_label_file_validation_result),
            style = MaterialTheme.typography.h6BoldTextPrimary(),
            modifier = modifier
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
            title = stringRec.ara_label_customer_name,
            personalInfoSubmitDocumentView?.name + " " + personalInfoSubmitDocumentView?.lastname,
            drawableRec
                .merat_ic_single,
            modifier
        )
        DocumentGenericLayout(
            title = stringRec.ara_label_personal_national_code,
            personalInfoSubmitDocumentView?.nationalCode.toString(),
            drawableRec.merat_ic_focus, modifier
        )
        DocumentGenericLayout(
            title = stringRec.ara_label_file_number,
            selectedDocument?.fileId.toString(),
            drawableRec.merat_ic_folder_simple, modifier
        )
        Text(
            text = stringResource(id = stringRec.ara_msg_result_validation_info),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.body2BoldTextSecondary(),
            modifier = Modifier
                .background(MaterialTheme.colors.divider())
                .padding(dimensionResource(id = dimensRec.spacing_4x))
        )
        DocumentGenericLayout(
            title = stringRec.ara_label_refund_capability_momtaz,
            selectedDocument?.validationResult?.perfectProportionate.toString(),
            drawableRec.merat_ic_money, modifier
        )
        DocumentGenericLayout(
            title = stringRec.ara_label_refund_capability_perfect_proportionate,
            selectedDocument?.validationResult?.proportionate.toString(),
            drawableRec.merat_ic_money, modifier
        )
        DocumentGenericLayout(
            title = stringRec.ara_label_refund_capability_proportionate,
            selectedDocument?.validationResult?.deptCeiling.toString(),
            drawableRec.merat_ic_money_11, modifier
        )
        DocumentGenericLayout(
            title = stringRec.ara_label_refund_capability_debt_ceiling,
            selectedDocument?.validationResult?.commitmentCeiling.toString(),
            drawableRec.merat_ic_money_12, modifier
        )
        DocumentGenericLayout(
            title = stringRec.ara_label_refund_capability_commitment_ceiling,
            selectedDocument?.validationResult?.commitmentCeilingRemain.toString(),
            drawableRec.merat_ic_money_12, modifier
        )
        DocumentGenericLayout(
            title = stringRec.ara_label_expire_date,
            selectedDocument?.validationResult?.expireDateView.toString(),
            drawableRec.ara_ic_calendar,
            modifier
        )
        if (selectedDocument?.validationResult?.perfectProportionate != 0L && selectedDocument?.validationResult?.proportionate != 0L) {
            TextWithDot(des = stringRec.ara_msg_result_validation_info_1)
            TextWithDot(des = stringRec.ara_msg_result_validation_info_1)
            TextWithDot(des = stringRec.ara_msg_result_validation_info_2)
            TextWithDot(des = stringRec.ara_msg_result_validation_info_2)
        }
        TextWithDot(des = stringRec.ara_msg_result_validation_info_3)
        TextWithDot(des = stringRec.ara_msg_result_validation_info_4)
        Text(
            text = stringResource(
                id = stringRec.ara_msg_result_validation_info_date,
                selectedDocument?.fileId.toString(),
                selectedDocument?.requestDateView.toString()
            ),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.body2BoldTextSecondary(),
            modifier = Modifier
                .background(MaterialTheme.colors.divider())
                .padding(dimensionResource(id = dimensRec.spacing_4x)),
        )

    }
}

@Composable
private fun TextWithDot(des: Int, shape: RoundedCornerShape = CircleShape) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = des),
            style = MaterialTheme.typography.body2TextSecondary(),
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(
                end = dimensionResource(id = DimenRes.spacing_2x),
                start = dimensionResource(id = DimenRes.spacing_4x)
            )
        )
        Spacer(
            modifier = Modifier
                .width(dimensionResource(id = dimensRec.spacing_2x))
                .height(dimensionResource(id = dimensRec.spacing_2x))
                .background(MaterialTheme.colors.primaryDark(), shape = shape)
        )
    }
}
