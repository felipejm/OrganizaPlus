package com.joffer.organizeplus.designsystem.catalog

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.joffer.organizeplus.designsystem.components.*
import com.joffer.organizeplus.designsystem.colors.SemanticColors
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.DesignSystemTypography

@Composable
fun ChartShowcaseScreen(
    modifier: Modifier = Modifier
) {
    val typography = DesignSystemTypography()
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(Spacing.md),
        verticalArrangement = Arrangement.spacedBy(Spacing.lg)
    ) {
        Text(
            text = "Charts",
            style = typography.headlineMedium,
            color = SemanticColors.Foreground.primary
        )
        
        Text(
            text = "Bar Chart",
            style = typography.titleLarge,
            color = SemanticColors.Foreground.primary
        )
        
        Text(
            text = "Vertical bar chart for comparing values",
            style = typography.bodyMedium,
            color = SemanticColors.Foreground.secondary
        )
        
        val sampleData = listOf(
            ChartDataPoint("Jan", 120f),
            ChartDataPoint("Feb", 150f),
            ChartDataPoint("Mar", 180f),
            ChartDataPoint("Apr", 140f),
            ChartDataPoint("May", 200f),
            ChartDataPoint("Jun", 190f)
        )

        AppBarChart(
            data = sampleData
        )
    }
}