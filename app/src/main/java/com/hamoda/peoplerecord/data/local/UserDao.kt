package com.hamoda.peoplerecord.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insetUser(userEntity: UserEntity)

    @Query("SELECT * FROM users ORDER BY id DESC")
    fun getAllUsers(): Flow<List<UserEntity>>

    @Query("DELETE FROM users WHERE id = :userId")
    suspend fun deleteUserById(userId: Int)

    @Query("DELETE FROM users")
    suspend fun deleteAllUsers()
}