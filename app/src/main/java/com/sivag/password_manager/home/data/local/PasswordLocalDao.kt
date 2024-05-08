package com.sivag.password_manager.home.data.local

import androidx.room.Dao
import androidx.room.Index
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sivag.password_manager.home.data.PasswordDataModel
import com.sivag.password_manager.home.repo.PasswordRepository

@Dao
interface PasswordLocalDao {

    @Query("SELECT * FROM password")
    suspend fun getPasswordFromDb(): List<PasswordDataModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPasswordToDb(password: PasswordDataModel)

//    @Query("SELECT * FROM password")
//    suspend fun editPasswordFromDb(password: PasswordDataModel)

    @Query("DELETE FROM password WHERE id = :id")
    suspend fun deletePassword(id: Int)
}