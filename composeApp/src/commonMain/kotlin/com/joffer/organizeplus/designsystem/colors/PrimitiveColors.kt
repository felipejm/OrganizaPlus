package com.joffer.organizeplus.designsystem.colors

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * Primitive colors serve as low-level tokens that are used to further define semantic tokens.
 * Unlike semantic tokens, primitive colors are not intended for direct use in UI design.
 * They are defined in a neutral, agnostic manner that describes the color itself rather than
 * its usage or meaning within the design.
 *
 * These colors are based on the actual color values currently used in the OrganizePlus app.
 */
@Immutable
object PrimitiveColors {

    // ===== NEUTRAL PRIMITIVES (Dark Theme) =====
    val black = Color(0xFF000000)
    val white = Color(0xFFFFFFFF)
    
    // Dark theme background colors (matching image)
    val darkBackground = Color(0xFF1A1D29) // Main background
    val darkSurface = Color(0xFF252836) // Card/surface background
    val darkSurfaceVariant = Color(0xFF3A3D4A) // Circle backgrounds
    
    // Text colors (matching image)
    val lightGrey = Color(0xFFF7F8F9)
    val textPrimary = Color(0xFFFFFFFF) // White text
    val textSecondary = Color(0xFFB0B0B0) // Light grey text

    // Neutral grays (dark theme adjusted)
    val neutral50 = Color(0xFFFCFCFC)
    val neutral100 = Color(0xFFFAFAFA)
    val neutral200 = Color(0xFFF5F5F5)
    val neutral300 = Color(0xFFE1E1E1)
    val neutral400 = Color(0xFFC2C2C2)
    val neutral500 = Color(0xFF8F8F8F)
    val neutral600 = Color(0xFF4F4F4F)
    val neutral700 = Color(0xFF1F1F1F)

    // ===== PRIMARY RED PRIMITIVES =====
    // Based on the original primary color palette
    val primary50 = Color(0xFFFFF8F0) // Almost white with orange tint
    val primary100 = Color(0xFFFFE0B2) // Lightest orange
    val primary200 = Color(0xFFFDCB6E) // Very light orange
    val primary300 = Color(0xFFF39C12) // Light orange
    val primary400 = Color(0xFFE67E22) // Orange-red
    val primary500 = Color(0xFFE74C3C) // Main red (#E74C3C)
    val primary600 = Color(0xFFD63031) // Dark red
    val primary700 = Color(0xFFC0392B) // Darker red

    // ===== AUXILIARY ORANGE PRIMITIVES =====
    // Based on the original auxiliary color palette
    val auxiliary50 = Color(0xFFFFF8F6)
    val auxiliary100 = Color(0xFFFFF2F2)
    val auxiliary200 = Color(0xFFFFE8DE)
    val auxiliary300 = Color(0xFFFFD1B0)
    val auxiliary400 = Color(0xFFFFB586)
    val auxiliary500 = Color(0xFFFFA447)
    val auxiliary600 = Color(0xFFFB815A)
    val auxiliary700 = Color(0xFFF36C3F)

    // ===== DANGER RED PRIMITIVES =====
    // Based on the original danger color palette
    val danger50 = Color(0xFFFFF3F3)
    val danger100 = Color(0xFFFFE7E2)
    val danger200 = Color(0xFFFFBEBF)
    val danger300 = Color(0xFFFF9B7A)
    val danger400 = Color(0xFFFE8055)
    val danger500 = Color(0xFFFB612F)
    val danger600 = Color(0xFFF54A2C)
    val danger700 = Color(0xFFE42312)

    // ===== WARNING YELLOW PRIMITIVES =====
    // Based on the original warning color palette
    val warning50 = Color(0xFFFFFAF3)
    val warning100 = Color(0xFFFFF6D2)
    val warning200 = Color(0xFFFFF1BE)
    val warning300 = Color(0xFFFFED83)
    val warning400 = Color(0xFFFFE16D)
    val warning500 = Color(0xFFFFC542)
    val warning600 = Color(0xFFF5B42C)
    val warning700 = Color(0xFFF29D0E)

    // ===== SUCCESS GREEN PRIMITIVES =====
    // Based on the original success color palette
    val success50 = Color(0xFFF0FBF7)
    val success100 = Color(0xFFE0F7EB)
    val success200 = Color(0xFFC5EFD9)
    val success300 = Color(0xFFA1E2BF)
    val success400 = Color(0xFF80D4A4)
    val success500 = Color(0xFF63C684)
    val success600 = Color(0xFF44B76C)
    val success700 = Color(0xFF1E9E4B)

    // ===== INFO BLUE PRIMITIVES =====
    // Based on the original info color palette
    val info50 = Color(0xFFF0F8FF)
    val info100 = Color(0xFFE4F3FF)
    val info200 = Color(0xFFC7E4FF)
    val info300 = Color(0xFF91CFFF)
    val info400 = Color(0xFF74BDFF)
    val info500 = Color(0xFF4DA7FF)
    val info600 = Color(0xFF2E8CF0)
    val info700 = Color(0xFF1A70D2)

    // ===== ACCENT COLORS (Matching Image) =====
    val personalAccent = Color(0xFF6A5ACD) // Blue accent for Personal (SlateBlue)
    val companyAccent = Color(0xFFFF8C00) // Orange accent for Company (DarkOrange)
    
    // ===== ADDITIONAL PRIMITIVES =====
    // Based on specific colors used in the app
    val teal = Color(0xFF42D9E4) // Personal accent (legacy)
    val tealLight = Color(0xFFE6F4FF) // Light teal
    val amber = Color(0xFFFF9800) // Company accent (legacy)
    val amberLight = Color(0xFFFFF3E0) // Light amber
    val overdueRed = Color(0xFFE53935) // Red for overdue
    val amountPaidGreen = Color(0xFF2E7D32) // Green for amount paid
    val summaryBackground = Color(0xFFFDECEC) // Soft tinted background for summary

    // Utility primitives
    val transparent = Color(0x00000000)
    val scrim = Color(0x80000000)
}
