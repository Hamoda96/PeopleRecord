package com.hamoda.peoplerecord.data.model

import com.hamoda.peoplerecord.data.local.UserEntity
import com.hamoda.peoplerecord.domain.model.User

/**
 * Maps UserEntity to User domain model.
 */
fun UserEntity.toDomain(): User {
    return User(id = id, name = name, age = age, jobTitle = jobTitle, gender = gender)
}

/**
 * Maps User domain model to UserEntity for database storage.
 */
fun User.toEntity(): UserEntity {
    return UserEntity(id = id, name = name, age = age, jobTitle = jobTitle, gender = gender)
}