package com.sivag.password_manager.home.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sivag.password_manager.home.data.PasswordDataModel

@Database(entities = [PasswordDataModel::class], version = 1)
abstract class PasswordDatabase : RoomDatabase() {
    abstract fun passwordDao(): PasswordLocalDao
}