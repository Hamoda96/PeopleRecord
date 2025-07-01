package com.hamoda.peoplerecord.domain.repository

import com.hamoda.peoplerecord.data.local.UserEntity
import com.hamoda.peoplerecord.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun addUser(user: User)
    fun getAllUsers(): Flow<List<User>>
    suspend fun deleteUserById(userId: Int)
    suspend fun deleteAllUsers()
}