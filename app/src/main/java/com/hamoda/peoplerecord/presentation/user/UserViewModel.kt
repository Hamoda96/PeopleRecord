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

// ViewModel for managing user-related UI state and business logic.
// Handles user input validation, add user operations, and navigation events.
class UserViewModel(
    private val userUseCases: UserUseCases
) : ViewModel() {

    // Emits navigation events to the UI layer.
    private val _navigationEvent = MutableSharedFlow<UserSideEffect>()
    val navigationEvent = _navigationEvent.asSharedFlow()
    // Represents whether a user is being added (for loading state).
    private val _isAdding = MutableStateFlow(false)
    val isAdding: StateFlow<Boolean> = _isAdding.asStateFlow()

    // Holds the current user validation errors.
    var userValidationError by mutableStateOf(UserValidationError())
        private set

    // Indicates if the save user action has been triggered.
    var hasSaveUser by mutableStateOf(false)
        private set

    // Validates and adds a user. Emits navigation event on success.
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

    // Validates user input and updates validation errors.
    fun validateUserError(user: User) {
        val validationUser = validateUserInput(
            name = user.name,
            age = user.age.toString(),
            jobTitle = user.jobTitle,
            gender = user.gender
        )

        userValidationError = validationUser
    }

    // Validates a specific user field and returns an error message if invalid.
    fun validateUserField(field: String, value: String): String? {
        return when (field) {
            "name" -> validateUserInput(value, "1", "12345", "Male").nameError
            "age" -> validateUserInput("Valid Name", value, "12345", "Male").ageError
            "jobTitle" -> validateUserInput("Valid Name", "1", value, "Male").jobTitleError
            else -> null
        }
    }

    // Clears the name validation error.
    fun clearNameError() {
        userValidationError = userValidationError.copy(nameError = null)
    }

    // Clears the age validation error.
    fun clearAgeError() {
        userValidationError = userValidationError.copy(ageError = null)
    }

    // Clears the job title validation error.
    fun clearJobTitleError() {
        userValidationError = userValidationError.copy(jobTitleError = null)
    }

    // Clears the gender validation error.
    fun clearGenderError() {
        userValidationError = userValidationError.copy(genderError = null)
    }

    // Triggers navigation to the user list screen.
    fun skipToUsers() {
        viewModelScope.launch {
            _navigationEvent.emit(UserSideEffect.NavigateToUserList)
        }
    }
}

// Represents a navigation event that can be emitted by the ViewModel.
sealed class UserSideEffect {
    // Navigation event to navigate to the user list screen.
    object NavigateToUserList : UserSideEffect()
}