package com.joffer.organizeplus.designsystem.colors

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
object ColorScheme {
    
    // ===== NEUTRAL COLORS =====
    val black = Color(0xFF000000)
    val white = Color(0xFFFFFFFF)
    val neutral700 = Color(0xFF1F1F1F)
    val neutral600 = Color(0xFF4F4F4F)
    val neutral500 = Color(0xFF8F8F8F)
    val neutral400 = Color(0xFFC2C2C2)
    val neutral300 = Color(0xFFE1E1E1)
    val neutral200 = Color(0xFFF5F5F5)
    val neutral100 = Color(0xFFFAFAFA)
    val neutral50 = Color(0xFFFCFCFC)
    
    // ===== PRIMARY COLORS =====
    val primary700 = Color(0xFFC0392B)  // Darker red
    val primary600 = Color(0xFFD63031)  // Dark red
    val primary500 = Color(0xFFE74C3C)  // Main red (#E74C3C)
    val primary400 = Color(0xFFE67E22)  // Orange-red
    val primary300 = Color(0xFFF39C12)  // Light orange
    val primary200 = Color(0xFFFDCB6E)  // Very light orange
    val primary100 = Color(0xFFFFE0B2)  // Lightest orange
    val primary50 = Color(0xFFFFF8F0)   // Almost white with orange tint
    
    // ===== AUXILIARY COLORS =====
    val auxiliary700 = Color(0xFFF36C3F)
    val auxiliary600 = Color(0xFFFB815A)
    val auxiliary500 = Color(0xFFFFA447)
    val auxiliary400 = Color(0xFFFFB586)
    val auxiliary300 = Color(0xFFFFD1B0)
    val auxiliary200 = Color(0xFFFFE8DE)
    val auxiliary100 = Color(0xFFFFF2F2)
    val auxiliary50 = Color(0xFFFFF8F6)
    
    // ===== SEMANTIC COLORS - DANGER =====
    val danger700 = Color(0xFFE42312)
    val danger600 = Color(0xFFF54A2C)
    val danger500 = Color(0xFFFB612F)
    val danger400 = Color(0xFFFE8055)
    val danger300 = Color(0xFFFF9B7A)
    val danger200 = Color(0xFFFFBEBF)
    val danger100 = Color(0xFFFFE7E2)
    val danger50 = Color(0xFFFFF3F3)
    
    // ===== SEMANTIC COLORS - WARNING =====
    val warning700 = Color(0xFFF29D0E)
    val warning600 = Color(0xFFF5B42C)
    val warning500 = Color(0xFFFFC542)
    val warning400 = Color(0xFFFFE16D)
    val warning300 = Color(0xFFFFED83)
    val warning200 = Color(0xFFFFF1BE)
    val warning100 = Color(0xFFFFF6D2)
    val warning50 = Color(0xFFFFFAF3)
    
    // ===== SEMANTIC COLORS - SUCCESS =====
    val success700 = Color(0xFF1E9E4B)
    val success600 = Color(0xFF44B76C)
    val success500 = Color(0xFF63C684)
    val success400 = Color(0xFF80D4A4)
    val success300 = Color(0xFFA1E2BF)
    val success200 = Color(0xFFC5EFD9)
    val success100 = Color(0xFFE0F7EB)
    val success50 = Color(0xFFF0FBF7)
    
    // ===== SEMANTIC COLORS - INFO =====
    val info700 = Color(0xFF1A70D2)
    val info600 = Color(0xFF2E8CF0)
    val info500 = Color(0xFF4DA7FF)
    val info400 = Color(0xFF74BDFF)
    val info300 = Color(0xFF91CFFF)
    val info200 = Color(0xFFC7E4FF)
    val info100 = Color(0xFFE4F3FF)
    val info50 = Color(0xFFF0F8FF)
    
    // ===== MATERIAL DESIGN 3 MAPPING =====
    // Primary colors mapped to new primary palette
    val primary = primary500
    val onPrimary = white
    val primaryContainer = primary100
    val onPrimaryContainer = primary700
    
    // Secondary colors mapped to auxiliary palette
    val secondary = auxiliary500
    val onSecondary = white
    val secondaryContainer = auxiliary100
    val onSecondaryContainer = auxiliary700
    
    // Tertiary colors mapped to info palette
    val tertiary = info500
    val onTertiary = white
    val tertiaryContainer = info100
    val onTertiaryContainer = info700
    
    // Error colors mapped to danger palette
    val error = danger500
    val onError = white
    val errorContainer = danger100
    val onErrorContainer = danger700
    
    // Background and surface colors
    val background = neutral50
    val onBackground = neutral700
    val surface = white
    val onSurface = neutral700
    val surfaceVariant = neutral100
    val onSurfaceVariant = neutral600
    
    // Outline colors
    val outline = neutral300
    val outlineVariant = neutral200
    
    // Additional surface colors
    val scrim = Color(0x80000000)
    val inverseSurface = neutral700
    val inverseOnSurface = white
    val inversePrimary = primary300
    
    val surfaceDim = neutral200
    val surfaceBright = white
    val surfaceContainerLowest = white
    val surfaceContainerLow = neutral50
    val surfaceContainer = neutral100
    val surfaceContainerHigh = neutral200
    val surfaceContainerHighest = neutral300
    
    // ===== FORM COLORS =====
    val formLabel = neutral700
    val formText = neutral700
    val formPlaceholder = neutral500
    val formBorder = neutral300
    val formBackground = white
    val formSecondaryText = neutral600
    val formIcon = neutral500
    
    // ===== LEGACY COLORS (for backward compatibility) =====
    val amber = warning500
    val onAmber = white
    val green = success500
    val onGreen = white
    val iconOrange = auxiliary500
    val iconBlue = info500
    val iconOrangeContainer = auxiliary100
    val iconBlueContainer = info100
    
    // HTML colors (keeping for compatibility)
    val htmlPrimary = info500
    val htmlAmber = warning500
    
    // ===== DASHBOARD SPECIFIC COLORS =====
    // Personal accent colors
    val personalAccent = Color(0xFF42D9E4)  // Teal
    val personalAccentLight = Color(0xFFE6F4FF)  // Light teal
    val personalBackground = Color(0xFFF0FAFA)  // Very light teal tint
    
    // Company accent colors
    val companyAccent = Color(0xFFFF9800)  // Amber
    val companyAccentLight = Color(0xFFFFF3E0)  // Light amber
    val companyBackground = Color(0xFFFFF8F0)  // Very light amber tint
    
    // Dashboard specific colors
    val overdueText = Color(0xFFE53935)  // Red for overdue
    val amountPaid = Color(0xFF2E7D32)  // Green for amount paid
    val taskDoneCount = personalAccent  // Teal for task count
    val summaryMonthLabel = overdueText  // Red for month label
    val summaryBackground = Color(0xFFFDECEC)  // Soft tinted background for summary
    
    // Text colors
    val sectionHeader = neutral700  // #1F1F1F
    val dutyTitle = neutral700  // #1F1F1F
    val dutyMeta = neutral600  // #6B6B6B
    val lastOccurrence = overdueText  // #E53935 for overdue
    
    // Background colors
    val backgroundNeutral = neutral100  // #FAFAFA
    val cardBackground = white  // #FFFFFF
}
