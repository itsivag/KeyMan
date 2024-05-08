package com.sivag.password_manager.home.presentation.ui

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sivag.password_manager.home.data.PasswordDataModel
import com.sivag.password_manager.home.presentation.viewModel.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPasswordBottomSheet(
    scope: CoroutineScope,
    viewModel: HomeViewModel,
    passwordList: List<PasswordDataModel>,
    onDismiss: () -> Unit,
    accountName: TextFieldValue,
    userName: TextFieldValue,
    password: TextFieldValue,
    onAccountNameChange: (TextFieldValue) -> Unit,
    onUserNameChange: (TextFieldValue) -> Unit,
    onPasswordChange: (TextFieldValue) -> Unit,
    isEditPassword: Boolean,
    editId: Int,
) {

    val addPasswordSheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = addPasswordSheetState,
        containerColor = Color.White
    ) {

        val internalModifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()

        val textFieldColors =
            OutlinedTextFieldDefaults.colors(unfocusedBorderColor = Color.LightGray)


        var buttonText by remember {
            mutableStateOf("")
        }

        val id = if (isEditPassword) {
            editId
        } else {
            passwordList.size + 1
        }

        var accountFieldError by remember {
            mutableStateOf(false)
        }

        var usernameFieldError by remember {
            mutableStateOf(false)
        }

        var passwordFieldError by remember {
            mutableStateOf(false)
        }


        var passwordHealth by remember {
            mutableFloatStateOf(0f)
        }

        buttonText = if (!isEditPassword) {
            "Add New Account"
        } else {
            "Edit"
        }


        OutlinedTextField(
            value = accountName,
            onValueChange = { value ->
                onAccountNameChange(value)
                accountFieldError = value.text.isEmpty()
            },
            isError = accountFieldError,
            colors = textFieldColors,
            label = { Text(text = "Account Name", color = Color.LightGray) },
            shape = RoundedCornerShape(12.dp),
            modifier = internalModifier
        )

        AnimatedVisibility(accountFieldError) {
            Text(
                text = "Account name cannot be empty",
                color = Color.Red,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
        OutlinedTextField(
            value = userName,
            onValueChange = { value ->
                onUserNameChange(value)
                usernameFieldError = value.text.isEmpty()
            },
            label = { Text(text = "Username / Email", color = Color.LightGray) },
            modifier = internalModifier,
            shape = RoundedCornerShape(12.dp),
            colors = textFieldColors,
            isError = usernameFieldError
        )


        AnimatedVisibility(usernameFieldError) {
            Text(
                text = "User name cannot be empty",
                color = Color.Red,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        OutlinedTextField(
            isError = passwordFieldError,
            value = password,
            shape = RoundedCornerShape(12.dp),
            colors = textFieldColors,
            onValueChange = { value ->
                onPasswordChange(value)
                isPasswordValid(
                    value,
                    enablePasswordError = { passwordFieldError = true },
                    disablePasswordError = { passwordFieldError = false },
                    updatePasswordHealth = { progress -> passwordHealth + progress })
            },
            label = { Text(text = "Password", color = Color.LightGray) },
            modifier = internalModifier
        )

        //password health meter
//        LinearProgressIndicator(progress = passwordHealth)

        AnimatedVisibility(passwordFieldError) {
            Text(
                text = "Password should be 8-13 characters long and should contain numbers and spl characters.",
                color = Color.Red,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        Button(
            enabled = !passwordFieldError && !accountFieldError && !usernameFieldError && accountName.text.isNotEmpty() && userName.text.isNotEmpty() && isPasswordValid(
                password,
                enablePasswordError = { passwordFieldError = true },
                disablePasswordError = { passwordFieldError = false },
                updatePasswordHealth = { progress -> passwordHealth + progress }
            ),
            onClick = {
                scope.launch {
                    viewModel.addPassword(
                        PasswordDataModel(
                            id, accountName.text, userName.text, password.text
                        )
                    )
                    addPasswordSheetState.hide()
                }.invokeOnCompletion {
                    if (!addPasswordSheetState.isVisible) {
                        onDismiss()
                    }
                }
            },
            modifier = internalModifier,
            colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
        ) {
            Text(
                text = buttonText, fontWeight = FontWeight.Bold, fontSize = 20.sp
            )
        }
    }
}

fun isPasswordValid(
    password: TextFieldValue,
    enablePasswordError: () -> Unit,
    disablePasswordError: () -> Unit,
    updatePasswordHealth: (Float) -> Unit
): Boolean {
    try {

        val passwordRegex = Regex("^(?=.*[0-9])(?=.*[!@#\$%^&*])(?=\\S+\$).{8,13}\$")
        val isValid = passwordRegex.matches(password.text)
        if (!isValid) {
            enablePasswordError()
        } else {
            disablePasswordError()
            var passwordHealth = 0.0f
            val digitCount = password.text.count { it.isDigit() }
            passwordHealth += when {
                digitCount == 1 -> 0.2f
                digitCount == 2 -> 0.3f
                digitCount >= 3 -> 0.4f
                else -> 0.0f
            }
            val specialCharCount = password.text.count { it in "!@#\$%^&*" }
            passwordHealth += when {
                specialCharCount == 1 -> 0.2f
                specialCharCount == 2 -> 0.3f
                specialCharCount >= 3 -> 0.4f
                else -> 0.0f
            }
            passwordHealth = passwordHealth.coerceAtMost(1.0f)
            updatePasswordHealth(passwordHealth)
        }
        return isValid
    } catch (e: Exception) {
        Log.e("Password validation error", e.toString())
        return false
    }
}



