package ir.part.sdk.ara.ui.user.screens.changePassword

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import ir.part.app.merat.ui.user.R
import ir.part.sdk.ara.base.util.TasksName
import ir.part.sdk.ara.common.ui.view.UserTextField
import ir.part.sdk.ara.common.ui.view.api.PublicState
import ir.part.sdk.ara.common.ui.view.common.SubmitActionContent
import ir.part.sdk.ara.common.ui.view.common.TopAppBarContent
import ir.part.sdk.ara.common.ui.view.rememberFlowWithLifecycle
import ir.part.sdk.ara.common.ui.view.theme.h6BoldTextPrimary
import ir.part.sdk.ara.common.ui.view.utils.dialog.DimensionResource
import ir.part.sdk.ara.common.ui.view.utils.dialog.getErrorDialog
import ir.part.sdk.ara.common.ui.view.utils.dialog.getLoadingDialog
import ir.part.sdk.ara.common.ui.view.utils.dialog.getSuccessDialog
import ir.part.sdk.ara.common.ui.view.utils.validation.ValidationField
import ir.part.sdk.ara.common.ui.view.utils.validation.ValidationResult
import ir.part.sdk.ara.common.ui.view.utils.validation.validateWidget
import ir.part.sdk.ara.ui.shared.feature.screens.task.TasksManagerViewModel


@Composable
fun ChangePasswordScreen(
    changePasswordViewModel: ChangePasswordViewModel,
    onNavigateUp: () -> Unit,
    tasksManagerViewModel: TasksManagerViewModel
) {

    val taskLoadingErrorState =
        rememberFlowWithLifecycle(flow = tasksManagerViewModel.loadingAndMessageState).collectAsState(
            initial = PublicState.Empty
        )

    var isChangePassword: Boolean by remember {
        mutableStateOf(false)
    }
    isChangePassword = changePasswordViewModel.isChangePassword.value

    if (isChangePassword) {
        getSuccessDialog(
            title = stringResource(id = R.string.label_change_password),
            description = stringResource(id = R.string.msg_change_Success_password)
        ) {
            if (tasksManagerViewModel.getDoingTaskName == TasksName.CHANG_PASS) {
                tasksManagerViewModel.done()
                changePasswordViewModel.isChangePassword.value = false

            } else {
                onNavigateUp.invoke()
                changePasswordViewModel.isChangePassword.value = false
            }
        }.show()
    }
    val changePassLoadingErrorState = changePasswordViewModel.let {
        rememberFlowWithLifecycle(flow = it.loadingAndMessageState).collectAsState(initial = PublicState.Empty)
    }

    ProcessLoadingAndErrorState(input = changePassLoadingErrorState.value) {
        changePasswordViewModel.loadingAndMessageState.value.message = null
    }
    ProcessLoadingAndErrorState(input = taskLoadingErrorState.value) {
        tasksManagerViewModel.loadingAndMessageState.value.message = null
    }
    val scrollState = rememberScrollState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.surface,
                elevation = dimensionResource(id = R.dimen.spacing_half_base)
            ) {
                TopAppBarContent(
                    title = stringResource(id = R.string.label_change_password),
                    onNavigateUp = {
                        onNavigateUp()
                    })
            }
        }, bottomBar = {
            SubmitActionContent(
                onSubmitClicked = {
                    if (changePasswordViewModel.isValidationField()) {
                        changePasswordViewModel.getChangePasswordRemote()
                    }
                },
                buttonText = R.string.label_change_password
            )
        }) { innerPadding ->
        Box(
            Modifier
                .padding(innerPadding)
                .verticalScroll(scrollState)
        ) {
            ShowPasswordContent(changePasswordViewModel)
        }

    }
}

