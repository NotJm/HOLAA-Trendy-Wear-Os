package com.example.holaa_trendy_wear_os.presentation.flash_deals_screen

import Bebas
import Karla
import PlayFair
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.*
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import coil.compose.AsyncImage

// Paleta de colores HOLAA Trendy
object TrendyColors {
    val Black = Color(0xFF000000)
    val Platinum = Color(0xFFE0E0E0)
    val White = Color(0xFFFFFFFF)
    val Cerise = Color(0xFFE91E63)
    val BlackSoft = Color(0xFF1A1A1A)
    val PlatinumDark = Color(0xFFBDBDBD)
    val CeriseGlow = Color(0xFFFF4081)
}

data class FlashDeal(
    val id: String,
    val title: String,
    val description: String,
    val discount: String,
    val durationMillis: Long,
    val startTime: Long = System.currentTimeMillis(),
    val category: String = "MODA",
    val originalPrice: String = "",
    val salePrice: String = ""
)

@Composable
fun FlashDealsScreen(
    onNavigateBack: () -> Unit = {}
) {
    val flashDeals = remember {
        mutableStateListOf(
            FlashDeal(
                id = "1",
                title = "TOPS PREMIUM",
                description = "Top cropped con diseño floral exclusivo",
                discount = "40% OFF",
                durationMillis = TimeUnit.HOURS.toMillis(2),
                category = "TOPS",
                originalPrice = "$899",
                salePrice = "$539"
            ),
            FlashDeal(
                id = "2",
                title = "JEANS SIGNATURE",
                description = "Jeans acampanados edición limitada",
                discount = "30% OFF",
                durationMillis = TimeUnit.MINUTES.toMillis(45),
                category = "DENIM",
                originalPrice = "$1,299",
                salePrice = "$909"
            ),
            FlashDeal(
                id = "3",
                title = "FALDAS ELEGANCE",
                description = "Falda plisada estilo colegiala premium",
                discount = "25% OFF",
                durationMillis = TimeUnit.MINUTES.toMillis(30),
                category = "FALDAS",
                originalPrice = "$699",
                salePrice = "$524"
            )
        )
    }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            flashDeals.forEachIndexed { index, deal ->
                val remainingTime = deal.startTime + deal.durationMillis - System.currentTimeMillis()
                if (remainingTime <= 0) {
                    flashDeals.removeAt(index)
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        TrendyColors.Black,
                        TrendyColors.BlackSoft,
                        TrendyColors.Black
                    )
                )
            )
    ) {
        // Elementos decorativos de fondo
        PremiumBackgroundDecorations()

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header elegante con navegación
            PremiumFlashHeader(onNavigateBack = onNavigateBack)

            // Lista de ofertas
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(6.dp),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
            ) {
                if (flashDeals.isEmpty()) {
                    item {
                        EmptyFlashDealsState()
                    }
                } else {
                    itemsIndexed(flashDeals) { index, deal ->
                        PremiumFlashDealItem(
                            deal = deal,
                            delay = index * 150L
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PremiumBackgroundDecorations() {
    Box(modifier = Modifier.fillMaxSize()) {
        // Círculo decorativo superior
        Box(
            modifier = Modifier
                .size(100.dp)
                .offset(x = 70.dp, y = (-30).dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            TrendyColors.Cerise.copy(alpha = 0.15f),
                            Color.Transparent
                        ),
                        radius = 50.dp.value
                    ),
                    shape = CircleShape
                )
                .align(Alignment.TopEnd)
        )

        // Círculo decorativo inferior
        Box(
            modifier = Modifier
                .size(80.dp)
                .offset(x = (-40).dp, y = 30.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            TrendyColors.CeriseGlow.copy(alpha = 0.1f),
                            Color.Transparent
                        ),
                        radius = 40.dp.value
                    ),
                    shape = CircleShape
                )
                .align(Alignment.BottomStart)
        )
    }
}

@Composable
fun PremiumFlashHeader(onNavigateBack: () -> Unit) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isVisible = true
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(animationSpec = tween(1000)) + slideInVertically(
            initialOffsetY = { -20 },
            animationSpec = tween(800)
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            // Fila con botón de regreso y logo
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Botón de regreso
                Button(
                    onClick = onNavigateBack,
                    modifier = Modifier.size(32.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = TrendyColors.BlackSoft
                    )
                ) {
                    Text(
                        text = "←",
                        color = TrendyColors.White,
                        fontSize = 12.sp
                    )
                }

                // Logo pequeño centrado
                AsyncImage(
                    model = "https://hebbkx1anhila5yf.public.blob.vercel-storage.com/logo-1r9WHUtpogKJYAjXm8HVQAn6e7fIZe.webp",
                    contentDescription = "HOLAA Trendy Logo",
                    modifier = Modifier
                        .height(16.dp)
                        .width(40.dp)
                )

                // Espacio para balance
                Spacer(modifier = Modifier.size(32.dp))
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Título principal
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "⚡",
                    fontSize = 12.sp
                )
                Text(
                    text = "OFERTAS RELAMPAGO",
                    fontSize = 12.sp,
                    fontFamily = Bebas,
                    color = TrendyColors.White,
                    letterSpacing = 1.sp,
                    style = TextStyle(
                        shadow = Shadow(
                            color = TrendyColors.Cerise.copy(alpha = 0.5f),
                            offset = Offset(1f, 1f),
                            blurRadius = 2f
                        )
                    )
                )
                Text(
                    text = "⚡",
                    fontSize = 12.sp
                )
            }

            // Línea decorativa
            Box(
                modifier = Modifier
                    .width(50.dp)
                    .height(1.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color.Transparent,
                                TrendyColors.Cerise,
                                Color.Transparent
                            )
                        )
                    )
            )
        }
    }
}

