package com.sivag.password_manager.home.data.local

import com.sivag.password_manager.home.data.PasswordDataModel

class PasswordLocalDbService(private val db: PasswordDatabase) : PasswordLocalDao {

    override suspend fun getPasswordFromDb(): List<PasswordDataModel> =
        db.passwordDao().getPasswordFromDb()

    override suspend fun addPasswordToDb(password: PasswordDataModel) =
        db.passwordDao().addPasswordToDb(password)

    override suspend fun deletePassword(id: Int) {
        db.passwordDao().deletePassword(id)
    }

//    override suspend fun editPasswordFromDb(password: PasswordDataModel) =
//        db.passwordDao().editPasswordFromDb(password)

//    override suspend fun deletePassword(password: PasswordDataModel) =
//        db.passwordDao().deletePassword(password)
}