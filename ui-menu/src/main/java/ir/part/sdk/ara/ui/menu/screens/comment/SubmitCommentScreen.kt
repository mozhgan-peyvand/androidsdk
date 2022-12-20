package ir.part.sdk.ara.ui.menu.screens.comment

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import ir.part.sdk.ara.common.ui.view.api.PublicState
import ir.part.sdk.ara.common.ui.view.common.CustomTextField
import ir.part.sdk.ara.common.ui.view.common.ProcessLoadingAndErrorState
import ir.part.sdk.ara.common.ui.view.common.SubmitActionContent
import ir.part.sdk.ara.common.ui.view.common.TopAppBarContent
import ir.part.sdk.ara.common.ui.view.divider
import ir.part.sdk.ara.common.ui.view.rememberFlowWithLifecycle
import ir.part.sdk.ara.common.ui.view.theme.*
import ir.part.sdk.ara.common.ui.view.utils.dialog.getSuccessDialog
import ir.part.sdk.ara.common.ui.view.utils.validation.ValidationField
import ir.part.sdk.ara.common.ui.view.utils.validation.validateWidget
import ir.part.sdk.ara.ui.menu.R
import ir.part.sdk.ara.ui.shared.feature.screens.captcha.Captcha
import ir.part.sdk.ara.ui.shared.feature.screens.captcha.CaptchaViewModel

@Composable
fun SubmitCommentScreen(
    onNavigateUp: () -> Unit, submitCommentViewModel: SubmitCommentViewModel,
    captchaViewModel: CaptchaViewModel,
) {

    var showSuccessDialog by remember {
        mutableStateOf(false)
    }
    showSuccessDialog = submitCommentViewModel.showSuccessDialog.value

    val loadingErrorState =
        rememberFlowWithLifecycle(flow = submitCommentViewModel.loadingAndMessageState).collectAsState(
            initial = PublicState.Empty
        )
    ProcessLoadingAndErrorState(
        loadingErrorState.value,
        removeErrorsFromStates = {
            submitCommentViewModel.clearAllMessage()
        })

    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.surface,
                elevation = dimensionResource(id = R.dimen.spacing_half_base)
            ) {
                TopAppBarContent(title = stringResource(id = R.string.ara_label_submit_comments),
                    onNavigateUp = {
                        onNavigateUp()
                    })
            }
        }, bottomBar = {
            SubmitActionContent(
                onSubmitClicked = {

                    captchaViewModel.setError(
                        validateWidget(
                            ValidationField.CAPTCHA,
                            captchaViewModel.captchaValue.value
                        )
                    )

                    submitCommentViewModel.sendComment(
                        isCaptchaValid = captchaViewModel.errorCaptchaValue.value.second.isEmpty(),
                        captchaToken = captchaViewModel.captchaViewState.value?.token ?: "",
                        captchaValue = captchaViewModel.captchaValue.value
                    )

                },
                buttonText = R.string.ara_label_send_comment
            )

        }) { innerPadding ->
        Box(Modifier.padding(innerPadding)) {
            CommentContent(viewModel = submitCommentViewModel,
                showSuccessDialog = showSuccessDialog,
                captchaViewModel = captchaViewModel,
                onSuccessDialogConfirmed = {
                    submitCommentViewModel.setSuccessDialogAsSeen()
                    onNavigateUp()
                })
        }
    }
}

@Composable
private fun CommentContent(
    viewModel: SubmitCommentViewModel,
    showSuccessDialog: Boolean,
    onSuccessDialogConfirmed: () -> Unit,
    captchaViewModel: CaptchaViewModel,
) {
    if (showSuccessDialog) {
        getSuccessDialog(
            title = stringResource(id = R.string.ara_label_send_comment),
            description = stringResource(id = R.string.ara_msg_submit_comment_success)
        ) {
            onSuccessDialogConfirmed()

        }.show()
    }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .padding(horizontal = dimensionResource(id = R.dimen.spacing_4x))
    ) {

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_6x)))
        Text(
            text = stringResource(id = R.string.ara_label_submit_comments),
            style = MaterialTheme.typography.h6Bold()
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_5x)))
        Text(
            text = stringResource(id = R.string.ara_label_description_comment),
            style = MaterialTheme.typography.subtitle2TextSecondary()
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_8x)))

        CommentTextFieldItem(
            title = stringResource(id = R.string.ara_label_name),
            value = viewModel.name.value,
            hint = stringResource(id = R.string.ara_label_enter_your_name),
            onValueChanged = {
                viewModel.setName(it)
            }, errorMessage = if (viewModel.errorName.value.second.isNotEmpty())
                viewModel.errorName.value.second.last().validator.getErrorMessage(LocalContext.current)
            else ""
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_4x)))

        CommentTextFieldItem(
            title = stringResource(id = R.string.ara_label_last_name),
            value = viewModel.lastName.value,
            hint = stringResource(id = R.string.ara_label_enter_your_last_name),
            onValueChanged = {
                viewModel.setLastName(it)
            }, errorMessage = if (viewModel.errorLastName.value.second.isNotEmpty())
                viewModel.errorLastName.value.second.last().validator.getErrorMessage(LocalContext.current)
            else ""
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_4x)))


        CommentTextFieldItem(
            title = stringResource(id = R.string.ara_label_email_persian),
            value = viewModel.email.value,
            hint = stringResource(id = R.string.ara_label_enter_your_email),
            onValueChanged = {
                viewModel.setEmail(it)
            }, errorMessage = if (viewModel.errorEmail.value.second.isNotEmpty())
                viewModel.errorEmail.value.second.last().validator.getErrorMessage(LocalContext.current)
            else "",
            keyboardType = KeyboardType.Email
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_4x)))


        CommentTextFieldItem(
            title = stringResource(id = R.string.ara_label_phone_number_with_colon),
            value = viewModel.phone.value,
            hint = stringResource(id = R.string.ara_label_enter_your_phone_number),
            onValueChanged = {
                viewModel.setPhone(it)
            }, errorMessage = if (viewModel.errorPhone.value.second.isNotEmpty())
                viewModel.errorPhone.value.second.last().validator.getErrorMessage(LocalContext.current)
            else "",
            keyboardType = KeyboardType.Number
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_4x)))

        CommentTextFieldItem(
            title = stringResource(id = R.string.ara_label_title_comment),
            value = viewModel.commentText.value,
            hint = stringResource(id = R.string.ara_label_enter_your_comment_content),
            onValueChanged = {
                viewModel.setCommentText(it)
            }, errorMessage = if (viewModel.errorCommentText.value.second.isNotEmpty())
                viewModel.errorCommentText.value.second.last().validator.getErrorMessage(
                    LocalContext.current
                )
            else ""
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_6x)))

        Text(
            text = stringResource(id = R.string.ara_label_enter_captcha),
            style = MaterialTheme.typography.subtitle1BoldTextPrimary()
        )
        Captcha(
            captchaViewModel = captchaViewModel
        )
    }
}

@Composable
private fun CommentTextFieldItem(
    title: String,
    hint: String,
    value: String,
    onValueChanged: (String) -> Unit,
    errorMessage: String,
    keyboardType: KeyboardType = KeyboardType.Text,
) {
    Text(
        text = title,
        style = MaterialTheme.typography.captionTextPrimary()
    )

    CustomTextField(
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
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
            keyboardType = keyboardType
        ),
        isError = errorMessage.isNotEmpty(),
        singleLine = true
    )

    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_base)))

    ErrorText(
        visible = errorMessage.isNotEmpty(),
        errorMessage = errorMessage
    )
}

