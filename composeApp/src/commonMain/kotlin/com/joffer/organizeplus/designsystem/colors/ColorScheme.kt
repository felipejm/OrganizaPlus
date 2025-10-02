package com.joffer.organizeplus.designsystem.colors

import androidx.compose.runtime.Immutable

/**
 * Main color scheme that provides access to both primitive and semantic colors.
 * This class maintains backward compatibility while delegating to the new color structure.
 */
@Immutable
object ColorScheme {

    // ===== PRIMITIVE COLORS DELEGATION =====
    // Delegate to PrimitiveColors for low-level color tokens
    val black = PrimitiveColors.black
    val white = PrimitiveColors.white
    val neutral700 = PrimitiveColors.neutral700
    val neutral600 = PrimitiveColors.neutral600
    val neutral500 = PrimitiveColors.neutral500
    val neutral400 = PrimitiveColors.neutral400
    val neutral300 = PrimitiveColors.neutral300
    val neutral200 = PrimitiveColors.neutral200
    val neutral100 = PrimitiveColors.neutral100
    val neutral50 = PrimitiveColors.neutral50

    // ===== PRIMARY COLORS DELEGATION =====
    val primary700 = PrimitiveColors.primary700
    val primary600 = PrimitiveColors.primary600
    val primary500 = PrimitiveColors.primary500
    val primary400 = PrimitiveColors.primary400
    val primary300 = PrimitiveColors.primary300
    val primary200 = PrimitiveColors.primary200
    val primary100 = PrimitiveColors.primary100
    val primary50 = PrimitiveColors.primary50

    // ===== AUXILIARY COLORS DELEGATION =====
    val auxiliary700 = PrimitiveColors.auxiliary700
    val auxiliary600 = PrimitiveColors.auxiliary600
    val auxiliary500 = PrimitiveColors.auxiliary500
    val auxiliary400 = PrimitiveColors.auxiliary400
    val auxiliary300 = PrimitiveColors.auxiliary300
    val auxiliary200 = PrimitiveColors.auxiliary200
    val auxiliary100 = PrimitiveColors.auxiliary100
    val auxiliary50 = PrimitiveColors.auxiliary50

    // ===== SEMANTIC COLORS - DANGER DELEGATION =====
    val danger700 = PrimitiveColors.danger700
    val danger600 = PrimitiveColors.danger600
    val danger500 = PrimitiveColors.danger500
    val danger400 = PrimitiveColors.danger400
    val danger300 = PrimitiveColors.danger300
    val danger200 = PrimitiveColors.danger200
    val danger100 = PrimitiveColors.danger100
    val danger50 = PrimitiveColors.danger50

    // ===== SEMANTIC COLORS - WARNING DELEGATION =====
    val warning700 = PrimitiveColors.warning700
    val warning600 = PrimitiveColors.warning600
    val warning500 = PrimitiveColors.warning500
    val warning400 = PrimitiveColors.warning400
    val warning300 = PrimitiveColors.warning300
    val warning200 = PrimitiveColors.warning200
    val warning100 = PrimitiveColors.warning100
    val warning50 = PrimitiveColors.warning50

    // ===== SEMANTIC COLORS - SUCCESS DELEGATION =====
    val success700 = PrimitiveColors.success700
    val success600 = PrimitiveColors.success600
    val success500 = PrimitiveColors.success500
    val success400 = PrimitiveColors.success400
    val success300 = PrimitiveColors.success300
    val success200 = PrimitiveColors.success200
    val success100 = PrimitiveColors.success100
    val success50 = PrimitiveColors.success50

    // ===== SEMANTIC COLORS - INFO DELEGATION =====
    val info700 = PrimitiveColors.info700
    val info600 = PrimitiveColors.info600
    val info500 = PrimitiveColors.info500
    val info400 = PrimitiveColors.info400
    val info300 = PrimitiveColors.info300
    val info200 = PrimitiveColors.info200
    val info100 = PrimitiveColors.info100
    val info50 = PrimitiveColors.info50

