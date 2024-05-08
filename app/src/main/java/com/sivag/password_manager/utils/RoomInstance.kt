package com.sivag.password_manager.utils

import android.content.Context
import androidx.room.Room
import com.sivag.password_manager.home.data.local.PasswordDatabase

object RoomInstance {
    fun getDb(context: Context): PasswordDatabase {
        return Room.databaseBuilder(
            context,
            PasswordDatabase::class.java, "password"
        ).fallbackToDestructiveMigration().build()
    }
}