package com.hamoda.peoplerecord.presentation.users

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hamoda.peoplerecord.R
import com.hamoda.peoplerecord.domain.model.User

@Composable
fun UserListScreenContent(onEvent: (UsersEvent) -> Unit, users: List<User>, loading: Boolean) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEvent(UsersEvent.AddUser) },
                content = { Icon(imageVector = Icons.Outlined.Add, contentDescription = "Delete") }
            )
        },
        content = { paddingValues ->
            if (loading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .padding(paddingValues)
                        .padding(16.dp),
                    content = {
                        items(users) { user ->
                            UserItem(
                                user = user,
                                onDeleteClick = { onEvent(UsersEvent.DeleteUser(id = user.id)) })
                        }

                        item {
                            Button(
                                onClick = { onEvent(UsersEvent.DeleteAllUsers) },
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                            ) {
                                Text(
                                    stringResource(R.string.delete_all),
                                    color = MaterialTheme.colorScheme.onError
                                )
                            }
                        }
                    }
                )
            }
        }
    )
}