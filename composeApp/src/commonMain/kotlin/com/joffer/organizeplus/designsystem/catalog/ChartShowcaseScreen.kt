package com.joffer.organizeplus.designsystem.catalog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.joffer.organizeplus.common.utils.toCurrencyFormat
import com.joffer.organizeplus.designsystem.colors.SemanticColors
import com.joffer.organizeplus.designsystem.components.*
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.DesignSystemTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChartShowcaseScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val typography = DesignSystemTypography()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = SemanticColors.Background.primary,
        topBar = {
            AppTopAppBarWithBackButton(onBackClick = onNavigateBack)
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = Spacing.md,
                    end = Spacing.md,
                    top = paddingValues.calculateTopPadding() + Spacing.md,
                    bottom = paddingValues.calculateBottomPadding() + Spacing.md
                ),
            verticalArrangement = Arrangement.spacedBy(Spacing.lg)
        ) {
            item {
                Text(
                    text = "Bar Chart",
                    style = typography.titleLarge,
                    color = SemanticColors.Foreground.primary,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                Text(
                    text = "Vertical bar chart for comparing values across categories",
                    style = typography.bodyMedium,
                    color = SemanticColors.Foreground.secondary
                )
            }

            item {
                val sampleData = listOf(
                    ChartDataPoint("Jan", 120f),
                    ChartDataPoint("Feb", 150f),
                    ChartDataPoint("Mar", 180f),
                    ChartDataPoint("Apr", 140f),
                    ChartDataPoint("May", 200f),
                    ChartDataPoint("Jun", 190f)
                )

                AppBarChart(
                    title = "Monthly Revenue",
                    data = sampleData,
                    config = ChartConfig(
                        barColors = listOf(
                            SemanticColors.Foreground.brand,
                            SemanticColors.Foreground.success,
                            SemanticColors.Foreground.warning,
                            SemanticColors.Foreground.error
                        ),
                        animationDuration = 1200
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                Spacer(modifier = Modifier.height(Spacing.lg))

                Text(
                    text = "Circle Chart",
                    style = typography.titleLarge,
                    color = SemanticColors.Foreground.primary,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                Text(
                    text = "Circular chart for showing proportions and percentages",
                    style = typography.bodyMedium,
                    color = SemanticColors.Foreground.secondary
                )
            }

            item {
                val circleData = listOf(
                    CircleChartSegment("Mobile", 45f, SemanticColors.Foreground.brand),
                    CircleChartSegment("Desktop", 35f, SemanticColors.Foreground.success),
                    CircleChartSegment("Tablet", 15f, SemanticColors.Foreground.warning),
                    CircleChartSegment("Other", 5f, SemanticColors.Foreground.error)
                )

                AppCircleChart(
                    title = "Device Usage",
                    data = circleData,
                    config = CircleChartConfig(
                        strokeWidth = 16.dp,
                        showCenterText = true,
                        centerText = "Total\n100%",
                        animationDuration = 1500
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                Spacer(modifier = Modifier.height(Spacing.lg))

                Text(
                    text = "Gauge Chart",
                    style = typography.titleLarge,
                    color = SemanticColors.Foreground.primary,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                Text(
                    text = "Gauge chart for displaying single values with min/max ranges",
                    style = typography.bodyMedium,
                    color = SemanticColors.Foreground.secondary
                )
            }

            item {
                val gaugeData = GaugeChartData(
                    value = 75f,
                    minValue = 0f,
                    maxValue = 100f,
                    unit = "%",
                    color = SemanticColors.Foreground.success,
                    backgroundColor = SemanticColors.Background.surfaceVariant,
                    label = "Completion Rate",
                    subtitle = "Progress towards goal"
                )

                AppGaugeChart(
                    data = gaugeData,
                    config = GaugeChartConfig(
                        strokeWidth = 20.dp,
                        animationDuration = 1800
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                Spacer(modifier = Modifier.height(Spacing.lg))

                Text(
                    text = "Currency Formatting",
                    style = typography.titleLarge,
                    color = SemanticColors.Foreground.primary,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                Text(
                    text = "Demonstration of currency formatting with thousands separator",
                    style = typography.bodyMedium,
                    color = SemanticColors.Foreground.secondary
                )
            }

            item {
                val testValues = listOf(
                    100.0,
                    1000.0,
                    10000.0,
                    100000.0,
                    1000000.0,
                    1234.56,
                    12345.67,
                    123456.78
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(Spacing.sm)
                ) {
                    testValues.forEach { value ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "R$ ${value.toInt()}",
                                style = typography.bodyMedium,
                                color = SemanticColors.Foreground.secondary
                            )
                            Text(
                                text = value.toCurrencyFormat(),
                                style = typography.bodyMedium,
                                color = SemanticColors.Foreground.primary,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }
    }
}
