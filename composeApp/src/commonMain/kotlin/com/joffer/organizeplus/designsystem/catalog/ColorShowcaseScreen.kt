package com.joffer.organizeplus.designsystem.catalog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.joffer.organizeplus.common.utils.formatString
import com.joffer.organizeplus.designsystem.colors.SemanticColors
import com.joffer.organizeplus.designsystem.components.AppTopAppBarWithBackButton
import com.joffer.organizeplus.designsystem.components.OrganizeCard
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.DesignSystemTypography
import com.joffer.organizeplus.designsystem.colors.ColorScheme as AppColorScheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorShowcaseScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val typography = DesignSystemTypography()
    Scaffold(
        topBar = {
            AppTopAppBarWithBackButton(
                title = "Color Palette",
                onBackClick = onNavigateBack
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(Spacing.md),
            verticalArrangement = Arrangement.spacedBy(Spacing.lg)
        ) {
            items(ColorPalette.values()) { palette ->
                ColorPaletteSection(palette = palette)
            }
        }
    }
}

@Composable
private fun ColorPaletteSection(
    palette: ColorPalette,
    modifier: Modifier = Modifier
) {
    val typography = DesignSystemTypography()
    OrganizeCard(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(Spacing.md)
        ) {
            Text(
                text = palette.title,
                style = typography.headlineMedium,
                color = SemanticColors.Foreground.primary
            )

            Text(
                text = palette.description,
                style = typography.bodyMedium,
                color = SemanticColors.Foreground.secondary
            )

            Spacer(modifier = Modifier.height(Spacing.md))

            when (palette) {
                ColorPalette.NEUTRAL -> {
                    NeutralColorGrid()
                }
                ColorPalette.PRIMARY -> {
                    PrimaryColorGrid()
                }
                ColorPalette.AUXILIARY -> {
                    AuxiliaryColorGrid()
                }
                ColorPalette.SEMANTIC -> {
                    SemanticColorGrid()
                }
            }
        }
    }
}

@Composable
private fun NeutralColorGrid() {
    val typography = DesignSystemTypography()
    val neutralColors = listOf(
        "Black" to AppColorScheme.black,
        "Neutral 700" to AppColorScheme.neutral700,
        "Neutral 600" to AppColorScheme.neutral600,
        "Neutral 500" to AppColorScheme.neutral500,
        "Neutral 400" to AppColorScheme.neutral400,
        "Neutral 300" to AppColorScheme.neutral300,
        "Neutral 200" to AppColorScheme.neutral200,
        "Neutral 100" to AppColorScheme.neutral100,
        "Neutral 50" to AppColorScheme.neutral50,
        "White" to AppColorScheme.white
    )

    ColorGrid(colors = neutralColors)
}

@Composable
private fun PrimaryColorGrid() {
    val typography = DesignSystemTypography()
    val primaryColors = listOf(
        "Primary 700" to AppColorScheme.primary700,
        "Primary 600" to AppColorScheme.primary600,
        "Primary 500" to AppColorScheme.primary500,
        "Primary 400" to AppColorScheme.primary400,
        "Primary 300" to AppColorScheme.primary300,
        "Primary 200" to AppColorScheme.primary200,
        "Primary 100" to AppColorScheme.primary100,
        "Primary 50" to AppColorScheme.primary50
    )

    ColorGrid(colors = primaryColors)
}

@Composable
private fun AuxiliaryColorGrid() {
    val typography = DesignSystemTypography()
    val auxiliaryColors = listOf(
        "Auxiliary 700" to AppColorScheme.auxiliary700,
        "Auxiliary 600" to AppColorScheme.auxiliary600,
        "Auxiliary 500" to AppColorScheme.auxiliary500,
        "Auxiliary 400" to AppColorScheme.auxiliary400,
        "Auxiliary 300" to AppColorScheme.auxiliary300,
        "Auxiliary 200" to AppColorScheme.auxiliary200,
        "Auxiliary 100" to AppColorScheme.auxiliary100,
        "Auxiliary 50" to AppColorScheme.auxiliary50
    )

    ColorGrid(colors = auxiliaryColors)
}

