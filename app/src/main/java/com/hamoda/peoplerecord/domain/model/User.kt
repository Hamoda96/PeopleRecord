package com.hamoda.peoplerecord.domain.model

data class User(
    val id: Int = 0,
    val name: String,
    val age: Int,
    val jobTitle: String,
    val gender: String
){
    val isMale = gender.lowercase().trim() == "male"
}