@Composable
fun PremiumFlashDealItem(
    deal: FlashDeal,
    delay: Long = 0L
) {
    val context = LocalContext.current
    var isVisible by remember { mutableStateOf(false) }
    var isPressed by remember { mutableStateOf(false) }

    var remainingTime by remember {
        mutableLongStateOf(deal.startTime + deal.durationMillis - System.currentTimeMillis())
    }

    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(delay)
        isVisible = true
    }

    LaunchedEffect(deal.id) {
        while (remainingTime > 0) {
            delay(1000)
            remainingTime = deal.startTime + deal.durationMillis - System.currentTimeMillis()
        }
    }

    LaunchedEffect(remainingTime <= TimeUnit.HOURS.toMillis(1)) {
        if (remainingTime in 1..TimeUnit.HOURS.toMillis(1)) {
            val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
                vibratorManager.defaultVibrator
            } else {
                context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            }
            vibrator.vibrate(VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE))
        }
    }

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy
        )
    )

    val isUrgent = remainingTime <= TimeUnit.HOURS.toMillis(1)

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(animationSpec = tween(600)) + slideInHorizontally(
            initialOffsetX = { 100 },
            animationSpec = tween(600)
        )
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .scale(scale)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = {
                            isPressed = true
                            tryAwaitRelease()
                            isPressed = false
                        }
                    )
                },
            backgroundPainter = CardDefaults.cardBackgroundPainter(
                startBackgroundColor = TrendyColors.BlackSoft,
                endBackgroundColor = TrendyColors.Black
            ),
            onClick = {},
            shape = RoundedCornerShape(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = if (isUrgent) 1.dp else 0.5.dp,
                        color = if (isUrgent) TrendyColors.CeriseGlow else TrendyColors.Platinum.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .background(
                        brush = if (isUrgent) {
                            Brush.horizontalGradient(
                                colors = listOf(
                                    TrendyColors.Cerise.copy(alpha = 0.1f),
                                    TrendyColors.CeriseGlow.copy(alpha = 0.05f)
                                )
                            )
                        } else {
                            Brush.horizontalGradient(
                                colors = listOf(
                                    TrendyColors.BlackSoft,
                                    TrendyColors.Black
                                )
                            )
                        },
                        shape = RoundedCornerShape(16.dp)
                    )
            ) {
                Column(
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    // Header con categoría y descuento
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = deal.category,
                            fontFamily = Karla,
                            fontSize = 7.sp,
                            color = TrendyColors.Platinum,
                            letterSpacing = 0.5.sp
                        )

                        Box(
                            modifier = Modifier
                                .background(
                                    color = TrendyColors.Cerise,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = deal.discount,
                                fontFamily = Bebas,
                                fontSize = 8.sp,
                                color = TrendyColors.White,
                                letterSpacing = 0.5.sp
                            )
                        }
                    }

                    // Título principal
                    Text(
                        text = deal.title,
                        fontFamily = Bebas,
                        fontSize = 14.sp,
                        color = TrendyColors.White,
                        letterSpacing = 1.sp,
                        style = TextStyle(
                            shadow = Shadow(
                                color = TrendyColors.Cerise.copy(alpha = 0.3f),
                                offset = Offset(0.5f, 0.5f),
                                blurRadius = 1f
                            )
                        )
                    )

                    // Descripción
                    Text(
                        text = deal.description,
                        fontFamily = Karla,
                        fontSize = 8.sp,
                        color = TrendyColors.Platinum,
                        lineHeight = 10.sp
                    )

                    // Precios
                    if (deal.originalPrice.isNotEmpty() && deal.salePrice.isNotEmpty()) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = deal.salePrice,
                                fontFamily = Bebas,
                                fontSize = 12.sp,
                                color = TrendyColors.Cerise,
                                letterSpacing = 0.5.sp
                            )
                            Text(
                                text = deal.originalPrice,
                                fontFamily = Karla,
                                fontSize = 8.sp,
                                color = TrendyColors.PlatinumDark,
                                style = TextStyle(
                                    textDecoration = androidx.compose.ui.text.style.TextDecoration.LineThrough
                                )
                            )
                        }
                    }

                    // Temporizador
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Text(
                            text = if (isUrgent) "🔥" else "⏰",
                            fontSize = 8.sp
                        )
                        Text(
                            text = "Termina en: ${formatRemainingTime(remainingTime)}",
                            fontFamily = Karla,
                            fontSize = 7.sp,
                            color = if (isUrgent) TrendyColors.CeriseGlow else TrendyColors.Platinum,
                            fontWeight = if (isUrgent) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun EmptyFlashDealsState() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(24.dp)
    ) {
        Text(
            text = "💤",
            fontSize = 24.sp
        )
        Text(
            text = "NO HAY OFERTAS ACTIVAS",
            fontFamily = Bebas,
            fontSize = 12.sp,
            color = TrendyColors.Platinum,
            textAlign = TextAlign.Center,
            letterSpacing = 1.sp
        )
        Text(
            text = "Las mejores ofertas aparecerán aquí",
            fontFamily = Karla,
            fontSize = 8.sp,
            color = TrendyColors.PlatinumDark,
            textAlign = TextAlign.Center
        )
    }
}

fun formatRemainingTime(millis: Long): String {
    return when {
        millis <= 0 -> "FINALIZADO"
        TimeUnit.MILLISECONDS.toHours(millis) > 0 -> {
            String.format(
                "%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1)
            )
        }
        else -> {
            String.format(
                "%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1)
            )
        }
    }
}