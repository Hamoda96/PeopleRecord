package com.hamoda.peoplerecord.presentation.users

sealed interface UsersEvent {
    object GetUsers : UsersEvent
    data class DeleteUser(val id: Int) : UsersEvent
    object DeleteAllUsers : UsersEvent
    object AddUser : UsersEvent
}