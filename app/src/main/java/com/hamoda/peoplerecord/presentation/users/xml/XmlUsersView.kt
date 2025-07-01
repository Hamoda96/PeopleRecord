package com.hamoda.peoplerecord.presentation.users.xml

import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hamoda.peoplerecord.R
import com.hamoda.peoplerecord.presentation.users.UsersEvent
import com.hamoda.peoplerecord.presentation.users.UsersViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

fun xmlUsersView(
    fragment: Fragment,
    view: View,
    viewModel: UsersViewModel
) {
    val btnAddUser = view.findViewById<Button>(R.id.btnAddUser)
    val btnDeleteAll = view.findViewById<Button>(R.id.btnDeleteAll)
    val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)
    val usersRecyclerView = view.findViewById<RecyclerView>(R.id.usersRecyclerView)

    val adapter = UserAdapter(emptyList()) { user ->
        viewModel.onEvent(UsersEvent.DeleteUser(user.id))
    }
    usersRecyclerView.layoutManager = LinearLayoutManager(fragment.requireContext())
    usersRecyclerView.adapter = adapter

    // Observe users and loading state using StateFlow
    fragment.lifecycleScope.launch {
        viewModel.users.collectLatest { users ->
            adapter.submitList(users)
        }
    }
    fragment.lifecycleScope.launch {
        viewModel.loading.collectLatest { loading ->
            progressBar.visibility = if (loading) View.VISIBLE else View.GONE
        }
    }

    btnAddUser.setOnClickListener {
        viewModel.onEvent(UsersEvent.AddUser)
    }
    btnDeleteAll.setOnClickListener {
        viewModel.onEvent(UsersEvent.DeleteAllUsers)
    }
}
