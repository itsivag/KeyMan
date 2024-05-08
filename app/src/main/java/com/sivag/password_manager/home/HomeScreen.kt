package com.sivag.password_manager.home

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sivag.password_manager.ui.theme.BGColor
import com.sivag.password_manager.ui.theme.ButtonColor


data class Password(
    val id: Int,
    val accountName: String,
    val userName: String,
    val password: String
)


val passwordList = listOf<Password>(
    Password(1, "google", "siva", "sample"),
    Password(1, "yahoo", "siva", "sample"),
    Password(1, "amazon", "siva", "sample"),
    Password(1, "netflix", "siva", "sample"),
    Password(1, "flipkart", "siva", "sample"),

    )

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = BGColor,
        topBar = {
            TopAppBar(
                title = { Text(text = "Password Manager") },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BGColor)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /*TODO*/ },
                containerColor = ButtonColor,
                contentColor = Color.White,
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Password")
            }
        }
    ) { innerPadding ->

        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            items(count = passwordList.size) { index ->
                val currPassword = passwordList[index]
                PasswordListItem(currPassword)
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordListItem(currPassword: Password) {
    Card(
        onClick = { /*TODO*/ },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.padding(16.dp),
        shape = RoundedCornerShape(28.dp)
    ) {
        Row(modifier = Modifier.padding(24.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = currPassword.accountName.capitalize(Locale.current),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.DarkGray
            )
            Text(
                text = "***********",
                color = Color.LightGray,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Edit Password"
            )

        }
    }
}