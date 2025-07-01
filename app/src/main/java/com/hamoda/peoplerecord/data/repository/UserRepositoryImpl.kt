package com.hamoda.peoplerecord.data.repository

import com.hamoda.peoplerecord.data.local.UserDao
import com.hamoda.peoplerecord.data.model.toDomain
import com.hamoda.peoplerecord.data.model.toEntity
import com.hamoda.peoplerecord.domain.model.User
import com.hamoda.peoplerecord.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepositoryImpl(
    private val userDao: UserDao
) : UserRepository {
    override suspend fun addUser(user: User) {
        userDao.insetUser(userEntity = user.toEntity())
    }

    override fun getAllUsers(): Flow<List<User>> {
        return userDao.getAllUsers().map { list -> list.map { it.toDomain() } }
    }

    override suspend fun deleteUserById(userId: Int) {
        userDao.deleteUserById(userId = userId)
    }

    override suspend fun deleteAllUsers() {
        userDao.deleteAllUsers()
    }
}