package com.hamoda.peoplerecord.domain.usecase

// Aggregates all user-related use cases for easy access in the application.
data class UserUseCases(
    val addUser: AddUserUseCase,
    val getUsers: GetUsersUseCase,
    val deleteUserById: DeleteUserByIdUseCase,
    val deleteAll: DeleteAllUsersUseCase
)