package com.hamoda.peoplerecord.presentation.user

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamoda.peoplerecord.domain.model.User
import com.hamoda.peoplerecord.domain.model.UserValidationError
import com.hamoda.peoplerecord.domain.model.validateUserInput
import com.hamoda.peoplerecord.domain.usecase.UserUseCases
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserViewModel(
    private val userUseCases: UserUseCases
) : ViewModel() {

    private val _navigationEvent = MutableSharedFlow<UserSideEffect>()
    val navigationEvent = _navigationEvent.asSharedFlow()
    private val _isAdding = MutableStateFlow(false)
    val isAdding: StateFlow<Boolean> = _isAdding.asStateFlow()

    var userValidationError by mutableStateOf(UserValidationError())
        private set

    var hasSaveUser by mutableStateOf(false)
        private set

    fun addUser(user: User) {
        hasSaveUser = true

        val validationUser = validateUserInput(
            name = user.name,
            age = user.age.toString(),
            jobTitle = user.jobTitle,
            gender = user.gender
        )

        userValidationError = validationUser

        if (validationUser.isValid) {
            viewModelScope.launch {
                _isAdding.value = true
                delay(500)
                userUseCases.addUser(user)
                _isAdding.value = false

                _navigationEvent.emit(UserSideEffect.NavigateToUserList)
            }
        }
    }

    fun validateUserError(user: User) {
        val validationUser = validateUserInput(
            name = user.name,
            age = user.age.toString(),
            jobTitle = user.jobTitle,
            gender = user.gender
        )

        userValidationError = validationUser
    }

    fun validateUserField(field: String, value: String): String? {
        return when (field) {
            "name" -> validateUserInput(value, "1", "12345", "Male").nameError
            "age" -> validateUserInput("Valid Name", value, "12345", "Male").ageError
            "jobTitle" -> validateUserInput("Valid Name", "1", value, "Male").jobTitleError
            else -> null
        }
    }

    fun clearNameError() {
        userValidationError = userValidationError.copy(nameError = null)
    }

    fun clearAgeError() {
        userValidationError = userValidationError.copy(ageError = null)
    }

    fun clearJobTitleError() {
        userValidationError = userValidationError.copy(jobTitleError = null)
    }

    fun clearGenderError() {
        userValidationError = userValidationError.copy(genderError = null)
    }

    fun skipToUsers() {
        viewModelScope.launch {
            _navigationEvent.emit(UserSideEffect.NavigateToUserList)
        }
    }
}

sealed class UserSideEffect {
    object NavigateToUserList : UserSideEffect()
}