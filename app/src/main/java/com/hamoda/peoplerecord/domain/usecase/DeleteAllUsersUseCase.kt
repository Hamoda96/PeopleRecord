package com.hamoda.peoplerecord.domain.usecase

import com.hamoda.peoplerecord.domain.repository.UserRepository

// Use case for deleting all users from the repository.
// Calls the repository to remove all user records.
class DeleteAllUsersUseCase(
    private val repository: UserRepository
) {
    // Invokes the delete all users operation asynchronously.
    suspend operator fun invoke() {
        repository.deleteAllUsers()
    }
}