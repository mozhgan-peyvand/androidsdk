package ir.part.sdk.ara.ui.user.screens.changePassword

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import ir.part.app.merat.ui.user.R
import ir.part.sdk.ara.base.util.TasksName
import ir.part.sdk.ara.common.ui.view.api.PublicState
import ir.part.sdk.ara.common.ui.view.common.SubmitActionContent
import ir.part.sdk.ara.common.ui.view.common.TopAppBarContent
import ir.part.sdk.ara.common.ui.view.primaryVariant
import ir.part.sdk.ara.common.ui.view.rememberFlowWithLifecycle
import ir.part.sdk.ara.common.ui.view.theme.ErrorText
import ir.part.sdk.ara.common.ui.view.theme.h6BoldPrimary
import ir.part.sdk.ara.common.ui.view.theme.subtitle1TextSecondary
import ir.part.sdk.ara.common.ui.view.utils.dialog.DimensionResource
import ir.part.sdk.ara.common.ui.view.utils.dialog.getInfoDialog
import ir.part.sdk.ara.common.ui.view.utils.dialog.getLoadingDialog
import ir.part.sdk.ara.common.ui.view.utils.validation.ValidationField
import ir.part.sdk.ara.common.ui.view.utils.validation.validateWidget
import ir.part.sdk.ara.ui.shared.feature.screens.task.TasksManagerViewModel


@Composable
fun ChangePasswordScreen(
    changePasswordViewModel: ChangePasswordViewModel? = null,
    onNavigateUp: () -> Unit,
    tasksManagerViewModel: TasksManagerViewModel
) {

    val taskLoadingErrorState =
        rememberFlowWithLifecycle(flow = tasksManagerViewModel.loadingAndMessageState).collectAsState(
            initial = PublicState.Empty
        )

    val changePassLoadingErrorState = changePasswordViewModel?.let {
        rememberFlowWithLifecycle(flow = it.loadingAndMessageState).collectAsState(initial = PublicState.Empty)
    }

    ProcessLoadingAndErrorState(input = changePassLoadingErrorState?.value)
    ProcessLoadingAndErrorState(input = taskLoadingErrorState.value)

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
                    if (changePasswordViewModel?.isValidationField() == true) {
                        changePasswordViewModel.getChangePasswordRemote(onSuccess = {
                            if (tasksManagerViewModel.getDoingTaskName == TasksName.CHANG_PASS) {
                                tasksManagerViewModel.done()
                            }
                        })
                    }
                },
                buttonText = R.string.label_change_password
            )
        }) { innerPadding ->
        Box(Modifier.padding(innerPadding)) {
            ShowPasswordContent(changePasswordViewModel)
        }

    }
}

@Composable
private fun ShowPasswordContent(changePasswordViewModel: ChangePasswordViewModel? = null) {
    Column {
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_9x)))
        Text(
            modifier = Modifier
                .padding(horizontal = dimensionResource(id = DimensionResource.spacing_4x)),
            text = stringResource(id = R.string.label_change_password),
            style = MaterialTheme.typography.h6BoldPrimary()
        )

        changePasswordViewModel?.let {
            ShowCurrentPassword(changePasswordViewModel = it)
            ShowNewPassword(changePasswordViewModel = it)
            ShowReNewPassword(changePasswordViewModel = it)
        }
    }
}

@Composable
private fun ShowCurrentPassword(changePasswordViewModel: ChangePasswordViewModel) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_3x)))
    TextField(
        value = changePasswordViewModel.currentPassword.value,
        onValueChange = { inputValue ->
            changePasswordViewModel.currentPassword.value = inputValue
            changePasswordViewModel.setErrorPassword(
                validateWidget(
                    ValidationField.PASSWORD,
                    inputValue
                )
            )
            if (changePasswordViewModel.newPassword.value.isNotEmpty()) {
                changePasswordViewModel.setErrorNewPassword(
                    validateWidget(
                        ValidationField.NEW_PASSWORD,
                        changePasswordViewModel.newPassword.value,
                        newValue = inputValue
                    )
                )
            }
        },
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_lock),
                contentDescription = "",
                tint = MaterialTheme.colors.primaryVariant()
            )
        },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val image = if (passwordVisible)
                Icons.Filled.VisibilityOff
            else Icons.Filled.Visibility

            // Please provide localized description for accessibility services
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(imageVector = image, "")
            }
        },
        placeholder = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.label_password),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.subtitle1TextSecondary()
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(
                dimensionResource(id = DimensionResource.spacing_4x)
            ),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White,
            unfocusedIndicatorColor = Color.LightGray
        ),
        textStyle = MaterialTheme.typography.subtitle1,
        singleLine = true,
        maxLines = 1,
        keyboardActions = KeyboardActions {},
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        isError = changePasswordViewModel.errorValuePassword.value.second.isNotEmpty()
    )
    ErrorText(
        visible = changePasswordViewModel.errorValuePassword.value.second.isNotEmpty(),
        errorMessage = if (changePasswordViewModel.errorValuePassword.value.second.isNotEmpty())
            changePasswordViewModel.errorValuePassword.value.second.last().validator.getErrorMessage(
                LocalContext.current
            ) else ""
    )
}

