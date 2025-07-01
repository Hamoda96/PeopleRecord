package com.hamoda.peoplerecord.presentation.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.net.toUri
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.hamoda.peoplerecord.domain.model.User
import com.hamoda.peoplerecord.presentation.designsystem.theme.PeopleRecordTheme
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import kotlin.getValue

class UserFragment : Fragment() {

    private val viewModel: UserViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)

            setContent {
                PeopleRecordTheme {
                    AddUserScreen(
                        addUser = { user -> viewModel.addUser(user = user) },
                        validationError = { user -> viewModel.validateUserError(user) },
                        skipToUsers = { viewModel.skipToUsers() }
                    )
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.navigationEvent.collect { event ->
                    when (event) {
                        UserSideEffect.NavigateToUserList -> {
                            val request = NavDeepLinkRequest.Builder
                                .fromUri("android-app://com.peoplerecord/usersfragment/".toUri())
                                .build()

                            findNavController().navigate(
                                request,
                                navOptions {
                                    launchSingleTop = true
                                })
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun AddUserScreen(
        addUser: (User) -> Unit,
        validationError: (User) -> Unit,
        skipToUsers: () -> Unit
    ) {
        val isAdding by viewModel.isAdding.collectAsStateWithLifecycle()
        val userValidationError = viewModel.userValidationError
        val hasSaveUser = viewModel.hasSaveUser

        AddUserScreenContent(
            addUser = addUser,
            isAdding = isAdding,
            userInputValidation = userValidationError,
            validationError = validationError,
            hasSaveUser = hasSaveUser,
            skipToUsers = skipToUsers
        )
    }
}