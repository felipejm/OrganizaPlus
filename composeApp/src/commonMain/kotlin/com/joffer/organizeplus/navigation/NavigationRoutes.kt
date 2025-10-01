package com.joffer.organizeplus.navigation

import kotlinx.serialization.Serializable

@Serializable
object Dashboard

@Serializable
object CreateDuty

@Serializable
data class CreateDutyWithCategory(val category: String)

@Serializable
data class Duties(val category: String)

@Serializable
data class EditDuty(val dutyId: Long)

@Serializable
data class DutyOccurrences(val dutyId: Long)

@Serializable
data class AddDutyOccurrence(val dutyId: Long)

@Serializable
object Settings

@Serializable
object DesignSystemCatalog
