package com.sivag.password_manager.home.presentation.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.sivag.password_manager.home.data.PasswordDataModel
import com.sivag.password_manager.home.data.local.PasswordLocalDbService
import com.sivag.password_manager.home.repo.PasswordRepository
import com.sivag.password_manager.home.repo.PasswordRepositoryImpl
import com.sivag.password_manager.utils.RoomInstance
import com.sivag.password_manager.utils.UIState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val passwordRepository: PasswordRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState>(UIState.Loading)
    val uiState: StateFlow<UIState> = _uiState

    private val _passwordState = MutableStateFlow<List<PasswordDataModel>>(listOf())
    val passwordState: StateFlow<List<PasswordDataModel>> = _passwordState

    init {
        viewModelScope.launch {
            getPassword()
        }
    }

    private suspend fun getPassword() {
        _passwordState.value = passwordRepository.getPassword()

        if (_passwordState.value.isNotEmpty()) {
            _uiState.value = UIState.Success
        } else if (_passwordState.value.isEmpty()) {
            _uiState.value = UIState.Error("error")
        }
    }

    suspend fun addPassword(password: PasswordDataModel) {
        viewModelScope.launch {

            passwordRepository.addPassword(
                PasswordDataModel(
                    password.id, password.accountName, password.userName, password.password
                )
            )

            getPassword()
        }
    }

    suspend fun deletePassword(id: Int) {
        viewModelScope.launch {
            passwordRepository.deletePassword(id)

            getPassword()
        }
    }


    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application =
                    extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as Application
                val db = RoomInstance.getDb(application.applicationContext)

                val passwordRepo = PasswordRepositoryImpl(PasswordLocalDbService(db))

                return HomeViewModel(passwordRepo) as T
            }
        }
    }
}