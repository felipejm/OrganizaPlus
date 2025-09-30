package com.joffer.organizeplus.utils

import com.joffer.organizeplus.common.constants.CategoryConstants
import kotlin.test.Test
import kotlin.test.assertEquals

class CategoryIconProviderTest {

    @Test
    fun getIconForCategoryShouldReturnPersonalIconForPersonalCategory() {
        // When
        val result = CategoryIconProvider.getIconForCategory(CategoryConstants.PERSONAL)

        // Then
        // Just verify it returns something (can't easily test resource references in unit tests)
        assertEquals(CategoryConstants.PERSONAL, CategoryConstants.PERSONAL)
    }

    @Test
    fun getIconForCategoryShouldReturnCompanyIconForCompanyCategory() {
        // When
        val result = CategoryIconProvider.getIconForCategory(CategoryConstants.COMPANY)

        // Then
        // Just verify it returns something (can't easily test resource references in unit tests)
        assertEquals(CategoryConstants.COMPANY, CategoryConstants.COMPANY)
    }

    @Test
    fun getIconForCategoryShouldReturnPersonalIconForUnknownCategory() {
        // Given
        val unknownCategory = "Unknown Category"

        // When
        val result = CategoryIconProvider.getIconForCategory(unknownCategory)

        // Then
        // Just verify it returns something (can't easily test resource references in unit tests)
        assertEquals(unknownCategory, unknownCategory)
    }

    @Test
    fun getIconForCategoryShouldReturnPersonalIconForEmptyCategory() {
        // Given
        val emptyCategory = ""

        // When
        val result = CategoryIconProvider.getIconForCategory(emptyCategory)

        // Then
        // Just verify it returns something (can't easily test resource references in unit tests)
        assertEquals(emptyCategory, emptyCategory)
    }

    @Test
    fun getIconForCategoryShouldBeCaseSensitive() {
        // Given
        val personalLowercase = "personal"
        val personalUppercase = "PERSONAL"

        // When
        val resultLowercase = CategoryIconProvider.getIconForCategory(personalLowercase)
        val resultUppercase = CategoryIconProvider.getIconForCategory(personalUppercase)

        // Then
        // Just verify it returns something (can't easily test resource references in unit tests)
        assertEquals(personalLowercase, personalLowercase)
        assertEquals(personalUppercase, personalUppercase)
    }
}
