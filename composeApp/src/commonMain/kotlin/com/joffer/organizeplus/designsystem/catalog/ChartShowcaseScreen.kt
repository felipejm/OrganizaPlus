package com.joffer.organizeplus.designsystem.catalog

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.joffer.organizeplus.designsystem.components.AppBarChart
import com.joffer.organizeplus.designsystem.components.AppTopAppBarWithBackButton
import com.joffer.organizeplus.designsystem.components.ChartDataPoint
import com.joffer.organizeplus.designsystem.spacing.Spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChartShowcaseScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            AppTopAppBarWithBackButton(
                title = "Chart Components",
                onBackClick = onNavigateBack
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(Spacing.md),
            verticalArrangement = Arrangement.spacedBy(Spacing.lg)
        ) {
            // Sample bar chart data
            val sampleData = listOf(
                ChartDataPoint("Jan", 45f),
                ChartDataPoint("Feb", 32f),
                ChartDataPoint("Mar", 67f),
                ChartDataPoint("Apr", 23f),
                ChartDataPoint("May", 89f),
                ChartDataPoint("Jun", 56f)
            )

            AppBarChart(
                data = sampleData,
                title = "Sample Data Chart",
                modifier = Modifier.fillMaxWidth()
            )

            // Empty state chart
            AppBarChart(
                data = emptyList(),
                title = "Empty Chart",
                emptyStateText = "No data available",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
