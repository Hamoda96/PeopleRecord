package com.hamoda.peoplerecord.di

import com.hamoda.peoplerecord.data.repository.UserRepositoryImpl
import com.hamoda.peoplerecord.domain.repository.UserRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<UserRepository> { UserRepositoryImpl(get()) }
}