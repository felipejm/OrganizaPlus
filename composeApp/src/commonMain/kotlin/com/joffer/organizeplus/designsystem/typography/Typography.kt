package com.joffer.organizeplus.designsystem.typography

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import organizeplus.composeapp.generated.resources.Res
import organizeplus.composeapp.generated.resources.SF_Pro_Text_Bold
import organizeplus.composeapp.generated.resources.SF_Pro_Text_Medium
import organizeplus.composeapp.generated.resources.SF_Pro_Text_Regular
import organizeplus.composeapp.generated.resources.SF_Pro_Text_Semibold

/**
 * A comprehensive and generic typography system using SF Pro Text fonts.
 *
 * This typography system provides:
 * - Consistent font sizing and spacing
 * - Proper font weight hierarchy
 * - Semantic naming based on Material Design 3
 * - Accessibility considerations
 * - Generic tokens that can be used across any application
 */
@Immutable
data class Typography(
    // Font Family
    val fontFamily: FontFamily,

    // ===== DISPLAY TYPOGRAPHY =====
    // For large headings and hero text
    val displayLarge: TextStyle,
    val displayMedium: TextStyle,
    val displaySmall: TextStyle,

    // ===== HEADLINE TYPOGRAPHY =====
    // For section headings and important titles
    val headlineLarge: TextStyle,
    val headlineMedium: TextStyle,
    val headlineSmall: TextStyle,

    // ===== TITLE TYPOGRAPHY =====
    // For titles, headers, and prominent text
    val titleLarge: TextStyle,
    val titleMedium: TextStyle,
    val titleSmall: TextStyle,

    // ===== BODY TYPOGRAPHY =====
    // For main content and readable text
    val bodyLarge: TextStyle,
    val bodyMedium: TextStyle,
    val bodySmall: TextStyle,

    // ===== LABEL TYPOGRAPHY =====
    // For buttons, tags, and UI labels
    val labelLarge: TextStyle,
    val labelMedium: TextStyle,
    val labelSmall: TextStyle,

    // ===== SEMANTIC TYPOGRAPHY =====
    // For common UI elements
    val button: TextStyle,
    val caption: TextStyle,
    val overline: TextStyle
)

/**
 * Creates a FontFamily using SF Pro Text fonts with proper weight mapping.
 */
@Composable
fun createTextFontFamily(): FontFamily {
    return FontFamily(
        Font(Res.font.SF_Pro_Text_Regular, FontWeight.Normal),
        Font(Res.font.SF_Pro_Text_Medium, FontWeight.Medium),
        Font(Res.font.SF_Pro_Text_Semibold, FontWeight.SemiBold),
        Font(Res.font.SF_Pro_Text_Bold, FontWeight.Bold)
    )
}

/**
 * Creates a generic typography system using SF Pro Text fonts.
 *
 * This function provides a complete typography system with:
 * - Proper font sizing scale (based on 4px grid system)
 * - Consistent line heights for readability
 * - Appropriate letter spacing for different sizes
 * - Generic semantic naming that can be used across any application
 */
@Composable
fun createTypography(): Typography {
    val fontFamily = createTextFontFamily()

    return Typography(
        fontFamily = fontFamily,

        // ===== DISPLAY TYPOGRAPHY =====
        displayLarge = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 57.sp,
            lineHeight = 64.sp,
            letterSpacing = (-0.25).sp
        ),

        displayMedium = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 45.sp,
            lineHeight = 52.sp,
            letterSpacing = 0.sp
        ),

        displaySmall = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 36.sp,
            lineHeight = 44.sp,
            letterSpacing = 0.sp
        ),

        // ===== HEADLINE TYPOGRAPHY =====
        headlineLarge = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 32.sp,
            lineHeight = 40.sp,
            letterSpacing = 0.sp
        ),

        headlineMedium = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 28.sp,
            lineHeight = 36.sp,
            letterSpacing = 0.sp
        ),

        headlineSmall = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 24.sp,
            lineHeight = 32.sp,
            letterSpacing = 0.sp
        ),

        // ===== TITLE TYPOGRAPHY =====
        titleLarge = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 22.sp,
            lineHeight = 28.sp,
            letterSpacing = 0.sp
        ),

        titleMedium = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp,
            lineHeight = 26.sp,
            letterSpacing = 0.15.sp
        ),

        titleSmall = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.1.sp
        ),

        // ===== BODY TYPOGRAPHY =====
        bodyLarge = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.5.sp
        ),

        bodyMedium = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.25.sp
        ),

        bodySmall = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.4.sp
        ),

        // ===== LABEL TYPOGRAPHY =====
        labelLarge = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.1.sp
        ),

        labelMedium = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.5.sp
        ),

        labelSmall = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 11.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.5.sp
        ),

        // ===== SEMANTIC TYPOGRAPHY =====
        button = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.1.sp
        ),

        caption = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.4.sp
        ),

        overline = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 10.sp,
            lineHeight = 16.sp,
            letterSpacing = 1.5.sp
        )
    )
}

/**
 * Extension functions for easy access to typography styles
 */
val Typography.defaultFontFamily: FontFamily
    get() = fontFamily

// Convenience accessors for common typography patterns
val Typography.display: TextStyle
    get() = displayMedium

val Typography.headline: TextStyle
    get() = headlineMedium

val Typography.title: TextStyle
    get() = titleMedium

val Typography.body: TextStyle
    get() = bodyMedium

val Typography.label: TextStyle
    get() = labelMedium