@Composable
private fun SemanticColorGrid() {
    val typography = DesignSystemTypography()
    Column(
        verticalArrangement = Arrangement.spacedBy(Spacing.md)
    ) {
        // Danger Colors
        Text(
            text = "Danger",
            style = typography.titleSmall,
            color = SemanticColors.Foreground.primary
        )
        val dangerColors = listOf(
            "Danger 700" to AppColorScheme.danger700,
            "Danger 500" to AppColorScheme.danger500,
            "Danger 300" to AppColorScheme.danger300,
            "Danger 100" to AppColorScheme.danger100
        )
        ColorGrid(colors = dangerColors)

        Spacer(modifier = Modifier.height(Spacing.sm))

        // Warning Colors
        Text(
            text = "Warning",
            style = typography.titleSmall,
            color = SemanticColors.Foreground.primary
        )
        val warningColors = listOf(
            "Warning 700" to AppColorScheme.warning700,
            "Warning 500" to AppColorScheme.warning500,
            "Warning 300" to AppColorScheme.warning300,
            "Warning 100" to AppColorScheme.warning100
        )
        ColorGrid(colors = warningColors)

        Spacer(modifier = Modifier.height(Spacing.sm))

        // Success Colors
        Text(
            text = "Success",
            style = typography.titleSmall,
            color = SemanticColors.Foreground.primary
        )
        val successColors = listOf(
            "Success 700" to AppColorScheme.success700,
            "Success 500" to AppColorScheme.success500,
            "Success 300" to AppColorScheme.success300,
            "Success 100" to AppColorScheme.success100
        )
        ColorGrid(colors = successColors)

        Spacer(modifier = Modifier.height(Spacing.sm))

        // Info Colors
        Text(
            text = "Info",
            style = typography.titleSmall,
            color = SemanticColors.Foreground.primary
        )
        val infoColors = listOf(
            "Info 700" to AppColorScheme.info700,
            "Info 500" to AppColorScheme.info500,
            "Info 300" to AppColorScheme.info300,
            "Info 100" to AppColorScheme.info100
        )
        ColorGrid(colors = infoColors)
    }
}

@Composable
private fun ColorGrid(
    colors: List<Pair<String, Color>>,
    modifier: Modifier = Modifier
) {
    val typography = DesignSystemTypography()
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Spacing.sm)
    ) {
        colors.chunked(4).forEach { rowColors ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(Spacing.sm)
            ) {
                rowColors.forEach { (name, color) ->
                    ColorSwatch(
                        name = name,
                        color = color,
                        modifier = Modifier.weight(1f)
                    )
                }
                // Fill remaining space if row has less than 4 colors
                repeat(4 - rowColors.size) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun ColorSwatch(
    name: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    val typography = DesignSystemTypography()
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .background(
                    color = color,
                    shape = RoundedCornerShape(8.dp)
                )
        )

        Spacer(modifier = Modifier.height(Spacing.xs))

        Text(
            text = name,
            style = typography.caption,
            color = SemanticColors.Foreground.secondary,
            fontWeight = FontWeight.Medium
        )

        Text(
            text = color.toHexString(),
            style = typography.caption,
            color = SemanticColors.Foreground.secondary
        )
    }
}

private fun Color.toHexString(): String {
    val alpha = (alpha * 255).toInt()
    val red = (red * 255).toInt()
    val green = (green * 255).toInt()
    val blue = (blue * 255).toInt()

    return if (alpha == 255) {
        formatString("#%02X%02X%02X", red, green, blue)
    } else {
        formatString("#%02X%02X%02X%02X", alpha, red, green, blue)
    }
}

enum class ColorPalette(
    val title: String,
    val description: String
) {
    NEUTRAL(
        title = "Neutral Colors",
        description = "Grayscale colors for text, backgrounds, and borders"
    ),
    PRIMARY(
        title = "Primary Colors",
        description = "Main brand colors for primary actions and highlights"
    ),
    AUXILIARY(
        title = "Auxiliary Colors",
        description = "Secondary brand colors for accents and variety"
    ),
    SEMANTIC(
        title = "Semantic Colors",
        description = "Colors with specific meanings for different states"
    )
}
