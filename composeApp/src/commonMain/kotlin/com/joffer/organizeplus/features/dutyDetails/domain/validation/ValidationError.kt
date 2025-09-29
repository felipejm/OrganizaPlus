package com.joffer.organizeplus.features.dutyDetails.domain.validation

sealed class ValidationError {
    object InvalidAmount : ValidationError()
    object InvalidDate : ValidationError()
    object BlankField : ValidationError()
}
