package com.hamoda.peoplerecord.presentation.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamoda.peoplerecord.domain.model.User
import com.hamoda.peoplerecord.domain.usecase.UserUseCases
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class UsersViewModel(
    private val userUseCases: UserUseCases
) : ViewModel() {

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users.asStateFlow()
    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<UsersSideEffect>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    init {
        viewModelScope.launch {
            _loading.value = true
            delay(500)
            _loading.value = false
            onEvent(UsersEvent.GetUsers)
        }
    }

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

    private fun observeUsers() {
        viewModelScope.launch {
            userUseCases.getUsers()
                .onEach { _users.value = it }
                .launchIn(this)
        }
    }

    private fun deleteUserById(id: Int) {
        viewModelScope.launch {
            _loading.value = true
            userUseCases.deleteUserById(id)
            _loading.value = false
        }
    }

    private fun deleteAllUsers() {
        viewModelScope.launch {
            _loading.value = true
            userUseCases.deleteAll()
            _loading.value = false
        }
    }
}

sealed class UsersSideEffect {
    object NavigateToAddUser : UsersSideEffect()
}