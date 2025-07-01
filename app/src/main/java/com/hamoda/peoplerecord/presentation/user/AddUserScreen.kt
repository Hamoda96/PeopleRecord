@file:OptIn(ExperimentalMaterial3Api::class)

package com.hamoda.peoplerecord.presentation.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hamoda.peoplerecord.domain.model.User
import com.hamoda.peoplerecord.domain.model.UserValidationError
import com.hamoda.peoplerecord.presentation.designsystem.theme.PeopleRecordTheme
import com.hamoda.peoplerecord.presentation.user.compose.GenderDropdown
import com.hamoda.peoplerecord.presentation.user.compose.SaveUserButton
import com.hamoda.peoplerecord.presentation.user.compose.SkipButton
import com.hamoda.peoplerecord.presentation.user.compose.UserImageHeader
import com.hamoda.peoplerecord.presentation.user.compose.ValidatedTextField

@Composable
fun AddUserScreenContent(
    userInputValidation: UserValidationError,
    validationError: (User) -> Unit,
    addUser: (User) -> Unit,
    skipToUsers: () -> Unit,
    hasSaveUser: Boolean,
    isAdding: Boolean
) {
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var jobTitle by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }

    val genderOptions = listOf("Male", "Female")
    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(name, age, jobTitle, gender) {
        validationError(
            User(
                name = name,
                age = age.ifBlank { "0" }.toInt(),
                jobTitle = jobTitle,
                gender = gender
            )
        )
    }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray)
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            UserImageHeader()

            ValidatedTextField(
                value = name,
                onValueChange = { name = it },
                label = "Name",
                errorMessage = userInputValidation.nameError,
                hasSaveUser = hasSaveUser
            )

            ValidatedTextField(
                value = age,
                onValueChange = { age = it },
                label = "Age",
                keyboardType = KeyboardType.Number,
                errorMessage = userInputValidation.ageError,
                hasSaveUser = hasSaveUser
            )

            ValidatedTextField(
                value = jobTitle,
                onValueChange = { jobTitle = it },
                label = "Job Title",
                errorMessage = userInputValidation.jobTitleError,
                hasSaveUser = hasSaveUser
            )

            GenderDropdown(
                gender = gender,
                onGenderSelected = { gender = it },
                expanded = expanded,
                onExpandedChange = { expanded = it },
                options = genderOptions,
                hasSaveUser = hasSaveUser,
                errorMessage = userInputValidation.genderError
            )

            Spacer(modifier = Modifier.height(24.dp))

            SaveUserButton(
                isAdding = isAdding,
                onClick = {
                    val parsedAge = age.toIntOrNull() ?: 0
                    val user = User(
                        name = name.trim(),
                        age = parsedAge,
                        jobTitle = jobTitle.trim(),
                        gender = gender
                    )
                    addUser(user)
                }
            )

            SkipButton(onClick = { skipToUsers() })
        }
    }
}


@Preview
@Composable
private fun AddUserScreenContent() {
    PeopleRecordTheme {
        AddUserScreenContent(
            addUser = {},
            isAdding = false,
            validationError = {},
            skipToUsers = {},
            userInputValidation = UserValidationError(),
            hasSaveUser = false
        )
    }
}