@Composable
private fun ShowPasswordContent(changePasswordViewModel: ChangePasswordViewModel) {

    var currentPassword: String? by remember {
        mutableStateOf(null)
    }

    var newPassword: String? by remember {
        mutableStateOf(null)
    }

    var reNewPassword: String? by remember {
        mutableStateOf(null)
    }

    currentPassword = changePasswordViewModel.currentPassword.value
    newPassword = changePasswordViewModel.newPassword.value
    reNewPassword = changePasswordViewModel.reNewPassword.value

    Column {
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_9x)))
        Text(
            modifier = Modifier
                .padding(horizontal = dimensionResource(id = DimensionResource.spacing_4x)),
            text = stringResource(id = R.string.label_change_password),
            style = MaterialTheme.typography.h6BoldTextPrimary()
        )

        ShowCurrentPassword(
            newPassword = newPassword ?: "",
            currentPassword = currentPassword ?: "",
            onChangeCurrentPassword = { currentPasswordText ->
                changePasswordViewModel.setCurrentPassword(currentPasswordText)
            },
            errorCurrentPassword = changePasswordViewModel.errorValuePassword,
            checkNewPassword = {
                changePasswordViewModel.setErrorNewPassword(
                    validateWidget(
                        ValidationField.NEW_PASSWORD,
                        value = newPassword ?: "",
                        newValue = currentPassword
                    )
                )
            }
        )
        ShowNewPassword(
            reNewPassword = reNewPassword ?: "",
            newPassword = newPassword ?: "",
            onChangeNewPassword = { newPasswordText ->
                changePasswordViewModel.setNewPassword(
                    newPasswordText = newPasswordText,
                    passwordText = currentPassword
                )
            },
            errorNewPassword = changePasswordViewModel.errorValueNewPassword,
            checkReNewPassword = {
                changePasswordViewModel.setErrorReNewPassword(
                    validateWidget(
                        ValidationField.RE_NEW_PASSWORD,
                        reNewPassword ?: "",
                        newValue = newPassword
                    )
                )
            }
        )
        ShowReNewPassword(
            reNewPassword = reNewPassword ?: "",
            onChangeReNewPassword = { reNewPasswordText ->
                changePasswordViewModel.setReNewPassword(
                    reNewPasswordText = reNewPasswordText,
                    newPasswordText = newPassword
                )
            },
            errorReNewPassword = changePasswordViewModel.errorValueReNewPassword
        )

    }
}

@Composable
private fun ShowCurrentPassword(
    currentPassword: String,
    checkNewPassword: () -> Unit,
    newPassword: String,
    onChangeCurrentPassword: (String) -> Unit,
    errorCurrentPassword: MutableState<Pair<ValidationField, List<ValidationResult>>>
) {
    if (newPassword.isNotEmpty()) {
        checkNewPassword.invoke()
    }

    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_3x)))
    UserTextField(
        hint = stringResource(id = R.string.label_password),
        value = currentPassword,
        onValueChanged = { currentPasswordText ->
            onChangeCurrentPassword(currentPasswordText)
        },
        errorMessage = if (errorCurrentPassword.value.second.isNotEmpty())
            errorCurrentPassword.value.second.last().validator.getErrorMessage(LocalContext.current) else "",
        trailingPasswordIcon = true,
        painter = painterResource(id = R.drawable.ic_lock),
        keyboardType = KeyboardType.Password
    )
}

@Composable
private fun ShowNewPassword(
    newPassword: String,
    reNewPassword: String,
    checkReNewPassword: () -> Unit,
    onChangeNewPassword: (String) -> Unit,
    errorNewPassword: MutableState<Pair<ValidationField, List<ValidationResult>>>
) {
    if (reNewPassword.isNotEmpty()) {
        checkReNewPassword.invoke()
    }
    UserTextField(
        hint = stringResource(id = R.string.label_new_password),
        value = newPassword,
        onValueChanged = { newPasswordText ->
            onChangeNewPassword(newPasswordText)
        },
        errorMessage = if (errorNewPassword.value.second.isNotEmpty())
            errorNewPassword.value.second.last().validator.getErrorMessage(LocalContext.current) else "",
        trailingPasswordIcon = true,
        painter = painterResource(id = R.drawable.ic_lock),
        keyboardType = KeyboardType.Password
    )
}

@Composable
private fun ShowReNewPassword(
    reNewPassword: String,
    onChangeReNewPassword: (String) -> Unit,
    errorReNewPassword: MutableState<Pair<ValidationField, List<ValidationResult>>>
) {
    UserTextField(
        hint = stringResource(id = R.string.label_re_new_password),
        value = reNewPassword,
        onValueChanged = { reNewPasswordText ->
            onChangeReNewPassword(reNewPasswordText)
        },
        errorMessage = if (errorReNewPassword.value.second.isNotEmpty())
            errorReNewPassword.value.second.last().validator.getErrorMessage(LocalContext.current) else "",
        trailingPasswordIcon = true,
        painter = painterResource(id = R.drawable.ic_lock),
        keyboardType = KeyboardType.Password
    )
}


@Composable
private fun ProcessLoadingAndErrorState(input: PublicState?, onErrorDialogDismissed: () -> Unit) {
    val loadingDialog = getLoadingDialog()
    val errorDialog = getErrorDialog(
        title = stringResource(id = R.string.label_warning_title_dialog),
        description = "",
        submitAction = {
            onErrorDialogDismissed()
        }
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
