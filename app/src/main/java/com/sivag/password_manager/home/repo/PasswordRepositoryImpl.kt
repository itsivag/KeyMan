package com.sivag.password_manager.home.repo

import android.util.Log
import com.sivag.password_manager.home.data.PasswordDataModel
import com.sivag.password_manager.home.data.local.PasswordLocalDao

class PasswordRepositoryImpl(private val passwordLocalDao: PasswordLocalDao) : PasswordRepository {
    override suspend fun getPassword(): List<PasswordDataModel> {
        val encryptedPasswords = passwordLocalDao.getPasswordFromDb()
        return encryptedPasswords.map { encryptedPassword ->
            try {
                val decryptedPass =
                    encryptedPassword.password.decrypt("1234567891234567")
                PasswordDataModel(
                    encryptedPassword.id,
                    encryptedPassword.accountName,
                    encryptedPassword.userName,
                    decryptedPass
                )
            } catch (e: Exception) {
                Log.e("Password Decryption Error", e.toString())
                PasswordDataModel(
                    encryptedPassword.id,
                    encryptedPassword.accountName,
                    encryptedPassword.userName,
                    ""
                )
            }
        }
    }

    override suspend fun addPassword(password: PasswordDataModel) {
        try {
            val encryptedPass = password.password.encrypt("1234567891234567")
            val encryptedPasswordData = PasswordDataModel(
                password.id,
                password.accountName,
                password.userName,
                encryptedPass
            )
            passwordLocalDao.addPasswordToDb(encryptedPasswordData)

        } catch (e: Exception) {
            Log.e("Password Encryption Error", e.toString())
        }
    }

    override suspend fun deletePassword(id: Int) {
        passwordLocalDao.deletePassword(id)
    }

//    override suspend fun editPassword(password: PasswordDataModel) {
//        passwordLocalDao.editPasswordFromDb(password)
//    }

//    override suspend fun deletePassword(password: PasswordDataModel) {
//        passwordLocalDao.deletePassword(password)
//    }

}