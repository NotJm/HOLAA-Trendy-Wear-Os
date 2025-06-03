package com.example.holaa_trendy_wear_os.presentation.api

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.net.HttpURLConnection
import java.net.URL

// Data classes para la respuesta de la API
@Serializable
data class Product(
    @SerialName("code")
    val id: String,
    val name: String,
    val description: String? = null,
    @SerialName("finalPrice")
    val price: String, // Cambiado a String porque la API devuelve String
    @SerialName("price")
    val originalPrice: String? = null,
    @SerialName("imgUri")
    val imageUrl: String? = null,
    @SerialName("categoryName")
    val category: String? = null,
    val isNew: Boolean = false,
    val discount: String? = null, // Cambiado a String
    @SerialName("subCategoryName")
    val subCategory: String? = null,
    @SerialName("colorsNames")
    val colors: List<String>? = null,
    @SerialName("sizesNames")
    val sizes: List<String>? = null
) {
    // Propiedades computadas para convertir precios a Double si es necesario
    val priceAsDouble: Double
        get() = price.toDoubleOrNull() ?: 0.0

    val originalPriceAsDouble: Double?
        get() = originalPrice?.toDoubleOrNull()

    val discountAsDouble: Double?
        get() = discount?.toDoubleOrNull()
}

@Serializable
data class ApiResponse<T>(
    val success: Boolean,
    val data: T? = null,
    val message: String? = null
)

class ApiService {
    private val baseUrl = "http://172.18.208.1:3000"
    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        coerceInputValues = true
    }

    suspend fun getNewArrivals(): Result<List<Product>> = withContext(Dispatchers.IO) {
        try {
            val url = URL("$baseUrl/products/view/new-arrivals")
            val connection = url.openConnection() as HttpURLConnection

            connection.apply {
                requestMethod = "GET"
                setRequestProperty("Content-Type", "application/json")
                setRequestProperty("Accept", "application/json")
                connectTimeout = 10000
                readTimeout = 10000
            }

            val responseCode = connection.responseCode

            if (responseCode == HttpURLConnection.HTTP_OK) {
                val response = connection.inputStream.bufferedReader().use { it.readText() }

                println("⚠️ Response: $response")

                // Intentar parsear como lista directa
                val products = try {
                    json.decodeFromString<List<Product>>(response)
                } catch (e: Exception) {
                    println("❌ Error parsing as direct list: ${e.message}")
                    // Si falla, intentar como ApiResponse
                    try {
                        val apiResponse = json.decodeFromString<ApiResponse<List<Product>>>(response)
                        apiResponse.data ?: emptyList()
                    } catch (e2: Exception) {
                        println("❌ Error parsing as ApiResponse: ${e2.message}")
                        throw e2
                    }
                }

                Result.success(products)
            } else {
                val errorResponse = connection.errorStream?.bufferedReader()?.use { it.readText() }
                Result.failure(Exception("HTTP Error $responseCode: $errorResponse"))
            }
        } catch (e: Exception) {
            println("❌ Exception in getNewArrivals: ${e.message}")
            e.printStackTrace()
            Result.failure(e)
        }
    }

    suspend fun getFlashDeals(): Result<List<Product>> = withContext(Dispatchers.IO) {
        try {
            val url = URL("$baseUrl/products/flash-deals")
            val connection = url.openConnection() as HttpURLConnection

            connection.apply {
                requestMethod = "GET"
                setRequestProperty("Content-Type", "application/json")
                setRequestProperty("Accept", "application/json")
                connectTimeout = 10000
                readTimeout = 10000
            }

            val responseCode = connection.responseCode

            if (responseCode == HttpURLConnection.HTTP_OK) {
                val response = connection.inputStream.bufferedReader().use { it.readText() }

                val products = try {
                    json.decodeFromString<List<Product>>(response)
                } catch (e: Exception) {
                    println("❌ Error parsing flash deals as direct list: ${e.message}")
                    try {
                        val apiResponse = json.decodeFromString<ApiResponse<List<Product>>>(response)
                        apiResponse.data ?: emptyList()
                    } catch (e2: Exception) {
                        println("❌ Error parsing flash deals as ApiResponse: ${e2.message}")
                        throw e2
                    }
                }

                Result.success(products)
            } else {
                val errorResponse = connection.errorStream?.bufferedReader()?.use { it.readText() }
                Result.failure(Exception("HTTP Error $responseCode: $errorResponse"))
            }
        } catch (e: Exception) {
            println("❌ Exception in getFlashDeals: ${e.message}")
            e.printStackTrace()
            Result.failure(e)
        }
    }
}