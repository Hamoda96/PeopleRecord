package com.hamoda.peoplerecord.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val jobTitle: String,
    val gender: String,
    val name: String,
    val age: Int
)
