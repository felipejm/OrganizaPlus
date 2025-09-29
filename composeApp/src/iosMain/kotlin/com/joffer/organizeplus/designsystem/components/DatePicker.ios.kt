package com.joffer.organizeplus.designsystem.components

import kotlinx.datetime.LocalDate

actual fun showDatePicker(
    initialDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit
) {
    // iOS implementation will be handled in the ViewModel
    // This is a placeholder for the expect/actual pattern
}

actual fun showTimePicker(
    initialHour: Int,
    initialMinute: Int,
    onTimeSelected: (Int, Int) -> Unit
) {
    // iOS implementation will be handled in the ViewModel
    // This is a placeholder for the expect/actual pattern
}


