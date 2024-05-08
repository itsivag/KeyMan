package com.sivag.password_manager.home.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sivag.password_manager.home.data.PasswordDataModel
import com.sivag.password_manager.home.presentation.viewModel.HomeViewModel
import com.sivag.password_manager.ui.theme.BGColor
import com.sivag.password_manager.ui.theme.ButtonColor
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sivag.password_manager.utils.UIState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)) {

    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val config = LocalConfiguration.current
    val screenHeight = config.screenHeightDp.dp

    var showAddPasswordBottomSheet by remember { mutableStateOf(false) }

    var showPasswordInfoBottomSheet by remember { mutableStateOf(false) }


    val passwordList by viewModel.passwordState.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    var currPassword by remember {
        mutableStateOf(PasswordDataModel(0, "", "", ""))
    }

    var isEditPassword by remember {
        mutableStateOf(false)
    }

    var accountName by remember { mutableStateOf(TextFieldValue()) }
    var userName by remember { mutableStateOf(TextFieldValue()) }
    var password by remember { mutableStateOf(TextFieldValue()) }

    Scaffold(modifier = Modifier.fillMaxSize(), containerColor = BGColor, topBar = {
        TopAppBar(
            title = { Text(text = "Password Manager") },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = BGColor)
        )
    }, floatingActionButton = {
        FloatingActionButton(
            onClick = {
                showAddPasswordBottomSheet = true
                isEditPassword = false
            },
            containerColor = ButtonColor,
            contentColor = Color.White,
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add Password")
        }
    }) { innerPadding ->

        LazyColumn(modifier = Modifier.padding(innerPadding)) {

            when (uiState) {
                UIState.Success -> {
                    items(count = passwordList.size) { index ->
                        PasswordListItem(passwordList[index]) {
                            showPasswordInfoBottomSheet = true
                            currPassword = passwordList[index]
                        }
                    }
                }

                UIState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier
                                .height(screenHeight)
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                else -> {
                    item {
                        Box(
                            modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No Saved Password! Click button below to Add Password",
                                modifier = Modifier.padding(16.dp),
                                fontWeight = FontWeight.SemiBold,
                                color = Color.DarkGray,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
            item {
                if (showAddPasswordBottomSheet) {
                    AddPasswordBottomSheet(
                        scope = scope,
                        viewModel = viewModel,
                        passwordList = passwordList,
                        onDismiss = { showAddPasswordBottomSheet = false },
                        accountName = accountName,
                        userName = userName,
                        password = password,
                        onAccountNameChange = { newValue -> accountName = newValue },
                        onUserNameChange = { newValue -> userName = newValue },
                        onPasswordChange = { newValue -> password = newValue },
                        isEditPassword = isEditPassword,
                        editId = currPassword.id
                    )

                } else {
                    accountName = TextFieldValue()
                    userName = TextFieldValue()
                    password = TextFieldValue()
                }
            }

            item {
                if (showPasswordInfoBottomSheet) {
                    PasswordInfoBottomSheet(scope = scope,
                        currPassword = currPassword,
                        onDismiss = { showPasswordInfoBottomSheet = false },
                        deletePassword = {
                            scope.launch {
                                viewModel.deletePassword(currPassword.id)
                            }.invokeOnCompletion {
                                showPasswordInfoBottomSheet = false
                            }
                        },
                        editPassword = {
                            scope.launch {
                                accountName = TextFieldValue(text = currPassword.accountName)
                                userName = TextFieldValue(text = currPassword.userName)
                                password = TextFieldValue(text = currPassword.password)
                                isEditPassword = true
                            }.invokeOnCompletion {
                                showAddPasswordBottomSheet = true
                            }
                        })
                }
            }

        }
    }
}







