package com.joffer.organizeplus.features.dashboard.domain.entities

import com.joffer.organizeplus.features.duty.occurrence.domain.entities.DutyOccurrence

data class DutyWithLastOccurrence(
    val duty: Duty,
    val lastOccurrence: DutyOccurrence?,
    val hasCurrentMonthOccurrence: Boolean = false
)
