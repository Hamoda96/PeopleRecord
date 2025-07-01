package com.hamoda.peoplerecord.domain.usecase

import com.hamoda.peoplerecord.domain.model.User
import com.hamoda.peoplerecord.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class GetUsersUseCase(
    private val repository: UserRepository
) {
    operator fun invoke(): Flow<List<User>> {
        return repository.getAllUsers()
    }
}