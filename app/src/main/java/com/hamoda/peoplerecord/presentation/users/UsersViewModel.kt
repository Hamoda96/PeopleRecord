package com.hamoda.peoplerecord.presentation.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamoda.peoplerecord.domain.model.User
import com.hamoda.peoplerecord.domain.usecase.UserUseCases
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

// ViewModel for managing the user list screen state and business logic.
// Handles loading users, user actions, and navigation events.
class UsersViewModel(
    private val userUseCases: UserUseCases
) : ViewModel() {

    // Holds the current list of users.
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users.asStateFlow()
    // Indicates whether a loading operation is in progress.
    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    // Emits navigation events to the UI layer.
    private val _navigationEvent = MutableSharedFlow<UsersSideEffect>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    // Initializes the ViewModel by loading users.
    init {
        viewModelScope.launch {
            _loading.value = true
            delay(500)
            _loading.value = false
            onEvent(UsersEvent.GetUsers)
        }
    }

    // Handles user actions/events such as loading, deleting, or navigating.
    fun onEvent(event: UsersEvent) {
        viewModelScope.launch {
            when (event) {
                is UsersEvent.GetUsers -> {
                    userUseCases.getUsers().onEach { _users.value = it }.launchIn(this)
                }

                is UsersEvent.DeleteUser -> {
                    userUseCases.deleteUserById(event.id)
                }

                is UsersEvent.DeleteAllUsers -> {
                    userUseCases.deleteAll()
                }

                UsersEvent.AddUser -> {
                    _navigationEvent.emit(UsersSideEffect.NavigateToAddUser)
                }
            }
        }
    }
}

// Represents navigation events that can be emitted by the UsersViewModel.
sealed class UsersSideEffect {
    // Navigation event to navigate to the add user screen.
    object NavigateToAddUser : UsersSideEffect()
}