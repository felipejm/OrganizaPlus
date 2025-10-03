package com.joffer.organizeplus.utils

import android.app.DatePickerDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import kotlinx.datetime.LocalDate
import java.util.Calendar

@Composable
actual fun showDatePickerDialog(
    initialDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit,
    title: String,
    doneText: String,
    cancelText: String
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val year = initialDate?.year ?: calendar.get(Calendar.YEAR)
    val month = (initialDate?.monthNumber ?: calendar.get(Calendar.MONTH) + 1) - 1
    val day = initialDate?.dayOfMonth ?: calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDay ->
            val newDate = LocalDate(selectedYear, selectedMonth + 1, selectedDay)
            onDateSelected(newDate)
        },
        year,
        month,
        day
    )

    datePickerDialog.show()
}
