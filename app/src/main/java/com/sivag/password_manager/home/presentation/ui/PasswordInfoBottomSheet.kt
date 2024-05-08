package com.sivag.password_manager.home.presentation.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sivag.password_manager.home.data.PasswordDataModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordInfoBottomSheet(
    scope: CoroutineScope,
    currPassword: PasswordDataModel,
    onDismiss: () -> Unit,
    deletePassword: () -> Unit,
    editPassword: () -> Unit
) {

    val passwordInfoSheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = {
            onDismiss()
        }, sheetState = passwordInfoSheetState, containerColor = Color.White
    ) {

        val internalModifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
        Text(
            text = "Account Details",
            modifier = internalModifier,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = Color.Blue
        )
        Text(
            text = "Account Name",
            color = Color.LightGray,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Text(
            text = currPassword.accountName,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            color = Color.DarkGray,
            modifier = internalModifier,
        )

        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = "Username / Email",
            color = Color.LightGray
        )
        Text(
            text = currPassword.userName,
            modifier = internalModifier,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            color = Color.DarkGray
        )

        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = "Password",
            color = Color.LightGray
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            val passwordVisibility = remember { mutableStateOf(false) }
            Text(
                text = if (passwordVisibility.value) currPassword.password else "*".repeat(
                    currPassword.password.length
                ),
                color = Color.DarkGray,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)

            )

            IconButton(
                onClick = { passwordVisibility.value = !passwordVisibility.value },
                modifier = Modifier.padding(end = 16.dp)
            ) {
                val icon =
                    if (passwordVisibility.value) Icons.Default.AccountBox else Icons.Default.AccountCircle
                Icon(icon, contentDescription = "Toggle password visibility", tint = Color.DarkGray)
            }
        }

        Row(modifier = internalModifier) {
            Button(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp), onClick = {
                    scope.launch {
                        editPassword()
                        passwordInfoSheetState.hide()
                    }.invokeOnCompletion {
                            if (!passwordInfoSheetState.isVisible) {
                                onDismiss()
                            }
                        }
                }, colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
            ) {
                Text(
                    text = "Edit", fontWeight = FontWeight.Bold, fontSize = 20.sp
                )
            }

            Button(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),
                onClick = { deletePassword() },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Text(
                    text = "Delete", fontWeight = FontWeight.Bold, fontSize = 20.sp
                )
            }
        }
    }
}