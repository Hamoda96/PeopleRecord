package com.hamoda.peoplerecord.domain.usecase

import com.hamoda.peoplerecord.domain.repository.UserRepository

// Use case for deleting a user by their ID from the repository.
// Calls the repository to remove a specific user.
class DeleteUserByIdUseCase(
    private val repository: UserRepository
) {
    // Invokes the delete user by ID operation asynchronously.
    suspend operator fun invoke(id: Int) {
        repository.deleteUserById(id)
    }
}