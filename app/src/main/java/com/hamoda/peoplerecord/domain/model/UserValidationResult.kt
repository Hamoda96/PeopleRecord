package com.hamoda.peoplerecord.domain.model

data class UserValidationError(
    val nameError: String? = null,
    val ageError: String? = null,
    val jobTitleError: String? = null,
    val genderError: String? = null,
    val isValid: Boolean = false
)

fun validateUserInput(
    name: String,
    age: String,
    jobTitle: String,
    gender: String
): UserValidationError {
    var valid = true

    val nameError = if (name.length < 5) {
        valid = false
        "Name must be at least 5 characters"
    } else null

    val ageError = if (age.toIntOrNull() == null || age.toInt() <= 0) {
        valid = false
        "Enter a valid age > 0"
    } else null

    val jobTitleError = if (jobTitle.length < 5) {
        valid = false
        "Job title must be at least 5 characters"
    } else null

    val genderError = if (gender != "Male" && gender != "Female") {
        valid = false
        "Please choose a gender"
    } else null

    return UserValidationError(
        nameError = nameError,
        ageError = ageError,
        jobTitleError = jobTitleError,
        genderError = genderError,
        isValid = valid
    )
}