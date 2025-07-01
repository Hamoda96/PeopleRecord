package com.hamoda.peoplerecord.presentation.users

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.hamoda.peoplerecord.R
import com.hamoda.peoplerecord.presentation.designsystem.theme.PeopleRecordTheme
import com.hamoda.peoplerecord.presentation.users.xml.xmlUsersView
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import kotlin.getValue

class UsersFragment : Fragment() {

    private val viewModel: UsersViewModel by inject()
    private var useCompose = false
    private lateinit var binding: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Optionally restore useCompose from savedInstanceState
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (useCompose) {
            return ComposeView(requireContext()).apply {
                setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
                setContent {
                    PeopleRecordTheme {
                        UserListScreenContent(
                            onEvent = viewModel::onEvent,
                            users = viewModel.users.collectAsStateWithLifecycle(emptyList()).value,
                            loading = viewModel.loading.collectAsStateWithLifecycle(false).value
                        )
                    }
                }
            }
        } else {
            binding = inflater.inflate(R.layout.users_fragment, container, false)
            xmlUsersView(fragment = this, view = binding, viewModel = viewModel)
            return binding
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
}