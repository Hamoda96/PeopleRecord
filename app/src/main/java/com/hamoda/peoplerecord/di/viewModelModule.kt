package com.hamoda.peoplerecord.di

import com.hamoda.peoplerecord.presentation.user.UserViewModel
import com.hamoda.peoplerecord.presentation.users.UsersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { UserViewModel(get()) }
    viewModel { UsersViewModel(get()) }
}