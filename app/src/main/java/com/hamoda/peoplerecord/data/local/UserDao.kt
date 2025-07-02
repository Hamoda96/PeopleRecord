package com.hamoda.peoplerecord.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    /**
     * Inserts a user entity into the database. Replaces if the user already exists.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insetUser(userEntity: UserEntity)

    /**
     * Returns a flow of all user entities from the database, ordered by ID descending.
     */
    @Query("SELECT * FROM users ORDER BY id DESC")
    fun getAllUsers(): Flow<List<UserEntity>>

    /**
     * Deletes a user from the database by their ID.
     */
    @Query("DELETE FROM users WHERE id = :userId")
    suspend fun deleteUserById(userId: Int)

    /**
     * Deletes all users from the database.
     */
    @Query("DELETE FROM users")
    suspend fun deleteAllUsers()
}