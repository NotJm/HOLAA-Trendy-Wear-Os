package com.example.holaa_trendy_wear_os.presentation.semibutton

import Karla
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text

@Composable
fun SemiButton(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Black,
    secondColor: Color = Color.Black,
    isTopButton: Boolean = false,
    isRoundedButton: Boolean = true,
    onClick: () -> Unit
) {
    val gradient = Brush.horizontalGradient(
        colors = listOf(color,secondColor)
    )

    if (isRoundedButton)
        Box(
            modifier = modifier
                .clip(
                    if (!isTopButton)
                        RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp)
                    else
                        RoundedCornerShape(bottomStart = 50.dp, bottomEnd = 50.dp)
                )
                .background(brush = gradient)
                .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp),
                    ambientColor = Color.Black.copy(alpha = 0.3f),
                    spotColor = Color.Black.copy(alpha = 0.5f)
                )
                .border(
                    width = 2.dp,
                    color = color.copy(alpha = 0.7f),
                    shape = RoundedCornerShape(50)
                )
                .clickable(onClick = onClick)
                .height(40.dp)
                .width(200.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                fontFamily = Karla,
                fontSize = 12.sp,
                color = Color.White
            )
        }
    else
        Box(
            modifier = modifier
                .background(brush = gradient)
                .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp),
                    ambientColor = Color.Black.copy(alpha = 0.3f),
                    spotColor = Color.Black.copy(alpha = 0.5f)
                )
                .border(
                    width = 2.dp,
                    color = color.copy(alpha = 0.7f),
                    shape = RoundedCornerShape(50)
                )
                .clickable(onClick = onClick)
                .height(40.dp)
                .width(200.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                fontFamily = Karla,
                fontSize = 12.sp,
                color = Color.White
            )
        }

}