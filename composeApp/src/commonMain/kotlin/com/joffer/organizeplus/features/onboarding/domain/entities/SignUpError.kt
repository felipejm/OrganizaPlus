package com.joffer.organizeplus.features.onboarding.domain.entities

enum class SignUpError {
    EMAIL_EMPTY,
    EMAIL_INVALID,
    PASSWORD_EMPTY,
    PASSWORD_TOO_SHORT,
    PASSWORDS_DO_NOT_MATCH,
    EMAIL_ALREADY_REGISTERED,
    UNKNOWN
}
