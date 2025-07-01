package com.hamoda.peoplerecord.presentation.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.hamoda.peoplerecord.databinding.UserFragmentBinding
import com.hamoda.peoplerecord.presentation.user.compose.composeView
import com.hamoda.peoplerecord.presentation.user.xml.xmlView
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import kotlin.getValue

// Fragment responsible for displaying the user screen.
// Supports both Compose and XML-based UI, and handles navigation and view model interaction.
class UserFragment : Fragment() {

    // Injected ViewModel for managing user data and UI logic.
    private val viewModel: UserViewModel by inject()
    // Determines which UI type to display (Compose or XML).
    private var viewType: String = "compose"
    // View binding for XML layout.
    private var _binding: UserFragmentBinding? = null
    // Getter for the view binding, ensuring it's not null.
    private val binding get() = _binding!!

    // Initializes the fragment and reads the view type argument.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewType = arguments?.getString("view_type") ?: return
            this@UserFragment.viewType = viewType
    }

    // Inflates the UI based on the selected view type (Compose or XML).
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return if (viewType.lowercase().trim() == "compose") {
            composeView(viewModel = viewModel, fragment = this)
        } else {
            _binding = UserFragmentBinding.inflate(inflater, container, false)
            xmlView(binding = binding, viewModel = viewModel, fragment = this)
        }
    }

    // Observes navigation events and navigates when triggered by the ViewModel.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.navigationEvent.collect { event ->
                    if (event == UserSideEffect.NavigateToUserList) {
                        val request = NavDeepLinkRequest.Builder
                            .fromUri("android-app://com.peoplerecord/usersfragment/${viewType}".toUri())
                            .build()
                        findNavController().navigate(request, navOptions { launchSingleTop = true })
                    }
                }
            }
        }
    }

    // Releases the view binding when the view is destroyed.
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}