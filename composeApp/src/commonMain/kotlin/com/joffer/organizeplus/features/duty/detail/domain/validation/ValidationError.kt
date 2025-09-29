package com.joffer.organizeplus.features.duty.detail.domain.validation

sealed class ValidationError {
    object InvalidAmount : ValidationError()
    object InvalidDate : ValidationError()
    object BlankField : ValidationError()
}
