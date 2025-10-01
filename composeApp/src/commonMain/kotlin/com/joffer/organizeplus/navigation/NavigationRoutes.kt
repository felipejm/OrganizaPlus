package com.joffer.organizeplus.navigation

object NavigationRoutes {
    const val DASHBOARD = "dashboard"
    const val CREATE_DUTY = "create_duty"
    const val DUTIES = "duties/{category}"
    const val EDIT_DUTY = "edit_duty/{dutyId}"
    const val DUTY_OCCURRENCES = "duty_occurrences/{dutyId}"
    const val ADD_DUTY_OCCURRENCE = "add_duty_occurrence/{dutyId}"
    const val SETTINGS = "settings"

    fun duties(category: String): String {
        return "duties/$category"
    }

    fun editDuty(dutyId: String): String {
        return "edit_duty/$dutyId"
    }

    fun dutyOccurrences(dutyId: String): String {
        return "duty_occurrences/$dutyId"
    }

    fun addDutyOccurrence(dutyId: String): String {
        return "add_duty_occurrence/$dutyId"
    }
}
