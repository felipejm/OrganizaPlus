package com.joffer.organizeplus.designsystem.catalog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.joffer.organizeplus.designsystem.colors.ColorScheme as AppColorScheme
import com.joffer.organizeplus.designsystem.components.*
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChartShowcaseScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            AppTopAppBarWithBackButton(
                title = "Charts",
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
            item {
                Text(
                    text = "Bar Chart",
                    style = Typography.h3,
                    color = AppColorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(Spacing.sm))
                Text(
                    text = "Vertical bar charts using AAY-chart library with design system color palette",
                    style = Typography.body,
                    color = AppColorScheme.onSurfaceVariant
                )
            }

            item {
                ChartExample(
                    title = "Sample Data Chart",
                    data = listOf(
                        ChartDataPoint("Jan", 10f),
                        ChartDataPoint("Feb", 25f),
                        ChartDataPoint("Mar", 15f),
                        ChartDataPoint("Apr", 30f),
                        ChartDataPoint("May", 20f),
                        ChartDataPoint("Jun", 35f)
                    ),
                    legend = "Monthly values"
                )
            }

            item {
                ChartExample(
                    title = "Empty State",
                    data = emptyList(),
                    legend = null
                )
            }

            item {
                ChartExample(
                    title = "Single Data Point",
                    data = listOf(
                        ChartDataPoint("Jan", 50f)
                    ),
                    legend = "Single month data"
                )
            }

            item {
                ChartExample(
                    title = "Multi-Color Bars",
                    data = listOf(
                        ChartDataPoint("Q1", 100f),
                        ChartDataPoint("Q2", 150f),
                        ChartDataPoint("Q3", 120f),
                        ChartDataPoint("Q4", 180f)
                    ),
                    legend = "Each bar uses a different color from the design system"
                )
            }

            item {
                ChartExample(
                    title = "Without Values",
                    data = listOf(
                        ChartDataPoint("Mon", 5f),
                        ChartDataPoint("Tue", 8f),
                        ChartDataPoint("Wed", 12f),
                        ChartDataPoint("Thu", 6f),
                        ChartDataPoint("Fri", 15f)
                    ),
                    legend = "Daily values",
                    showValues = false
                )
            }

            item {
                ChartExample(
                    title = "Large Dataset",
                    data = listOf(
                        ChartDataPoint("Week 1", 45f),
                        ChartDataPoint("Week 2", 52f),
                        ChartDataPoint("Week 3", 38f),
                        ChartDataPoint("Week 4", 61f),
                        ChartDataPoint("Week 5", 47f),
                        ChartDataPoint("Week 6", 55f),
                        ChartDataPoint("Week 7", 42f),
                        ChartDataPoint("Week 8", 58f),
                        ChartDataPoint("Week 9", 49f),
                        ChartDataPoint("Week 10", 63f)
                    ),
                    legend = "Weekly performance over 10 weeks"
                )
            }
        }
    }
}

@Composable
private fun ChartExample(
    title: String,
    data: List<ChartDataPoint>,
    legend: String?,
    showValues: Boolean = true
) {
    OrganizeCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(Spacing.md)
        ) {
            Text(
                text = title,
                style = Typography.titleMedium,
                color = AppColorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(Spacing.sm))
            
            // Convert single data series to new format
            val xAxisLabels = data.map { it.label }
            val dataSeries = listOf(
                ChartDataSeries(
                    name = "Data",
                    values = data.map { it.value.toDouble() }
                )
            )
            
            AppBarChart(
                dataSeries = dataSeries,
                xAxisLabels = xAxisLabels,
                legend = legend,
                showValues = showValues
            )
        }
    }
}