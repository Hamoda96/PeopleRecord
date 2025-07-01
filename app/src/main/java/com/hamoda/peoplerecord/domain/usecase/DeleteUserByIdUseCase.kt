package com.hamoda.peoplerecord.domain.usecase

import com.hamoda.peoplerecord.domain.repository.UserRepository

class DeleteUserByIdUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(id: Int) {
        repository.deleteUserById(id)
    }
}