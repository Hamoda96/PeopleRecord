package com.hamoda.peoplerecord.domain.repository

import com.hamoda.peoplerecord.domain.model.User
import kotlinx.coroutines.flow.Flow

// Repository interface for user data operations.
// Defines methods for adding, retrieving, and deleting users from the data source.
interface UserRepository {
    // Adds a new user to the data source.
    suspend fun addUser(user: User)
    // Returns a Flow stream of all users from the data source.
    fun getAllUsers(): Flow<List<User>>
    // Deletes a user by their unique ID.
    suspend fun deleteUserById(userId: Int)
    // Deletes all users from the data source.
    suspend fun deleteAllUsers()
}