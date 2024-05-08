package com.sivag.password_manager.utils

sealed class UIState {
    data object Loading : UIState()
    data object Success : UIState()
    data class Error(val errorMessage: String) : UIState()
}
