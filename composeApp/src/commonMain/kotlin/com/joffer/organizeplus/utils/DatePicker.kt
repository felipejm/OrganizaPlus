package com.joffer.organizeplus.utils

import androidx.compose.runtime.Composable
import kotlinx.datetime.LocalDate

@Composable
expect fun showDatePickerDialog(
    initialDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit,
    title: String = "Select Date",
    doneText: String = "Done",
    cancelText: String = "Cancel"
)
