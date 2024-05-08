package com.sivag.password_manager.home.repo

import com.sivag.password_manager.home.data.PasswordDataModel

interface PasswordRepository {

    suspend fun getPassword(): List<PasswordDataModel>

    suspend fun addPassword(password: PasswordDataModel)

//    suspend fun editPassword(password: PasswordDataModel)

    suspend fun deletePassword(id: Int)

}