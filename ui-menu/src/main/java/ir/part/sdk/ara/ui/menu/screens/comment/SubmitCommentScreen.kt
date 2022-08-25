package ir.part.sdk.ara.ui.menu.screens.comment

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import ir.part.sdk.ara.common.ui.view.api.PublicState
import ir.part.sdk.ara.common.ui.view.common.SubmitActionContent
import ir.part.sdk.ara.common.ui.view.common.TopAppBarContent
import ir.part.sdk.ara.common.ui.view.divider
import ir.part.sdk.ara.common.ui.view.rememberFlowWithLifecycle
import ir.part.sdk.ara.common.ui.view.theme.*
import ir.part.sdk.ara.common.ui.view.utils.dialog.getErrorDialog
import ir.part.sdk.ara.common.ui.view.utils.dialog.getLoadingDialog
import ir.part.sdk.ara.common.ui.view.utils.dialog.getSuccessDialog
import ir.part.sdk.merat.ui.menu.R

@Composable
fun SubmitCommentScreen(onNavigateUp: () -> Unit, viewModel: SubmitCommentViewModel) {

    var showSuccessDialog by remember {
        mutableStateOf(false)
    }
    showSuccessDialog = viewModel.showSuccessDialog.value

    val loadingErrorState =
        rememberFlowWithLifecycle(flow = viewModel.loadingAndMessageState).collectAsState(
            initial = PublicState.Empty
        )
    ProcessLoadingAndErrorState(
        loadingErrorState.value,
        onErrorDialogDismissed = {
            viewModel.loadingAndMessageState.value.message = null
        })

    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(backgroundColor = MaterialTheme.colors.surface,
                elevation = dimensionResource(id = R.dimen.spacing_half_base)) {
                TopAppBarContent(title = stringResource(id = R.string.label_submit_comments),
                    onNavigateUp = {
                        onNavigateUp()
                    })
            }
        }, bottomBar = {
            SubmitActionContent(onSubmitClicked = {
                viewModel.sendComment()
            }, buttonText = R.string.label_send_comment)
        }) { innerPadding ->
        Box(Modifier.padding(innerPadding)) {
            CommentContent(viewModel = viewModel, showSuccessDialog = showSuccessDialog) {
                viewModel.setSuccessDialogAsSeen()
                onNavigateUp()
            }
        }
    }
}

@Composable
private fun CommentContent(
    viewModel: SubmitCommentViewModel,
    showSuccessDialog: Boolean,
    onSuccessDialogConfirmed: () -> Unit,
) {
    if (showSuccessDialog) {
        getSuccessDialog(
            title = stringResource(id = R.string.label_send_comment),
            description = stringResource(id = R.string.msg_submit_comment_success)) {
            onSuccessDialogConfirmed()

        }.show()
    }

    val scrollState = rememberScrollState()

    Column(modifier = Modifier
        .verticalScroll(scrollState)
        .padding(horizontal = dimensionResource(id = R.dimen.spacing_4x))) {

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_6x)))
        Text(text = stringResource(id = R.string.label_submit_comments),
            style = MaterialTheme.typography.h6Bold())

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_5x)))
        Text(text = stringResource(id = R.string.label_description_comment),
            style = MaterialTheme.typography.subtitle2TextSecondary())

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_8x)))

        CommentTextFieldItem(title = stringResource(id = R.string.label_name),
            value = viewModel.name.value,
            hint = stringResource(id = R.string.label_enter_your_name),
            onValueChanged = {
                viewModel.setName(it)
            }, errorMessage = if (viewModel.errorName.value.second.isNotEmpty())
                viewModel.errorName.value.second.last().validator.getErrorMessage(LocalContext.current)
            else "")
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_4x)))

        CommentTextFieldItem(title = stringResource(id = R.string.label_last_name),
            value = viewModel.lastName.value,
            hint = stringResource(id = R.string.label_enter_your_last_name),
            onValueChanged = {
                viewModel.setLastName(it)
            }, errorMessage = if (viewModel.errorLastName.value.second.isNotEmpty())
                viewModel.errorLastName.value.second.last().validator.getErrorMessage(LocalContext.current)
            else "")
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_4x)))


        CommentTextFieldItem(title = stringResource(id = R.string.label_email_persian),
            value = viewModel.email.value,
            hint = stringResource(id = R.string.label_enter_your_email),
            onValueChanged = {
                viewModel.setEmail(it)
            }, errorMessage = if (viewModel.errorEmail.value.second.isNotEmpty())
                viewModel.errorEmail.value.second.last().validator.getErrorMessage(LocalContext.current)
            else "",
            keyboardType = KeyboardType.Email)
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_4x)))


        CommentTextFieldItem(title = stringResource(id = R.string.label_phone_number_with_colon),
            value = viewModel.phone.value,
            hint = stringResource(id = R.string.label_enter_your_phone_number),
            onValueChanged = {
                viewModel.setPhone(it)
            }, errorMessage = if (viewModel.errorPhone.value.second.isNotEmpty())
                viewModel.errorPhone.value.second.last().validator.getErrorMessage(LocalContext.current)
            else "",
            keyboardType = KeyboardType.Number)
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_4x)))

        CommentTextFieldItem(title = stringResource(id = R.string.label_title_comment),
            value = viewModel.commentText.value,
            hint = stringResource(id = R.string.label_enter_your_comment_content),
            onValueChanged = {
                viewModel.setCommentText(it)
            }, errorMessage = if (viewModel.errorCommentText.value.second.isNotEmpty())
                viewModel.errorCommentText.value.second.last().validator.getErrorMessage(
                    LocalContext.current)
            else "",
            isLastField = true)
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_6x)))

        CaptchaContent(onCaptchaValueChanged = {
            //todo
        })
    }
}

@Composable
private fun CommentTextFieldItem(
    title: String,
    hint: String,
    value: String,
    onValueChanged: (String) -> Unit,
    errorMessage: String,
    isLastField: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
) {
    val focusManager = LocalFocusManager.current

    Text(text = title,
        style = MaterialTheme.typography.captionPrimary())

    TextField(
        value = value,
        onValueChange = { newValue -> onValueChanged(newValue) },
        modifier = Modifier
            .fillMaxWidth(),
        placeholder = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = hint,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.subtitle2TextSecondary()
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.background,
            unfocusedIndicatorColor = MaterialTheme.colors.divider()
        ),
        keyboardOptions = KeyboardOptions(imeAction = if (isLastField) ImeAction.Done else ImeAction.Next,
            keyboardType = keyboardType),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        isError = errorMessage.isNotEmpty(),
        singleLine = true
    )

    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_base)))

    ErrorText(
        visible =
        errorMessage.isNotEmpty(),
        errorMessage = errorMessage
    )
}

@Composable
private fun CaptchaContent(onCaptchaValueChanged: (String) -> Unit) {
    Text(text = stringResource(id = R.string.label_enter_captcha),
        style = MaterialTheme.typography.subtitle1TextPrimaryBold())
    // todo add captcha part
}

@Composable
private fun ProcessLoadingAndErrorState(
    input: PublicState?,
    onErrorDialogDismissed: () -> Unit,
) {
    val dialog = getErrorDialog(
        title = stringResource(id = R.string.msg_general_error_title),
        description = "",
        submitAction = {
            onErrorDialogDismissed()
        }
    )
    val loadingDialog = getLoadingDialog()

    if (input?.refreshing == true) {
        loadingDialog.show()
    } else {
        loadingDialog.dismiss()
        input?.message?.let { messageModel ->
            dialog.setDialogDetailMessage(messageModel.message).show()
        }
    }
}
