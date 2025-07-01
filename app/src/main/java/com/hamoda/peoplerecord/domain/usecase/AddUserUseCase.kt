package com.hamoda.peoplerecord.domain.usecase

import com.hamoda.peoplerecord.domain.model.User
import com.hamoda.peoplerecord.domain.repository.UserRepository

class AddUserUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(user: User) {
        repository.addUser(user)
    }
}