package com.hamoda.peoplerecord.domain.usecase

data class UserUseCases(
    val addUser: AddUserUseCase,
    val getUsers: GetUsersUseCase,
    val deleteUserById: DeleteUserByIdUseCase,
    val deleteAll: DeleteAllUsersUseCase
)