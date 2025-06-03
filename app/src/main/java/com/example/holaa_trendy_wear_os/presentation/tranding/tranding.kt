package com.example.holaa_trendy_wear_os.presentation.tranding

import Bebas
import Karla
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.*
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import coil.compose.AsyncImage
import com.example.holaa_trendy_wear_os.presentation.api.ApiService
import com.example.holaa_trendy_wear_os.presentation.api.Product
import kotlinx.coroutines.launch

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

@Composable
fun TrendingScreen(
    onNavigateBack: () -> Unit = {}
) {
    val apiService = remember { ApiService() }
    var products by remember { mutableStateOf<List<Product>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    // Cargar productos al iniciar la pantalla
    LaunchedEffect(Unit) {
        scope.launch {
            isLoading = true
            errorMessage = null

            apiService.getNewArrivals()
                .onSuccess { productList ->
                    products = productList
                    isLoading = false
                    println("✅ Productos cargados: ${productList.size}")
                }
                .onFailure { exception ->
                    errorMessage = exception.message ?: "Error desconocido"
                    isLoading = false
                    println("❌ Error cargando productos: ${exception.message}")
                }
        }
    }

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
                        )
                    )
                )
        ) {
            // Elementos decorativos de fondo
            TrendingBackgroundDecorations()

            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // Header elegante con navegación
                TrendingHeader(onNavigateBack = onNavigateBack)

                // Contenido principal
                when {
                    isLoading -> {
                        LoadingState()
                    }
                    errorMessage != null -> {
                        ErrorState(
                            message = errorMessage!!,
                            onRetry = {
                                scope.launch {
                                    isLoading = true
                                    errorMessage = null

                                    apiService.getNewArrivals()
                                        .onSuccess { productList ->
                                            products = productList
                                            isLoading = false
                                        }
                                        .onFailure { exception ->
                                            errorMessage = exception.message ?: "Error desconocido"
                                            isLoading = false
                                        }
                                }
                            }
                        )
                    }
                    products.isEmpty() -> {
                        EmptyTrendingState()
                    }
                    else -> {
                        ProductsList(products = products)
                    }
                }
            }
        }
    }
}

@Composable
fun TrendingBackgroundDecorations() {
    Box(modifier = Modifier.fillMaxSize()) {
        // Círculo decorativo superior
        Box(
            modifier = Modifier
                .size(100.dp)
                .offset(x = 70.dp, y = (-30).dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            TrendyColors.CeriseGlow.copy(alpha = 0.15f),
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
                            TrendyColors.Cerise.copy(alpha = 0.1f),
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
fun TrendingHeader(onNavigateBack: () -> Unit) {
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
            modifier = Modifier.padding(vertical = 6.dp)
        ) {
            // Fila con botón de regreso y logo
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Botón de regreso
                Button(
                    onClick = onNavigateBack,
                    modifier = Modifier
                        .size(28.dp)
                        .border(
                            width = 0.5.dp,
                            color = TrendyColors.Platinum.copy(alpha = 0.3f),
                            shape = RoundedCornerShape(14.dp)
                        ),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = TrendyColors.BlackSoft
                    ),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Text(
                        text = "←",
                        fontSize = 12.sp,
                        color = TrendyColors.White
                    )
                }

                // Logo pequeño centrado
                AsyncImage(
                    model = "https://hebbkx1anhila5yf.public.blob.vercel-storage.com/logo-1r9WHUtpogKJYAjXm8HVQAn6e7fIZe.webp",
                    contentDescription = "HOLAA Trendy Logo",
                    modifier = Modifier
                        .height(14.dp)
                        .width(35.dp)
                )

                // Espacio para balance
                Spacer(modifier = Modifier.size(28.dp))
            }

            Spacer(modifier = Modifier.height(3.dp))

            // Título principal
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                Text(
                    text = "🔥",
                    fontSize = 10.sp
                )
                Text(
                    text = "RECIEN LLEGADOS",
                    fontSize = 10.sp,
                    fontFamily = Bebas,
                    color = TrendyColors.White,
                    letterSpacing = 0.8.sp,
                    style = TextStyle(
                        shadow = Shadow(
                            color = TrendyColors.Cerise.copy(alpha = 0.5f),
                            offset = Offset(0.5f, 0.5f),
                            blurRadius = 1f
                        )
                    )
                )
                Text(
                    text = "🔥",
                    fontSize = 10.sp
                )
            }

            // Línea decorativa
            Box(
                modifier = Modifier
                    .width(40.dp)
                    .height(0.5.dp)
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
fun LoadingState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                indicatorColor = TrendyColors.Cerise,
                strokeWidth = 2.dp
            )
            Text(
                text = "CARGANDO...",
                fontFamily = Bebas,
                fontSize = 8.sp,
                color = TrendyColors.Platinum,
                letterSpacing = 0.5.sp
            )
        }
    }
}

