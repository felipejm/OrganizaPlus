package com.joffer.organizeplus.features.duty.data.remote

import io.github.aakira.napier.Napier
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.HttpResponse
import io.ktor.http.*

interface DutyRemoteDataSource {
    suspend fun getAllDuties(): Result<DutyListRemoteResponse>
    suspend fun getDutyById(id: Long): Result<DutyDetailRemoteResponse>
    suspend fun createDuty(request: CreateDutyRequest): Result<DutyRemoteDto>
    suspend fun deleteDuty(id: Long): Result<Unit>
}

class DutyRemoteDataSourceImpl(
    private val httpClient: HttpClient,
    private val baseUrl: String
) : DutyRemoteDataSource {

    override suspend fun getAllDuties(): Result<DutyListRemoteResponse> {
        return try {
            val response = httpClient.get("$baseUrl/duties") {
                headers {
                    append(HttpHeaders.Accept, ContentType.Application.Json.toString())
                }
            }
            parseResponse(response)
        } catch (e: Exception) {
            handleException(e, "getAllDuties")
        }
    }

    override suspend fun getDutyById(id: Long): Result<DutyDetailRemoteResponse> {
        return try {
            val response = httpClient.get("$baseUrl/duties/$id") {
                headers {
                    append(HttpHeaders.Accept, ContentType.Application.Json.toString())
                }
            }
            parseResponse(response)
        } catch (e: Exception) {
            handleException(e, "getDutyById")
        }
    }

    override suspend fun createDuty(request: CreateDutyRequest): Result<DutyRemoteDto> {
        return try {
            val response = httpClient.post("$baseUrl/duties") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }

            if (response.status.isSuccess()) {
                try {
                    val createResponse = response.body<CreateDutyResponse>()
                    Result.success(createResponse.duty)
                } catch (e: Exception) {
                    Napier.e("Failed to parse createDuty response: ${e.message}", e, tag = TAG)
                    Result.failure(Exception("Failed to parse response: ${e.message}"))
                }
            } else {
                val error = "createDuty failed: ${response.status.description}"
                Napier.e(error, tag = TAG)
                Result.failure(Exception(error))
            }
        } catch (e: Exception) {
            handleException(e, "createDuty")
        }
    }

    override suspend fun deleteDuty(id: Long): Result<Unit> {
        return try {
            val response = httpClient.delete("$baseUrl/duties/$id")
            if (response.status.isSuccess()) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Failed to delete duty: ${response.status.description}"))
            }
        } catch (e: Exception) {
            handleException(e, "deleteDuty")
        }
    }

    private suspend inline fun <reified T> parseResponse(response: HttpResponse): Result<T> {
        return try {
            val data = response.body<T>()
            Result.success(data)
        } catch (e: Exception) {
            Napier.e("Failed to parse response", e, tag = TAG)
            Result.failure(Exception("Failed to parse response: ${e.message}"))
        }
    }

    private fun <T> handleException(exception: Exception, operation: String): Result<T> {
        Napier.e("$operation failed: ${exception.message}", exception, tag = TAG)
        return Result.failure(exception)
    }

    companion object {
        private const val TAG = "DutyRemoteDataSource"
    }
}