@Composable
private fun ShowNewPassword(changePasswordViewModel: ChangePasswordViewModel) {

    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    TextField(
        value = changePasswordViewModel.newPassword.value,
        onValueChange = { inputValue ->
            changePasswordViewModel.newPassword.value = inputValue
            changePasswordViewModel.setErrorNewPassword(
                validateWidget(
                    ValidationField.NEW_PASSWORD,
                    inputValue,
                    newValue = changePasswordViewModel.currentPassword.value
                )
            )
            if (changePasswordViewModel.reNewPassword.value.isNotEmpty())
                changePasswordViewModel.setErrorReNewPassword(
                    validateWidget(
                        ValidationField.RE_NEW_PASSWORD,
                        changePasswordViewModel.reNewPassword.value,
                        newValue = inputValue
                    )
                )
        },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val image = if (passwordVisible)
                Icons.Filled.VisibilityOff
            else Icons.Filled.Visibility

            // Please provide localized description for accessibility services
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(imageVector = image, "")
            }
        },
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_lock),
                contentDescription = "",
                tint = MaterialTheme.colors.primaryVariant()
            )
        },
        placeholder = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.label_new_password),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.subtitle1TextSecondary()
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(
                dimensionResource(id = DimensionResource.spacing_4x)
            ),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White,
            unfocusedIndicatorColor = Color.LightGray
        ),
        textStyle = MaterialTheme.typography.subtitle1,
        singleLine = true,
        maxLines = 1,
        keyboardActions = KeyboardActions {},
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        isError = changePasswordViewModel.errorValueNewPassword.value.second.isNotEmpty()
    )
    ErrorText(
        visible = changePasswordViewModel.errorValueNewPassword.value.second.isNotEmpty(),
        errorMessage = if (changePasswordViewModel.errorValueNewPassword.value.second.isNotEmpty())
            changePasswordViewModel.errorValueNewPassword.value.second.last().validator.getErrorMessage(
                LocalContext.current
            ) else ""
    )

}

@Composable
private fun ShowReNewPassword(changePasswordViewModel: ChangePasswordViewModel) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    TextField(
        value = changePasswordViewModel.reNewPassword.value,
        onValueChange = { inputValue ->
            changePasswordViewModel.reNewPassword.value = inputValue
            changePasswordViewModel.setErrorReNewPassword(
                validateWidget(
                    ValidationField.RE_NEW_PASSWORD,
                    inputValue,
                    newValue = changePasswordViewModel.newPassword.value
                )
            )
        },
        placeholder = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.label_re_new_password),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.subtitle1TextSecondary()
            )
        },
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_lock),
                contentDescription = "",
                tint = MaterialTheme.colors.primaryVariant()
            )
        },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val image = if (passwordVisible)
                Icons.Filled.VisibilityOff
            else Icons.Filled.Visibility

            // Please provide localized description for accessibility services
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(imageVector = image, "")
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(
                dimensionResource(id = DimensionResource.spacing_4x)
            ),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White,
            unfocusedIndicatorColor = Color.LightGray
        ),
        textStyle = MaterialTheme.typography.subtitle1,
        singleLine = true,
        maxLines = 1,
        keyboardActions = KeyboardActions {},
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        isError = changePasswordViewModel.errorValueReNewPassword.value.second.isNotEmpty()
    )
    ErrorText(
        visible = changePasswordViewModel.errorValueReNewPassword.value.second.isNotEmpty(),
        errorMessage = if (changePasswordViewModel.errorValueReNewPassword.value.second.isNotEmpty())
            changePasswordViewModel.errorValueReNewPassword.value.second.last().validator.getErrorMessage(
                LocalContext.current
            ) else ""
    )
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
