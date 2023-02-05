package ir.part.sdk.ara.ui.document.detailDocument

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import ir.part.sdk.ara.common.ui.view.divider
import ir.part.sdk.ara.common.ui.view.highlightBackground
import ir.part.sdk.ara.common.ui.view.primaryVariant
import ir.part.sdk.ara.common.ui.view.theme.captionBoldTextPrimary
import ir.part.sdk.ara.common.ui.view.theme.captionTextSecondary
import ir.part.sdk.ara.common.ui.view.theme.h6BoldTextPrimary
import ir.part.sdk.ara.common.ui.view.theme.subtitle2BoldTextPrimary
import ir.part.sdk.ara.common.ui.view.utils.dialog.DimensionResource
import ir.part.sdk.ara.ui.document.R
import ir.part.sdk.ara.ui.document.overviewDocument.DocumentSharedViewModel
import ir.part.sdk.ara.ui.document.submitDocument.model.PersonalDocumentMessageView
import ir.part.sdk.ara.ui.document.submitDocument.model.PersonalDocumentsView

@Composable
fun DocumentStatusScreen(viewModel: DocumentSharedViewModel? = null) {

    var selectedDocument: PersonalDocumentsView? by remember {
        mutableStateOf(null)
    }
    selectedDocument = viewModel?.itemPersonalDocument?.value

    DocumentStatus(
        selectedDocument,
    )
}

@Composable
private fun DocumentStatus(
    selectedDocument: PersonalDocumentsView?,
) {

    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Text(
            text = stringResource(id = stringRec.ara_label_files_messages_list_title),
            style = MaterialTheme.typography.h6BoldTextPrimary(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = dimensionResource(
                        id = dimensRec
                            .spacing_6x
                    ),
                    end = dimensionResource(id = dimensRec.spacing_4x),
                    start = dimensionResource(id = dimensRec.spacing_4x),
                    bottom = dimensionResource(id = dimensRec.spacing_5x)
                ),
            textAlign = TextAlign.Start
        )
        LazyColumn(modifier = Modifier.padding(top = dimensionResource(id = DimensionResource.spacing_2x))) {
            item {
                selectedDocument?.messageList?.forEach {
                    DocumentStatusItemList(
                        fileItem = it
                    )
                }
            }
        }
    }
}

@Composable
private fun DocumentStatusItemList(
    fileItem: PersonalDocumentMessageView,
) {

    val constrainParent = ConstraintSet {
        val clBox = createRefFor("clBox")
        val clLine = createRefFor("clLine")
        constrain(clBox) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            end.linkTo(parent.end)
            start.linkTo(clLine.end)
            width = Dimension.fillToConstraints
        }
        constrain(clLine) {
            top.linkTo(clBox.top)
            bottom.linkTo(clBox.bottom)
            start.linkTo(parent.start)
            height = Dimension.fillToConstraints
        }
    }
    val constrain = ConstraintSet {
        val documentStatusIcon = createRefFor("documentStatusIcon")
        val documentStatusTitle = createRefFor("documentStatusTitle")
        val documentStatusDes = createRefFor("documentStatusDes")
        constrain(documentStatusIcon) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
        }
        constrain(documentStatusTitle) {
            start.linkTo(documentStatusIcon.end)
            top.linkTo(documentStatusIcon.top)
            bottom.linkTo(documentStatusIcon.bottom)
        }
        constrain(documentStatusDes) {
            top.linkTo(documentStatusIcon.bottom)
            start.linkTo(documentStatusIcon.end)
        }
    }

    val constrainLine = ConstraintSet {
        val line = createRefFor("line")
        val circular = createRefFor("circular")
        val documentDate = createRefFor("docDate")
        constrain(line) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            height = Dimension.fillToConstraints
        }
        constrain(circular) {
            top.linkTo(line.top)
            bottom.linkTo(line.bottom)
            end.linkTo(line.end)
            start.linkTo(line.start)
        }
        constrain(documentDate) {
            start.linkTo(line.end)
            top.linkTo(circular.top)
            bottom.linkTo(circular.bottom)
        }
    }

    ConstraintLayout(
        constrainParent, modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = dimensionResource(id = dimensRec.spacing_5x),
                end = dimensionResource(id = dimensRec.spacing_5x),
                top = dimensionResource(id = dimensRec.spacing_2x),
                bottom = dimensionResource(id = dimensRec.spacing_2x)
            )
    ) {

        ConstraintLayout(
            constraintSet = constrain,
            modifier = Modifier
                .background(
                    color = fileItem.statusCode?.backgroundColor
                        ?: MaterialTheme.colors.highlightBackground(), RoundedCornerShape(10.dp)
                )
                .padding(
                    end = dimensionResource(id = dimensRec.spacing_2x),
                    top = dimensionResource(id = dimensRec.spacing_2x),
                    bottom = dimensionResource(id = dimensRec.spacing_2x),
                    start = dimensionResource(id = dimensRec.spacing_4x)
                )
                .layoutId("clBox")
        ) {
            Image(
                painter = fileItem.statusCode?.icon ?: painterResource(R.drawable.ara_ic_hourglass),
                contentDescription = "",
                modifier = Modifier
                    .layoutId("documentStatusIcon")
                    .width(dimensionResource(id = dimensRec.spacing_4x))
                    .height(dimensionResource(id = dimensRec.spacing_4x))

            )

            Text(
                text = fileItem.statusCode?.messageId?.let { stringResource(id = it) }
                    ?: "-",
                modifier = Modifier
                    .layoutId("documentStatusTitle")
                    .padding(start = dimensionResource(id = dimensRec.spacing_2x)),
                style = MaterialTheme.typography.subtitle2BoldTextPrimary(),
                color = fileItem.statusCode?.color ?: MaterialTheme.colors.primaryVariant()
            )
            Text(
                text = fileItem.message.toString(),
                modifier = Modifier
                    .fillMaxWidth()
                    .layoutId("documentStatusDes")
                    .padding(
                        end = dimensionResource(id = dimensRec.spacing_2x),
                        top = dimensionResource(id = dimensRec.spacing_3x),
                        bottom = dimensionResource(id = dimensRec.spacing_2x)
                    ),
                style = MaterialTheme.typography.captionBoldTextPrimary(),
                textAlign = TextAlign.Start
            )
        }

        ConstraintLayout(
            modifier = Modifier
                .layoutId("clLine"),
            constraintSet = constrainLine
        ) {
            Spacer(
                modifier = Modifier
                    .background(MaterialTheme.colors.divider())
                    .width(dimensionResource(id = dimensRec.divider_height))
                    .layoutId("line")
            )
            Spacer(
                modifier = Modifier
                    .background(MaterialTheme.colors.divider(), RoundedCornerShape(100.dp))
                    .width(dimensionResource(id = dimensRec.spacing_2x))
                    .height(dimensionResource(id = dimensRec.spacing_2x))
                    .layoutId("circular")
            )
            Text(
                text = fileItem.dateTime.toString(), modifier = Modifier
                    .layoutId("docDate")
                    .padding(dimensionResource(id = dimensRec.spacing_4x)),
                style = MaterialTheme.typography.captionTextSecondary()
            )
        }
    }

}
