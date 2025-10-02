package com.joffer.organizeplus.features.duty.list.domain

import com.joffer.organizeplus.common.constants.CategoryConstants

enum class DutyCategoryFilter(val categoryName: String) {
    Personal(CategoryConstants.PERSONAL),
    Company(CategoryConstants.COMPANY)
}
