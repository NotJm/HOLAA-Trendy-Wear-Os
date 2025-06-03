package com.example.holaa_trendy_wear_os.presentation.home

import Bebas
import Karla
import PlayFair
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.*
import com.example.holaa_trendy_wear_os.R
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage

// Paleta de colores HOLAA Trendy
object TrendyColors {
    val Black = Color(0xFF000000)
    val Platinum = Color(0xFFE0E0E0)
    val White = Color(0xFFFFFFFF)
    val Cerise = Color(0xFFE91E63)
    val BlackSoft = Color(0xFF1A1A1A)
    val PlatinumDark = Color(0xFFBDBDBD)
}

@Composable
fun HomeScreen(
    onNavigateToFlashDeals: () -> Unit = {},
    onNavigateToTrending: () -> Unit = {}
) {
    val scrollState = rememberScrollState()

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            TrendyColors.Black,
                            TrendyColors.BlackSoft,
                            TrendyColors.Black
                        ),
                        startY = 0f,
                        endY = 800f
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            // Elementos decorativos de fondo
            BackgroundDecorations()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Logo y header premium con imagen
                PremiumHeaderWithLogo()

                Spacer(modifier = Modifier.height(8.dp))

                // Grid de opciones con navegación (solo 2 botones)
                MinimalistOptionsGrid(
                    onNavigateToFlashDeals = onNavigateToFlashDeals,
                    onNavigateToTrending = onNavigateToTrending
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Footer elegante
                ElegantFooter()
            }
        }
    }
}

@Composable
fun BackgroundDecorations() {
    // Círculos decorativos con el color cerise
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Círculo superior derecho
        Box(
            modifier = Modifier
                .size(80.dp)
                .offset(x = 60.dp, y = (-20).dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            TrendyColors.Cerise.copy(alpha = 0.1f),
                            Color.Transparent
                        ),
                        radius = 40.dp.value
                    ),
                    shape = CircleShape
                )
                .align(Alignment.TopEnd)
        )

        // Círculo inferior izquierdo
        Box(
            modifier = Modifier
                .size(60.dp)
                .offset(x = (-30).dp, y = 20.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            TrendyColors.Cerise.copy(alpha = 0.08f),
                            Color.Transparent
                        ),
                        radius = 30.dp.value
                    ),
                    shape = CircleShape
                )
                .align(Alignment.BottomStart)
        )
    }
}

@Composable
fun PremiumHeaderWithLogo() {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isVisible = true
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(animationSpec = tween(1200)) + slideInVertically(
            initialOffsetY = { -30 },
            animationSpec = tween(1000)
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            // Logo HOLAA Trendy como imagen
            AsyncImage(
                model = "https://hebbkx1anhila5yf.public.blob.vercel-storage.com/logo-1r9WHUtpogKJYAjXm8HVQAn6e7fIZe.webp",
                contentDescription = "HOLAA Trendy Logo",
                modifier = Modifier
                    .height(24.dp)
                    .width(60.dp),
                contentScale = ContentScale.Fit
            )

            // Línea decorativa debajo del logo
            Box(
                modifier = Modifier
                    .width(40.dp)
                    .height(1.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color.Transparent,
                                TrendyColors.Cerise.copy(alpha = 0.6f),
                                Color.Transparent
                            )
                        )
                    )
            )

            // Subtítulo elegante
            Text(
                text = "MODA INTELIGENTE",
                fontSize = 7.sp,
                fontFamily = Karla,
                color = TrendyColors.Platinum,
                letterSpacing = 1.2.sp,
                fontWeight = FontWeight.Normal,
                style = TextStyle(
                    shadow = Shadow(
                        color = TrendyColors.Black.copy(alpha = 0.5f),
                        offset = Offset(0.5f, 0.5f),
                        blurRadius = 1f
                    )
                )
            )
        }
    }
}

@Composable
fun MinimalistOptionsGrid(
    onNavigateToFlashDeals: () -> Unit,
    onNavigateToTrending: () -> Unit
) {
    val options = listOf(
        OptionData("⚡", "Flash Deals", TrendyColors.White, onNavigateToFlashDeals),
        OptionData("🔥", "Trending", TrendyColors.Cerise, onNavigateToTrending)
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
    ) {
        options.forEachIndexed { index, option ->
            MinimalistOptionCard(
                option = option,
                delay = index * 150L
            )
        }
    }
}

@Composable
fun MinimalistOptionCard(
    option: OptionData,
    delay: Long = 0L
) {
    var isVisible by remember { mutableStateOf(false) }
    var isPressed by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(delay)
        isVisible = true
    }

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.92f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessHigh
        )
    )

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(animationSpec = tween(800)) + scaleIn(
            initialScale = 0.7f,
            animationSpec = tween(800)
        )
    ) {
        Card(
            modifier = Modifier
                .size(width = 70.dp, height = 60.dp)
                .scale(scale)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = {
                            isPressed = true
                            tryAwaitRelease()
                            isPressed = false
                        },
                        onTap = {
                            option.onClick()
                        }
                    )
                },
            shape = RoundedCornerShape(16.dp),
            onClick = option.onClick,
            backgroundPainter = CardDefaults.cardBackgroundPainter(
                startBackgroundColor = TrendyColors.BlackSoft,
                endBackgroundColor = TrendyColors.Black
            ),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .border(
                        width = 0.5.dp,
                        color = TrendyColors.Platinum.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(16.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(6.dp)
                ) {
                    Text(
                        text = option.emoji,
                        fontSize = 18.sp
                    )
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        text = option.label,
                        fontSize = 7.sp,
                        fontFamily = Karla,
                        color = option.color,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        lineHeight = 8.sp
                    )
                }
            }
        }
    }
}

@Composable
fun ElegantFooter() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        // Indicador de estado
        Row(
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(4.dp)
                    .background(
                        color = TrendyColors.Cerise,
                        shape = CircleShape
                    )
            )
            Text(
                text = "CONECTADO",
                fontSize = 6.sp,
                fontFamily = Karla,
                color = TrendyColors.Platinum,
                letterSpacing = 0.5.sp
            )
        }
    }
}

// Data class para las opciones con navegación
data class OptionData(
    val emoji: String,
    val label: String,
    val color: Color,
    val onClick: () -> Unit
)