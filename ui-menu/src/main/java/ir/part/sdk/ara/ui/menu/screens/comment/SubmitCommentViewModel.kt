package ir.part.sdk.ara.ui.menu.screens.comment

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.part.sdk.ara.common.ui.view.ExceptionHelper
import ir.part.sdk.ara.common.ui.view.api.ObservableLoadingCounter
import ir.part.sdk.ara.common.ui.view.api.PublicState
import ir.part.sdk.ara.common.ui.view.api.UiMessageManager
import ir.part.sdk.ara.common.ui.view.api.collectAndChangeLoadingAndMessageStatus
import ir.part.sdk.ara.common.ui.view.utils.validation.ValidationResult
import ir.part.sdk.ara.domain.menu.entities.BodyComment
import ir.part.sdk.ara.domain.menu.entities.DataComment
import ir.part.sdk.ara.domain.menu.interactors.SubmitCommentRemote
import ir.part.sdk.ara.ui.menu.util.validation.ValidationField
import ir.part.sdk.ara.ui.menu.util.validation.validateWidget
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class SubmitCommentViewModel @Inject constructor(
    private val submitCommentRemote: SubmitCommentRemote,
    var exceptionHelper: ExceptionHelper,
) : ViewModel() {


    var uiMessageManager = UiMessageManager()
    var loadingState = ObservableLoadingCounter()

    val loadingAndMessageState: StateFlow<PublicState> = combine(
        loadingState.observable,
        uiMessageManager.message
    ) { refreshing, message ->
        PublicState(
            refreshing = refreshing,
            message = message
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = PublicState.Empty
    )

    var showSuccessDialog = mutableStateOf(false)

    val name = mutableStateOf("")
    val lastName = mutableStateOf("")
    val email = mutableStateOf("")
    val phone = mutableStateOf("")
    val commentText = mutableStateOf("")

    var errorName =
        mutableStateOf(Pair(ValidationField.PERSIAN_REQUIRED, listOf<ValidationResult>()))
    var errorLastName =
        mutableStateOf(Pair(ValidationField.PERSIAN_REQUIRED, listOf<ValidationResult>()))
    var errorEmail =
        mutableStateOf(Pair(ValidationField.PERSIAN_REQUIRED, listOf<ValidationResult>()))
    var errorPhone =
        mutableStateOf(Pair(ValidationField.PERSIAN_REQUIRED, listOf<ValidationResult>()))
    var errorCommentText =
        mutableStateOf(Pair(ValidationField.PERSIAN_REQUIRED, listOf<ValidationResult>()))


    private fun checkErrorName() {
        errorName.value = validateWidget(ValidationField.PERSIAN_REQUIRED, name.value)
    }

    private fun checkErrorLastName() {
        errorLastName.value = validateWidget(ValidationField.PERSIAN_REQUIRED, lastName.value)
    }

    private fun checkErrorEmail() {
        errorEmail.value = validateWidget(ValidationField.EMAIL, email.value)
    }

    private fun checkErrorPhone() {
        errorPhone.value = validateWidget(ValidationField.PHONE, phone.value)
    }

    private fun checkErrorCommentText() {
        errorCommentText.value = validateWidget(ValidationField.REQUIRED, commentText.value)
    }


    fun setName(newName: String) {
        name.value = newName
        checkErrorName()
    }

    fun setLastName(newLastName: String) {
        lastName.value = newLastName
        checkErrorLastName()
    }

    fun setEmail(newEmail: String) {
        email.value = newEmail
        checkErrorEmail()
    }

    fun setPhone(newPhone: String) {
        phone.value = newPhone
        checkErrorPhone()
    }

    fun setCommentText(newText: String) {
        commentText.value = newText
        checkErrorCommentText()
    }

    fun setSuccessDialogAsSeen() {
        showSuccessDialog.value = false
    }


    private fun isAllFieldValid(): Boolean {
        checkErrorName()
        checkErrorLastName()
        checkErrorPhone()
        checkErrorEmail()
        checkErrorCommentText()
        return errorPhone.value.second.isEmpty()
                && errorName.value.second.isEmpty()
                && errorLastName.value.second.isEmpty()
                && errorEmail.value.second.isEmpty()
                && errorCommentText.value.second.isEmpty()
    }

    fun sendComment(isCaptchaValid: Boolean, captchaToken: String, captchaValue: String) {
        if (isAllFieldValid()) {


            if (loadingState.count.toInt() == 0 && isCaptchaValid) {
                clearAllMessage()

                // send
                submitCommentRemote(SubmitCommentRemote.Param(BodyComment(
                    captchaValue = captchaValue,
                    captchaToken = captchaToken,
                    data = DataComment(
                        description = commentText.value,
                        email = email.value,
                        family = lastName.value,
                        mobile = phone.value,
                        name = name.value)
                ))).collectAndChangeLoadingAndMessageStatus(
                    coroutineScope = viewModelScope,
                    counter = loadingState,
                    exceptionHelper = exceptionHelper,
                    uiMessageManager = uiMessageManager
                ) { successful ->
                    showSuccessDialog.value = successful
                    if (successful) clearAllFieldValue()
                }
            }
        }
    }

    private fun clearAllFieldValue() {
        name.value = ""
        lastName.value = ""
        email.value = ""
        phone.value = ""
        commentText.value = ""
    }

    private fun clearAllMessage() {
        viewModelScope.launch {
            if (loadingState.count.toInt() == 0) {
                uiMessageManager.clearAllMessage()
            }
        }
    }

}