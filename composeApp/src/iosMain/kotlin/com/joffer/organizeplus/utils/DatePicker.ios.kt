package com.joffer.organizeplus.utils

import androidx.compose.runtime.Composable
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toNSDate
import platform.UIKit.*

@Composable
actual fun showDatePickerDialog(
    initialDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit
) {
    val datePicker = UIDatePicker().apply {
        datePickerMode = UIDatePickerMode.UIDatePickerModeDate
        preferredDatePickerStyle = UIDatePickerStyle.UIDatePickerStyleWheels
        translatesAutoresizingMaskIntoConstraints = false
        initialDate?.let {
            date = it.toNSDate()
        }
    }

    val alertController = UIAlertController.alertControllerWithTitle(
        title = "Select Date",
        message = "\n\n\n\n\n\n\n",
        preferredStyle = UIAlertControllerStyleAlert
    ).apply {
        view.addSubview(datePicker)
        view.addConstraints(
            listOf(
                datePicker.centerXAnchor.constraintEqualToAnchor(view.centerXAnchor),
                datePicker.topAnchor.constraintEqualToAnchor(view.topAnchor, constant = 10.0),
                datePicker.widthAnchor.constraintEqualToAnchor(view.widthAnchor, constant = -20.0)
            )
        )
    }

    alertController.addAction(
        UIAlertAction.actionWithTitle(
            title = "Done",
            style = UIAlertActionStyleDefault
        ) { _ ->
            val selectedNSDate = datePicker.date
            val timeInterval = selectedNSDate.timeIntervalSince1970
            val daysSinceEpoch = (timeInterval / (24 * 60 * 60)).toLong()
            val localDate = LocalDate.fromEpochDays(daysSinceEpoch)
            onDateSelected(localDate)
        }
    )

    alertController.addAction(
        UIAlertAction.actionWithTitle(
            title = "Cancel",
            style = UIAlertActionStyleCancel
        ) { _ ->
            // Do nothing on cancel
        }
    )

    UIApplication.sharedApplication.keyWindow?.rootViewController?.presentViewController(
        alertController,
        animated = true,
        completion = null
    )
}

