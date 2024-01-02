package com.cholis.myapplicationgithubuser.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserGithubEntity::class], version = 1, exportSchema = false)
abstract class UserGithubRoomDB : RoomDatabase() {

    abstract fun userGithubDao(): UserGithubDao

    companion object {
        @Volatile
        private var INSTANCE: UserGithubRoomDB? = null
        @JvmStatic
        fun getDatabase(context: Context): UserGithubRoomDB {
            if (INSTANCE == null) {
                synchronized(UserGithubRoomDB::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        UserGithubRoomDB::class.java, "userGithub_database").build()
                }
            }
            return INSTANCE as UserGithubRoomDB
            }
        }
    }
