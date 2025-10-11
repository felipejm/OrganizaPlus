package com.joffer.organizeplus.features.duty.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DutyListRemoteResponse(
    @SerialName("duties") val duties: List<DutyRemoteDto>
)

@Serializable
data class DutyDetailRemoteResponse(
    @SerialName("duty") val duty: DutyRemoteDto,
    @SerialName("occurrences") val occurrences: List<DutyOccurrenceRemoteDto>
)

@Serializable
data class DutyRemoteDto(
    @SerialName("id") val id: Long,
    @SerialName("title") val title: String,
    @SerialName("type") val type: String,
    @SerialName("categoryName") val categoryName: String,
    @SerialName("createdAt") val createdAt: String
)

@Serializable
data class DutyOccurrenceRemoteDto(
    @SerialName("id") val id: Long,
    @SerialName("dutyId") val dutyId: Long,
    @SerialName("amountPaid") val amountPaid: Double?,
    @SerialName("completedAt") val completedAt: String,
    @SerialName("notes") val notes: String?
)

@Serializable
data class CreateDutyRequest(
    @SerialName("id") val id: Long = 0,
    @SerialName("title") val title: String,
    @SerialName("type") val type: String,
    @SerialName("categoryName") val categoryName: String
)

@Serializable
data class CreateDutyResponse(
    @SerialName("duty") val duty: DutyRemoteDto
)
