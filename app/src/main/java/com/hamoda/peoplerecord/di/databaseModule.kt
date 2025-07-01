package com.hamoda.peoplerecord.di

import android.app.Application
import androidx.room.Room
import com.hamoda.peoplerecord.data.local.UserDatabase
import org.koin.dsl.module
import kotlin.jvm.java


val databaseModule = module {
    single {
        Room.databaseBuilder(
            get<Application>(),
            UserDatabase::class.java,
            "people_record_db"
        ).build()
    }

    single { get<UserDatabase>().userDao() }
}