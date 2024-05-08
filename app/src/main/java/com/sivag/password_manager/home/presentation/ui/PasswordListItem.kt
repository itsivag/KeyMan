package com.sivag.password_manager.home.presentation.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sivag.password_manager.home.data.PasswordDataModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordListItem(
    currPassword: PasswordDataModel,
    onItemClick: () -> Unit // Callback to be triggered when the card is clicked
) {
    Card(
        onClick = onItemClick, // Trigger callback when the card is clicked
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
                imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "Edit Password"
            )
        }
    }
}
