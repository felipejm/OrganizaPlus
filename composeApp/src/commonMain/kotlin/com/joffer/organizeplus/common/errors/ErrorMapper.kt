package com.joffer.organizeplus.common.errors

import org.jetbrains.compose.resources.StringResource
import organizeplus.composeapp.generated.resources.Res
import organizeplus.composeapp.generated.resources.error_constraint_violation
import organizeplus.composeapp.generated.resources.error_database_not_initialized
import organizeplus.composeapp.generated.resources.error_database_unknown
import organizeplus.composeapp.generated.resources.error_file_not_found
import organizeplus.composeapp.generated.resources.error_file_unknown
import organizeplus.composeapp.generated.resources.error_forbidden
import organizeplus.composeapp.generated.resources.error_generic_custom
import organizeplus.composeapp.generated.resources.error_generic_unknown
import organizeplus.composeapp.generated.resources.error_image_processing_failed
import organizeplus.composeapp.generated.resources.error_insufficient_storage
import organizeplus.composeapp.generated.resources.error_invalid_file_format
import organizeplus.composeapp.generated.resources.error_invalid_format
import organizeplus.composeapp.generated.resources.error_network_unknown
import organizeplus.composeapp.generated.resources.error_no_internet
import organizeplus.composeapp.generated.resources.error_no_text_detected
import organizeplus.composeapp.generated.resources.error_not_found
import organizeplus.composeapp.generated.resources.error_ocr_unknown
import organizeplus.composeapp.generated.resources.error_out_of_range
import organizeplus.composeapp.generated.resources.error_permission_denied
import organizeplus.composeapp.generated.resources.error_record_not_found
import organizeplus.composeapp.generated.resources.error_server_error
import organizeplus.composeapp.generated.resources.error_server_unavailable
import organizeplus.composeapp.generated.resources.error_timeout
import organizeplus.composeapp.generated.resources.error_transaction_failed
import organizeplus.composeapp.generated.resources.error_unauthorized
import organizeplus.composeapp.generated.resources.error_unsupported_image_format
import organizeplus.composeapp.generated.resources.error_validation_custom

object ErrorMapper {
    
    fun getMessageResource(error: AppError): StringResource = when (error) {
        is AppError.Network.NoInternetConnection -> Res.string.error_no_internet
        is AppError.Network.ServerUnavailable -> Res.string.error_server_unavailable
        is AppError.Network.Timeout -> Res.string.error_timeout
        is AppError.Network.Unauthorized -> Res.string.error_unauthorized
        is AppError.Network.Forbidden -> Res.string.error_forbidden
        is AppError.Network.NotFound -> Res.string.error_not_found
        is AppError.Network.ServerError -> Res.string.error_server_error
        is AppError.Network.Unknown -> Res.string.error_network_unknown
        
        is AppError.Database.DatabaseNotInitialized -> Res.string.error_database_not_initialized
        is AppError.Database.RecordNotFound -> Res.string.error_record_not_found
        is AppError.Database.ConstraintViolation -> Res.string.error_constraint_violation
        is AppError.Database.TransactionFailed -> Res.string.error_transaction_failed
        is AppError.Database.Unknown -> Res.string.error_database_unknown
        
        is AppError.FileSystem.FileNotFound -> Res.string.error_file_not_found
        is AppError.FileSystem.PermissionDenied -> Res.string.error_permission_denied
        is AppError.FileSystem.InsufficientStorage -> Res.string.error_insufficient_storage
        is AppError.FileSystem.InvalidFileFormat -> Res.string.error_invalid_file_format
        is AppError.FileSystem.Unknown -> Res.string.error_file_unknown
        
        is AppError.OCR.NoTextDetected -> Res.string.error_no_text_detected
        is AppError.OCR.ImageProcessingFailed -> Res.string.error_image_processing_failed
        is AppError.OCR.UnsupportedImageFormat -> Res.string.error_unsupported_image_format
        is AppError.OCR.Unknown -> Res.string.error_ocr_unknown
        
        is AppError.Validation.EmptyField -> Res.string.error_validation_custom
        is AppError.Validation.InvalidFormat -> Res.string.error_invalid_format
        is AppError.Validation.OutOfRange -> Res.string.error_out_of_range
        is AppError.Validation.Custom -> Res.string.error_validation_custom
        
        is AppError.Generic.Unknown -> Res.string.error_generic_unknown
        is AppError.Generic.Custom -> Res.string.error_generic_custom
    }
    
    fun getTitleResource(error: AppError): StringResource = when (error) {
        is AppError.Network -> Res.string.error_network_unknown
        is AppError.Database -> Res.string.error_database_unknown
        is AppError.FileSystem -> Res.string.error_file_unknown
        is AppError.OCR -> Res.string.error_ocr_unknown
        is AppError.Validation -> Res.string.error_validation_custom
        is AppError.Generic -> Res.string.error_generic_unknown
    }
}
