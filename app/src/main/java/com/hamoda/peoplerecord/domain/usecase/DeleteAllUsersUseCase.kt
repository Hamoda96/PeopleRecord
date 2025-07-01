package com.hamoda.peoplerecord.domain.usecase

import com.hamoda.peoplerecord.domain.repository.UserRepository

class DeleteAllUsersUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke() {
        repository.deleteAllUsers()
    }
}