@Composable
fun ErrorState(
    message: String,
    onRetry: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "⚠️",
                fontSize = 20.sp
            )
            Text(
                text = "ERROR DE CONEXIÓN",
                fontFamily = Bebas,
                fontSize = 10.sp,
                color = TrendyColors.Cerise,
                textAlign = TextAlign.Center,
                letterSpacing = 0.8.sp
            )
            Text(
                text = message,
                fontFamily = Karla,
                fontSize = 7.sp,
                color = TrendyColors.Platinum,
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            Button(
                onClick = onRetry,
                modifier = Modifier
                    .border(
                        width = 0.5.dp,
                        color = TrendyColors.White.copy(alpha = 0.3f),
                        shape = RoundedCornerShape(12.dp)
                    ),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = TrendyColors.Cerise
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "REINTENTAR",
                    fontFamily = Bebas,
                    fontSize = 7.sp,
                    color = TrendyColors.White,
                    letterSpacing = 0.5.sp
                )
            }
        }
    }
}

@Composable
fun EmptyTrendingState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "🛍️",
                fontSize = 20.sp
            )
            Text(
                text = "SIN PRODUCTOS",
                fontFamily = Bebas,
                fontSize = 10.sp,
                color = TrendyColors.Platinum,
                textAlign = TextAlign.Center,
                letterSpacing = 0.8.sp
            )
            Text(
                text = "No hay productos trending disponibles",
                fontFamily = Karla,
                fontSize = 7.sp,
                color = TrendyColors.PlatinumDark,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun ProductsList(products: List<Product>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
    ) {
        items(products) { product ->
            ProductItem(product = product)
        }
    }
}

@Composable
fun ProductItem(product: Product) {
    var isPressed by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy
        )
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .border(
                width = 0.5.dp,
                color = TrendyColors.Platinum.copy(alpha = 0.2f),
                shape = RoundedCornerShape(16.dp)
            )
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
        onClick = { /* TODO: Implementar navegación a detalle del producto */ },
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            // Header con categoría y badge NEW
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = product.category?.uppercase() ?: "MODA",
                    fontFamily = Karla,
                    fontSize = 6.sp,
                    color = TrendyColors.Platinum,
                    letterSpacing = 0.3.sp
                )

                // Mostrar badge NEW si el descuento es mayor a 0
                val hasDiscount = product.discountAsDouble?.let { it > 0 } ?: false
                if (hasDiscount) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = TrendyColors.CeriseGlow,
                                shape = RoundedCornerShape(6.dp)
                            )
                            .padding(horizontal = 4.dp, vertical = 1.dp)
                    ) {
                        Text(
                            text = "OFERTA",
                            fontFamily = Bebas,
                            fontSize = 6.sp,
                            color = TrendyColors.White,
                            letterSpacing = 0.3.sp
                        )
                    }
                }
            }

            // Nombre del producto
            Text(
                text = product.name,
                fontFamily = Bebas,
                fontSize = 11.sp,
                color = TrendyColors.White,
                letterSpacing = 0.5.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            // Descripción (si existe)
            product.description?.let { description ->
                Text(
                    text = description,
                    fontFamily = Karla,
                    fontSize = 7.sp,
                    color = TrendyColors.PlatinumDark,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Precios - ACTUALIZADO para usar las nuevas propiedades
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Precio final (si existe) o precio original
                val displayPrice = if (product.price != "0.00" && product.price.isNotEmpty()) {
                    product.price
                } else {
                    product.originalPrice ?: "0.00"
                }

                Text(
                    text = "$$displayPrice",
                    fontFamily = Bebas,
                    fontSize = 10.sp,
                    color = TrendyColors.Cerise,
                    letterSpacing = 0.3.sp
                )

                // Mostrar precio original tachado si hay descuento
                val originalPrice = product.originalPriceAsDouble
                val finalPrice = product.priceAsDouble
                val hasValidDiscount = originalPrice != null && finalPrice > 0 && originalPrice > finalPrice

                if (hasValidDiscount && originalPrice != null) {
                    Text(
                        text = "$${product.originalPrice}",
                        fontFamily = Karla,
                        fontSize = 7.sp,
                        color = TrendyColors.PlatinumDark,
                        style = TextStyle(
                            textDecoration = androidx.compose.ui.text.style.TextDecoration.LineThrough
                        )
                    )

                    // Calcular y mostrar descuento
                    val discountPercent = ((originalPrice - finalPrice) / originalPrice * 100).toInt()
                    if (discountPercent > 0) {
                        Text(
                            text = "-${discountPercent}%",
                            fontFamily = Bebas,
                            fontSize = 7.sp,
                            color = TrendyColors.CeriseGlow,
                            letterSpacing = 0.2.sp
                        )
                    }
                }
            }

            // Mostrar información adicional si está disponible
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Subcategoría
                product.subCategory?.let { subCategory ->
                    Text(
                        text = subCategory.uppercase(),
                        fontFamily = Karla,
                        fontSize = 5.sp,
                        color = TrendyColors.PlatinumDark,
                        letterSpacing = 0.2.sp
                    )
                }

                // Número de colores disponibles
                product.colors?.let { colors ->
                    if (colors.isNotEmpty()) {
                        Text(
                            text = "${colors.size} colores",
                            fontFamily = Karla,
                            fontSize = 5.sp,
                            color = TrendyColors.PlatinumDark,
                            letterSpacing = 0.2.sp
                        )
                    }
                }

                // Número de tallas disponibles
                product.sizes?.let { sizes ->
                    if (sizes.isNotEmpty()) {
                        Text(
                            text = "${sizes.size} tallas",
                            fontFamily = Karla,
                            fontSize = 5.sp,
                            color = TrendyColors.PlatinumDark,
                            letterSpacing = 0.2.sp
                        )
                    }
                }
            }
        }
    }
}