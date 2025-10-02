package com.joffer.organizeplus.designsystem.components

import kotlinx.datetime.LocalDate

expect fun showDatePicker(
    initialDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit
)

expect fun showTimePicker(
    initialHour: Int,
    initialMinute: Int,
    onTimeSelected: (Int, Int) -> Unit
)