    // ===== MATERIAL DESIGN 3 MAPPING =====
    // Primary colors mapped to new primary palette
    val primary = SemanticColors.Foreground.brand
    val onPrimary = SemanticColors.OnBackground.onBrand
    val primaryContainer = SemanticColors.Background.brandContainer
    val onPrimaryContainer = SemanticColors.OnBackground.onBrandContainer

    // Secondary colors mapped to auxiliary palette
    val secondary = PrimitiveColors.auxiliary500
    val onSecondary = PrimitiveColors.white
    val secondaryContainer = PrimitiveColors.auxiliary100
    val onSecondaryContainer = PrimitiveColors.auxiliary700

    // Tertiary colors mapped to info palette
    val tertiary = SemanticColors.Foreground.info
    val onTertiary = PrimitiveColors.white
    val tertiaryContainer = SemanticColors.Background.info
    val onTertiaryContainer = SemanticColors.OnBackground.onInfo

    // Error colors mapped to danger palette
    val error = SemanticColors.Foreground.error
    val onError = PrimitiveColors.white
    val errorContainer = SemanticColors.Background.error
    val onErrorContainer = SemanticColors.OnBackground.onError

    // Background and surface colors
    val background = SemanticColors.Background.primary
    val onBackground = SemanticColors.OnBackground.primary
    val surface = SemanticColors.Background.surface
    val onSurface = SemanticColors.OnBackground.onSurface
    val surfaceVariant = SemanticColors.Background.surfaceVariant
    val onSurfaceVariant = SemanticColors.OnBackground.onSurfaceVariant

    // Outline colors
    val outline = SemanticColors.Border.outline
    val outlineVariant = SemanticColors.Border.outlineVariant

    // Additional surface colors
    val scrim = PrimitiveColors.scrim
    val inverseSurface = PrimitiveColors.neutral700
    val inverseOnSurface = PrimitiveColors.white
    val inversePrimary = PrimitiveColors.primary300

    val surfaceContainerLow = SemanticColors.Background.surfaceContainerLow

    // ===== FORM COLORS =====
    val formLabel = SemanticColors.Foreground.primary
    val formText = SemanticColors.Foreground.primary
    val formPlaceholder = SemanticColors.Foreground.tertiary
    val formBorder = SemanticColors.Border.primary
    val formBackground = SemanticColors.Background.primary
    val formSecondaryText = SemanticColors.Foreground.secondary
    val formIcon = SemanticColors.Foreground.tertiary

    // ===== LEGACY COLORS (for backward compatibility) =====
    val iconOrange = SemanticColors.Legacy.iconOrange
    val iconBlue = SemanticColors.Legacy.iconBlue
    val iconOrangeContainer = SemanticColors.Legacy.iconOrangeContainer
    val iconBlueContainer = SemanticColors.Legacy.iconBlueContainer

    // ===== DASHBOARD SPECIFIC COLORS =====
    // Personal accent colors
    val personalAccent = SemanticColors.Legacy.personalAccent
    val personalAccentLight = SemanticColors.Legacy.personalAccentLight

    // Company accent colors
    val companyAccent = SemanticColors.Legacy.companyAccent
    val companyAccentLight = SemanticColors.Legacy.companyAccentLight

    // Dashboard specific colors
    val overdueText = SemanticColors.Legacy.overdueText
    val amountPaid = SemanticColors.Legacy.amountPaid
    val summaryMonthLabel = SemanticColors.Legacy.summaryMonthLabel
    val summaryBackground = SemanticColors.Legacy.summaryBackground

    // Text colors
    val sectionHeader = SemanticColors.Legacy.sectionHeader
    val dutyTitle = SemanticColors.Legacy.dutyTitle
    val dutyMeta = SemanticColors.Legacy.dutyMeta
    val lastOccurrence = SemanticColors.Legacy.lastOccurrence

    // Background colors
    val cardBackground = SemanticColors.Legacy.cardBackground

    // Divider colors
    val divider = SemanticColors.Legacy.divider
}
