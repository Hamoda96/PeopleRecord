package com.hamoda.peoplerecord.di

import com.hamoda.peoplerecord.domain.usecase.AddUserUseCase
import com.hamoda.peoplerecord.domain.usecase.DeleteAllUsersUseCase
import com.hamoda.peoplerecord.domain.usecase.DeleteUserByIdUseCase
import com.hamoda.peoplerecord.domain.usecase.GetUsersUseCase
import com.hamoda.peoplerecord.domain.usecase.UserUseCases
import org.koin.dsl.module

val useCaseModule = module {
    single {
        UserUseCases(
            addUser = AddUserUseCase(get()),
            getUsers = GetUsersUseCase(get()),
            deleteUserById = DeleteUserByIdUseCase(get()),
            deleteAll = DeleteAllUsersUseCase(get())
        )
    }
}