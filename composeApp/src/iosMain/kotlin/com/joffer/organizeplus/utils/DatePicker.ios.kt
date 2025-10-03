package com.joffer.organizeplus.utils

import androidx.compose.runtime.Composable
import kotlinx.datetime.LocalDate
import platform.Foundation.*
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
        
        // Set initial date if provided
        initialDate?.let { localDate ->
            // Convert LocalDate to NSDate using proper epoch conversion
            val calendar = NSCalendar.currentCalendar
            val components = NSDateComponents()
            components.year = localDate.year.toLong()
            components.month = localDate.monthNumber.toLong()
            components.day = localDate.dayOfMonth.toLong()
            
            val nsDate = calendar.dateFromComponents(components)
            if (nsDate != null) {
                date = nsDate
            }
        }
    }

    val alertController = UIAlertController.alertControllerWithTitle(
        title = "Select Date",
        message = "\n\n\n\n\n\n\n",
        preferredStyle = UIAlertControllerStyleAlert
    ).apply {
        view.addSubview(datePicker)
        NSLayoutConstraint.activateConstraints(
            listOf(
                datePicker.centerXAnchor.constraintEqualToAnchor(view.centerXAnchor),
                datePicker.topAnchor.constraintEqualToAnchor(view.topAnchor, constant = 60.0),
                datePicker.widthAnchor.constraintEqualToAnchor(view.widthAnchor, constant = -40.0)
            )
        )
    }

    alertController.addAction(
        UIAlertAction.actionWithTitle(
            title = "Done",
            style = UIAlertActionStyleDefault
        ) { _ ->
            val selectedNSDate = datePicker.date
            val calendar = NSCalendar.currentCalendar
            val components = calendar.components(
                NSCalendarUnitYear or NSCalendarUnitMonth or NSCalendarUnitDay,
                fromDate = selectedNSDate
            )
            
            val localDate = LocalDate(
                year = components.year.toInt(),
                monthNumber = components.month.toInt(),
                dayOfMonth = components.day.toInt()
            )
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

    // Get the current view controller and present the alert
    val rootViewController = UIApplication.sharedApplication.keyWindow?.rootViewController
    rootViewController?.presentViewController(
        alertController,
        animated = true,
        completion = null
    )
}
