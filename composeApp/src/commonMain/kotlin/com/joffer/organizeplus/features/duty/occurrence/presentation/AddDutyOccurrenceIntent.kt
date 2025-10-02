package com.joffer.organizeplus.features.duty.occurrence.presentation

import com.joffer.organizeplus.features.duty.occurrence.domain.entities.DutyOccurrenceFormField

sealed class AddDutyOccurrenceIntent {
    data class UpdateFormField(val field: DutyOccurrenceFormField, val value: Any) : AddDutyOccurrenceIntent()
    object SaveRecord : AddDutyOccurrenceIntent()
    object ClearError : AddDutyOccurrenceIntent()
    object Retry : AddDutyOccurrenceIntent()
}
