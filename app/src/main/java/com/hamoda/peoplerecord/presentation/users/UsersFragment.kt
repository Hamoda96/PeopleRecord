package com.hamoda.peoplerecord.presentation.users

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
import com.hamoda.peoplerecord.presentation.designsystem.theme.PeopleRecordTheme
import com.hamoda.peoplerecord.presentation.user.UserSideEffect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import kotlin.getValue

class UsersFragment : Fragment() {

    private val viewModel: UsersViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)

            setContent {
                PeopleRecordTheme {
                    AddUserScreen(onEvent = viewModel::onEvent)
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
                        UsersSideEffect.NavigateToAddUser -> {
                            val request = NavDeepLinkRequest.Builder
                                .fromUri("android-app://com.peoplerecord/userfragment/".toUri())
                                .build()

                            findNavController().navigate(request, navOptions {
                                launchSingleTop = true
                            })
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun AddUserScreen(onEvent: (UsersEvent) -> Unit) {
        val users by viewModel.users.collectAsStateWithLifecycle()
        val loading by viewModel.loading.collectAsStateWithLifecycle()
        UserListScreenContent(onEvent = onEvent, users = users, loading = loading)
    }
}