package com.joffer.organizeplus.features.duty.list.domain

import com.joffer.organizeplus.common.constants.CategoryConstants

sealed class DutyCategoryFilter(val categoryName: String) {
    data object All : DutyCategoryFilter("All")
    data object Personal : DutyCategoryFilter(CategoryConstants.PERSONAL)
    data object Company : DutyCategoryFilter(CategoryConstants.COMPANY)
    data class Custom(val name: String) : DutyCategoryFilter(name)

    companion object {
        fun fromCategoryName(name: String): DutyCategoryFilter {
            return when (name) {
                CategoryConstants.COMPANY -> Company
                CategoryConstants.PERSONAL -> Personal
                else -> All
            }
        }
    }
}
