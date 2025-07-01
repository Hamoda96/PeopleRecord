package com.hamoda.peoplerecord.presentation.user.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hamoda.peoplerecord.presentation.designsystem.theme.PeopleRecordTheme
import com.hamoda.peoplerecord.presentation.user.AddUserScreenContent
import com.hamoda.peoplerecord.presentation.user.UserViewModel

fun composeView(viewModel: UserViewModel, fragment: Fragment): ComposeView =
    ComposeView(fragment.requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            PeopleRecordTheme {
                AddUserScreen(viewModel = viewModel)
            }
        }
    }

@Composable
private fun AddUserScreen(viewModel: UserViewModel) {
    val isAdding by viewModel.isAdding.collectAsStateWithLifecycle()
    val userValidationError = viewModel.userValidationError
    val hasSaveUser = viewModel.hasSaveUser
    AddUserScreenContent(
        addUser = { user -> viewModel.addUser(user) },
        isAdding = isAdding,
        userInputValidation = userValidationError,
        validationError = { user -> viewModel.validateUserError(user) },
        hasSaveUser = hasSaveUser,
        skipToUsers = { viewModel.skipToUsers() }
    )
}