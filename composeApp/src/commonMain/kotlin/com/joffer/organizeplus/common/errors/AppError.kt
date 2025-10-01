package com.joffer.organizeplus.common.errors

/**
 * Sealed class representing all possible errors in the application
 */
sealed class AppError : Throwable() {

    // Network errors
    sealed class Network : AppError() {
        object NoInternetConnection : Network()
        object ServerUnavailable : Network()
        object Timeout : Network()
        object Unauthorized : Network()
        object Forbidden : Network()
        object NotFound : Network()
        data class ServerError(val code: Int, override val message: String) : Network()
        data class Unknown(override val message: String) : Network()
    }

    // Database errors
    sealed class Database : AppError() {
        object DatabaseNotInitialized : Database()
        object RecordNotFound : Database()
        object ConstraintViolation : Database()
        object TransactionFailed : Database()
        data class Unknown(override val message: String) : Database()
    }

    // File system errors
    sealed class FileSystem : AppError() {
        object FileNotFound : FileSystem()
        object PermissionDenied : FileSystem()
        object InsufficientStorage : FileSystem()
        object InvalidFileFormat : FileSystem()
        data class Unknown(override val message: String) : FileSystem()
    }

    // OCR errors
    sealed class OCR : AppError() {
        object NoTextDetected : OCR()
        object ImageProcessingFailed : OCR()
        object UnsupportedImageFormat : OCR()
        data class Unknown(override val message: String) : OCR()
    }

    // Validation errors
    sealed class Validation : AppError() {
        object EmptyField : Validation()
        object InvalidFormat : Validation()
        object OutOfRange : Validation()
        data class Custom(override val message: String) : Validation()
    }

    // Generic errors
    sealed class Generic : AppError() {
        object Unknown : Generic()
        data class Custom(override val message: String) : Generic()
    }
}
