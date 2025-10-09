package com.joffer.organizeplus.features.onboarding.data.remote

import com.joffer.organizeplus.BuildConfig
import com.joffer.organizeplus.features.onboarding.domain.entities.SignInError
import com.joffer.organizeplus.features.onboarding.domain.entities.SignUpError
import com.joffer.organizeplus.features.onboarding.domain.usecases.SignInException
import com.joffer.organizeplus.features.onboarding.domain.usecases.SignUpException
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.Serializable

class AuthRemoteDataSourceImpl(
    private val httpClient: HttpClient,
    private val baseUrl: String = BuildConfig.API_BASE_URL
) : AuthRemoteDataSource {

    override suspend fun signUp(email: String, password: String): Result<AuthTokens> {
        return try {
            val response: HttpResponse = httpClient.post("$baseUrl/auth/signup") {
                setBody(SignUpRequestRemote(email, password))
            }

            if (response.status.value in 200..299) {
                val authResponse = response.body<AuthResponseRemote>()
                Result.success(
                    AuthTokens(
                        userId = authResponse.userId ?: "",
                        email = authResponse.email ?: email,
                        accessToken = authResponse.accessToken ?: "",
                        refreshToken = authResponse.refreshToken ?: "",
                        idToken = authResponse.idToken ?: "",
                        expiresIn = authResponse.expiresIn ?: 3600
                    )
                )
            } else {
                val errorResponse = try {
                    response.body<ErrorResponseRemote>()
                } catch (e: Exception) {
                    ErrorResponseRemote("SIGNUP_FAILED", "Sign up failed")
                }

                val error = when (errorResponse.error) {
                    "EMAIL_ALREADY_EXISTS" -> SignUpError.EMAIL_ALREADY_REGISTERED
                    "INVALID_PASSWORD" -> SignUpError.PASSWORD_TOO_SHORT
                    else -> SignUpError.UNKNOWN
                }
                Result.failure(SignUpException(error))
            }
        } catch (e: Exception) {
            Napier.e("Sign up failed: ${e.message}", e, tag = TAG)
            Result.failure(SignUpException(SignUpError.UNKNOWN))
        }
    }

    override suspend fun signIn(email: String, password: String): Result<AuthTokens> {
        return try {
            val response: HttpResponse = httpClient.post("$baseUrl/auth/signin") {
                contentType(ContentType.Application.Json)
                setBody(SignInRequestRemote(email, password))
            }

            if (response.status.value in 200..299) {
                val authResponse = response.body<AuthResponseRemote>()
                Result.success(
                    AuthTokens(
                        userId = authResponse.userId ?: "",
                        email = authResponse.email ?: email,
                        accessToken = authResponse.accessToken ?: "",
                        refreshToken = authResponse.refreshToken ?: "",
                        idToken = authResponse.idToken ?: "",
                        expiresIn = authResponse.expiresIn ?: 3600
                    )
                )
            } else {
                val errorResponse = try {
                    response.body<ErrorResponseRemote>()
                } catch (e: Exception) {
                    ErrorResponseRemote("SIGNIN_FAILED", "Sign in failed")
                }

                val error = when (errorResponse.error) {
                    "INVALID_CREDENTIALS" -> SignInError.INVALID_PASSWORD
                    "USER_NOT_FOUND" -> SignInError.EMAIL_NOT_REGISTERED
                    else -> SignInError.UNKNOWN
                }
                Result.failure(SignInException(error))
            }
        } catch (e: Exception) {
            Napier.e("Sign in failed: ${e.message}", e, tag = TAG)
            Result.failure(SignInException(SignInError.UNKNOWN))
        }
    }

    override suspend fun refreshToken(refreshToken: String): Result<AuthTokens> {
        return try {
            val response: HttpResponse = httpClient.post("$baseUrl/auth/refresh") {
                contentType(ContentType.Application.Json)
                setBody(RefreshTokenRequestRemote(refreshToken))
            }

            if (response.status.value in 200..299) {
                val authResponse = response.body<AuthResponseRemote>()
                Result.success(
                    AuthTokens(
                        userId = authResponse.userId ?: "",
                        email = authResponse.email ?: "",
                        accessToken = authResponse.accessToken ?: "",
                        refreshToken = refreshToken, // Keep the same refresh token
                        idToken = authResponse.idToken ?: "",
                        expiresIn = authResponse.expiresIn ?: 3600
                    )
                )
            } else {
                Result.failure(Exception("Token refresh failed"))
            }
        } catch (e: Exception) {
            Napier.e("Token refresh failed: ${e.message}", e, tag = TAG)
            Result.failure(e)
        }
    }

    override suspend fun signOut(accessToken: String): Result<Unit> {
        return try {
            val response: HttpResponse = httpClient.post("$baseUrl/auth/signout") {
                contentType(ContentType.Application.Json)
                setBody(SignOutRequestRemote(accessToken))
            }

            if (response.status.value in 200..299) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Sign out failed"))
            }
        } catch (e: Exception) {
            Napier.e("Sign out failed: ${e.message}", e, tag = TAG)
            Result.failure(e)
        }
    }

    companion object {
        private const val TAG = "AuthRemoteDataSource"
    }
}

// Remote DTOs
@Serializable
data class SignUpRequestRemote(
    val email: String,
    val password: String
)

@Serializable
data class SignInRequestRemote(
    val email: String,
    val password: String
)

@Serializable
data class RefreshTokenRequestRemote(
    val refreshToken: String
)

@Serializable
data class SignOutRequestRemote(
    val accessToken: String
)

@Serializable
data class AuthResponseRemote(
    val userId: String? = null,
    val email: String? = null,
    val accessToken: String? = null,
    val refreshToken: String? = null,
    val idToken: String? = null,
    val expiresIn: Int? = null,
    val tokenType: String? = null
)

@Serializable
data class ErrorResponseRemote(
    val error: String,
    val message: String
)
