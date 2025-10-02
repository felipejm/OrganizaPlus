package com.joffer.organizeplus.designsystem.colors

import androidx.compose.runtime.Immutable

/**
 * Semantic colors define the high-level logic used when applying color to an interface.
 * They are divided into six distinct groups, each serving different purposes and uses.
 * These colors are built on top of primitive colors and provide meaning and context.
 */
@Immutable
object SemanticColors {

    // ===== BACKGROUND COLORS =====
    // Used as fills or backgrounds of interface elements
    object Background {
        val primary = PrimitiveColors.lightGrey
        val secondary = PrimitiveColors.neutral50
        val tertiary = PrimitiveColors.neutral100
        val quaternary = PrimitiveColors.neutral200

        // Surface variants
        val surface = PrimitiveColors.white
        val surfaceVariant = PrimitiveColors.neutral100
        val surfaceContainer = PrimitiveColors.neutral50
        val surfaceContainerLow = PrimitiveColors.neutral50

        // Brand backgrounds
        val brand = PrimitiveColors.black
        val brandContainer = PrimitiveColors.primary100

        // State backgrounds
        val success = PrimitiveColors.success100
        val warning = PrimitiveColors.warning100
        val error = PrimitiveColors.danger100
        val info = PrimitiveColors.info100

        // Interactive backgrounds
        val hover = PrimitiveColors.neutral200
        val pressed = PrimitiveColors.neutral300
        val selected = PrimitiveColors.primary100
        val disabled = PrimitiveColors.neutral200
    }

    // ===== ON BACKGROUND COLORS =====
    // Accessible color pairs for use on top of specific background colors
    object OnBackground {
        val primary = PrimitiveColors.neutral700
        val secondary = PrimitiveColors.neutral600
        val tertiary = PrimitiveColors.neutral500
        val disabled = PrimitiveColors.neutral400

        // On brand backgrounds
        val onBrand = PrimitiveColors.white
        val onBrandContainer = PrimitiveColors.primary700

        // On state backgrounds
        val onSuccess = PrimitiveColors.success700
        val onWarning = PrimitiveColors.warning700
        val onError = PrimitiveColors.danger700
        val onInfo = PrimitiveColors.info700

        // On surface variants
        val onSurface = PrimitiveColors.neutral700
        val onSurfaceVariant = PrimitiveColors.neutral600
    }

    // ===== FOREGROUND COLORS =====
    // Used for content placed on top of backgrounds such as text or icons
    object Foreground {
        val primary = PrimitiveColors.neutral700
        val secondary = PrimitiveColors.neutral600
        val tertiary = PrimitiveColors.neutral500
        val quaternary = PrimitiveColors.neutral400
        val disabled = PrimitiveColors.neutral400

        // Brand foreground
        val brand = PrimitiveColors.black
        val brandSecondary = PrimitiveColors.primary600

        // State foreground
        val success = PrimitiveColors.success600
        val warning = PrimitiveColors.warning600
        val error = PrimitiveColors.danger600
        val info = PrimitiveColors.info600

        // Interactive foreground
        val interactive = PrimitiveColors.primary500
        val interactiveHover = PrimitiveColors.primary600
        val interactivePressed = PrimitiveColors.primary700
        val interactiveDisabled = PrimitiveColors.neutral400
    }

    // ===== BORDER COLORS =====
    // Used as stroke or border of interface elements
    object Border {
        val primary = PrimitiveColors.neutral300
        val secondary = PrimitiveColors.neutral200
        val tertiary = PrimitiveColors.neutral400
        val disabled = PrimitiveColors.neutral200

        // Brand borders
        val brand = PrimitiveColors.primary500
        val brandSecondary = PrimitiveColors.primary300
        val brandDisabled = PrimitiveColors.neutral300

        // State borders
        val success = PrimitiveColors.success500
        val warning = PrimitiveColors.warning500
        val error = PrimitiveColors.danger500
        val info = PrimitiveColors.info500

        // Interactive borders
        val interactive = PrimitiveColors.primary500
        val interactiveHover = PrimitiveColors.primary600
        val interactivePressed = PrimitiveColors.primary700
        val interactiveFocused = PrimitiveColors.primary500
        val interactiveDisabled = PrimitiveColors.neutral300

        // Outline variants
        val outline = PrimitiveColors.neutral300
        val outlineVariant = PrimitiveColors.neutral200
    }

    // ===== LINK COLORS =====
    // Used for consistent styling of clickable text and icons
    object Link {
        val primary = PrimitiveColors.info600
        val secondary = PrimitiveColors.info500
        val tertiary = PrimitiveColors.info400
        val visited = PrimitiveColors.info700
        val hover = PrimitiveColors.info500
        val pressed = PrimitiveColors.info700
        val disabled = PrimitiveColors.neutral400

        // Brand links
        val brand = PrimitiveColors.primary500
        val brandHover = PrimitiveColors.primary600
        val brandPressed = PrimitiveColors.primary700
    }

    // ===== FOCUS COLORS =====
    // Used as the outline of focused elements on the screen
    object Focus {
        val primary = PrimitiveColors.info500
        val secondary = PrimitiveColors.info400
        val tertiary = PrimitiveColors.info300
        val disabled = PrimitiveColors.neutral300

        // Brand focus
        val brand = PrimitiveColors.primary500
        val brandSecondary = PrimitiveColors.primary400

        // State focus
        val success = PrimitiveColors.success500
        val warning = PrimitiveColors.warning500
        val error = PrimitiveColors.danger500
        val info = PrimitiveColors.info500

        // Ring/outline variants
        val ring = PrimitiveColors.info500
        val ringVariant = PrimitiveColors.info300
    }

    // ===== LEGACY COMPATIBILITY =====
    // Maintain backward compatibility with existing color usage
    object Legacy {
        // Dashboard specific colors
        val personalAccent = PrimitiveColors.teal
        val personalAccentLight = PrimitiveColors.tealLight
        val companyAccent = PrimitiveColors.amber
        val companyAccentLight = PrimitiveColors.amberLight

        // Status colors
        val overdueText = PrimitiveColors.overdueRed
        val amountPaid = PrimitiveColors.amountPaidGreen
        val summaryMonthLabel = PrimitiveColors.overdueRed
        val summaryBackground = PrimitiveColors.summaryBackground

        // Text colors
        val sectionHeader = PrimitiveColors.neutral700
        val dutyTitle = PrimitiveColors.neutral700
        val dutyMeta = PrimitiveColors.neutral600
        val lastOccurrence = PrimitiveColors.overdueRed

        // Background colors
        val cardBackground = PrimitiveColors.white

        // Divider colors
        val divider = PrimitiveColors.neutral300

        // Icon colors
        val iconOrange = PrimitiveColors.auxiliary500
        val iconBlue = PrimitiveColors.info500
        val iconOrangeContainer = PrimitiveColors.auxiliary100
        val iconBlueContainer = PrimitiveColors.info100
    }
}
