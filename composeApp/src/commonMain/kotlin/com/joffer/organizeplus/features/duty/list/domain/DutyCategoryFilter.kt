package com.joffer.organizeplus.features.duty.list.domain

import com.joffer.organizeplus.common.constants.CategoryConstants

sealed class DutyCategoryFilter(val categoryName: String) {
    data object Personal : DutyCategoryFilter(CategoryConstants.PERSONAL)
    data object Company : DutyCategoryFilter(CategoryConstants.COMPANY)
}
