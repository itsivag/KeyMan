package com.sivag.password_manager.home.data

import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Keep
@Entity(tableName = "password")
@Immutable
@Stable
@Serializable
data class PasswordDataModel(
    @PrimaryKey
    val id: Int,

    @ColumnInfo("account_name")
    val accountName: String,

    @ColumnInfo("user_name")
    val userName: String,

    @ColumnInfo("password")
    val password: String
)
