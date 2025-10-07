package com.joffer.organizeplus.features.dashboard.data.remote

import io.github.aakira.napier.Napier
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.HttpResponse
import io.ktor.http.*

interface DashboardRemoteDataSource {
    suspend fun getDashboardData(): Result<DashboardRemoteResponse>
}

class DashboardRemoteDataSourceImpl(
    private val httpClient: HttpClient,
    private val baseUrl: String
) : DashboardRemoteDataSource {

    override suspend fun getDashboardData(): Result<DashboardRemoteResponse> {
        return try {
            val response = httpClient.get("$baseUrl/dashboard") {
                headers {
                    append(HttpHeaders.Accept, ContentType.Application.Json.toString())
                }
            }

            handleResponse(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    private suspend fun handleResponse(response: HttpResponse): Result<DashboardRemoteResponse> {
        return parseResponse(response)
    }

    private suspend fun parseResponse(response: HttpResponse): Result<DashboardRemoteResponse> {
        return try {
            val dashboardData = response.body<DashboardRemoteResponse>()
            Result.success(dashboardData)
        } catch (e: Exception) {
            Napier.e("Failed to parse response: ${e.message}", e, tag = TAG)
            Result.failure(Exception("Failed to parse dashboard data: ${e.message}"))
        }
    }

    private fun handleException(exception: Exception): Result<DashboardRemoteResponse> {
        Napier.e("Network request failed: ${exception.message}", exception, tag = TAG)
        return Result.failure(exception)
    }

    companion object {
        private const val TAG = "DashboardRemoteDataSource"
    }
}
