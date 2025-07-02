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
    /**
     * Adds a new user to the local database.
     */
    override suspend fun addUser(user: User) {
        userDao.insetUser(userEntity = user.toEntity())
    }

    /**
     * Returns a Flow that emits the list of all users from the local database.
     */
    override fun getAllUsers(): Flow<List<User>> {
        return userDao.getAllUsers().map { list -> list.map { it.toDomain() } }
    }

    /**
     * Deletes a user from the local database by their ID.
     */
    override suspend fun deleteUserById(userId: Int) {
        userDao.deleteUserById(userId = userId)
    }

    /**
     * Deletes all users from the local database.
     */
    override suspend fun deleteAllUsers() {
        userDao.deleteAllUsers()
    }
}