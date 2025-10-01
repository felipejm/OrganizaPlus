package com.joffer.organizeplus.utils

import androidx.compose.runtime.Composable
import kotlinx.datetime.LocalDate
import platform.Foundation.NSDate
import platform.Foundation.NSTimeInterval
import platform.Foundation.timeIntervalSince1970
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
            // Convert LocalDate to NSDate
            val epochDays = it.toEpochDays()
            val timeInterval: NSTimeInterval = epochDays * 24.0 * 60.0 * 60.0
            date = NSDate(timeIntervalSinceReferenceDate = timeInterval - 978307200.0) // NSDate epoch is Jan 1, 2001
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
            // Access the property directly - timeIntervalSince1970 is a Double property
            val timeIntervalSeconds = selectedNSDate.timeIntervalSince1970
            val daysSinceEpoch = (timeIntervalSeconds / (24.0 * 60.0 * 60.0)).toInt()
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
