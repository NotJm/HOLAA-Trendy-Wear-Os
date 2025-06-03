package com.example.holaa_trendy_wear_os.presentation.icon_button

import Karla
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Card
import androidx.wear.compose.material.CardDefaults
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Text

@Composable
fun EmojiCard(emoji: String, label: String, bgColor: Color, size: Dp = 70.dp) {
    Box(
        modifier = Modifier
            .size(size) // ⬅️ aquí controlas el tamaño total de la card
            .clip(RoundedCornerShape(0.dp))
            .fillMaxSize()
            .background(bgColor),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = label,
                fontSize = 10.sp,
                fontFamily = Karla,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = emoji,
                fontSize = 12.sp // ⬅️ tamaño del emoji
            )
            Spacer(modifier = Modifier.height(4.dp))

        }
    }
}

