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

class UserFragment : Fragment() {

    private val viewModel: UserViewModel by inject()
    private var viewType: String = "compose"
    private var _binding: UserFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewType = arguments?.getString("view_type") ?: return
            this@UserFragment.viewType = viewType

    }

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}