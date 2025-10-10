package com.joffer.organizeplus.features.demo.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.joffer.organizeplus.designsystem.colors.SemanticColors
import com.joffer.organizeplus.designsystem.components.charts.BarChart
import com.joffer.organizeplus.designsystem.components.charts.BarChartConfig
import com.joffer.organizeplus.designsystem.components.charts.BarChartData
import com.joffer.organizeplus.designsystem.components.charts.BarDataPoint
import com.joffer.organizeplus.designsystem.icons.OrganizeIcons
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.DesignSystemTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarChartDemoScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val typography = DesignSystemTypography()
    val listState = rememberLazyListState()

    // Sample data for different chart examples
    val monthlyRevenueData = BarChartData(
        dataPoints = listOf(
            BarDataPoint("Jan", 12000f, SemanticColors.Foreground.success),
            BarDataPoint("Feb", 15000f, SemanticColors.Foreground.success),
            BarDataPoint("Mar", 18000f, SemanticColors.Foreground.success),
            BarDataPoint("Apr", 22000f, SemanticColors.Foreground.success),
            BarDataPoint("May", 19000f, SemanticColors.Foreground.success),
            BarDataPoint("Jun", 25000f, SemanticColors.Foreground.success)
        ),
        config = BarChartConfig(
            showValues = true,
            showGrid = true,
            showAxisLabels = true,
            animationEnabled = true
        ),
        title = "Monthly Revenue",
        subtitle = "Q1-Q2 2024 Performance",
        xAxisLabel = "Months",
        yAxisLabel = "Revenue ($)"
    )

    val taskCompletionData = BarChartData(
        dataPoints = listOf(
            BarDataPoint("Mon", 8f),
            BarDataPoint("Tue", 12f),
            BarDataPoint("Wed", 6f),
            BarDataPoint("Thu", 15f),
            BarDataPoint("Fri", 10f),
            BarDataPoint("Sat", 4f),
            BarDataPoint("Sun", 2f)
        ),
        config = BarChartConfig(
            showValues = true,
            showGrid = false,
            showAxisLabels = true,
            animationEnabled = true,
            barSpacing = 0.3f
        ),
        title = "Tasks Completed",
        subtitle = "This week's productivity",
        xAxisLabel = "Days",
        yAxisLabel = "Tasks"
    )

    val categoryDistributionData = BarChartData(
        dataPoints = listOf(
            BarDataPoint("Work", 45f, SemanticColors.Foreground.brand),
            BarDataPoint("Personal", 30f, SemanticColors.Foreground.success),
            BarDataPoint("Health", 15f, SemanticColors.Foreground.warning),
            BarDataPoint("Learning", 10f, SemanticColors.Legacy.companyAccent)
        ),
        config = BarChartConfig(
            showValues = true,
            showGrid = true,
            showAxisLabels = true,
            animationEnabled = true,
            maxValue = 100f
        ),
        title = "Time Distribution",
        subtitle = "Percentage breakdown by category",
        xAxisLabel = "Categories",
        yAxisLabel = "Percentage (%)"
    )

    val emptyData = BarChartData(
        dataPoints = emptyList(),
        title = "Empty Chart",
        subtitle = "This shows the empty state"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Bar Chart Demo",
                        style = typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = OrganizeIcons.System.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = SemanticColors.Background.surface
                )
            )
        },
        containerColor = SemanticColors.Background.primary
    ) { paddingValues ->
        LazyColumn(
            state = listState,
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(Spacing.Screen.padding),
            verticalArrangement = Arrangement.spacedBy(Spacing.xl)
        ) {
            item {
                Text(
                    text = "Bar Chart Component Examples",
                    style = typography.headlineMedium,
                    color = SemanticColors.Foreground.primary,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                Text(
                    text = "This demo showcases the BarChart component with different configurations and data sets.",
                    style = typography.bodyLarge,
                    color = SemanticColors.Foreground.secondary
                )
            }

            // Monthly Revenue Chart
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = SemanticColors.Background.surface
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = Spacing.Elevation.sm)
                ) {
                    Column(
                        modifier = Modifier.padding(Spacing.lg)
                    ) {
                        Text(
                            text = "Example 1: Monthly Revenue",
                            style = typography.titleMedium,
                            color = SemanticColors.Foreground.primary,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(Spacing.md))
                        BarChart(
                            data = monthlyRevenueData
                        )
                    }
                }
            }

            // Task Completion Chart
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = SemanticColors.Background.surface
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = Spacing.Elevation.sm)
                ) {
                    Column(
                        modifier = Modifier.padding(Spacing.lg)
                    ) {
                        Text(
                            text = "Example 2: Task Completion",
                            style = typography.titleMedium,
                            color = SemanticColors.Foreground.primary,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(Spacing.md))
                        BarChart(
                            data = taskCompletionData
                        )
                    }
                }
            }

            // Category Distribution Chart
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = SemanticColors.Background.surface
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = Spacing.Elevation.sm)
                ) {
                    Column(
                        modifier = Modifier.padding(Spacing.lg)
                    ) {
                        Text(
                            text = "Example 3: Category Distribution",
                            style = typography.titleMedium,
                            color = SemanticColors.Foreground.primary,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(Spacing.md))
                        BarChart(
                            data = categoryDistributionData
                        )
                    }
                }
            }

            // Empty State Chart
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = SemanticColors.Background.surface
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = Spacing.Elevation.sm)
                ) {
                    Column(
                        modifier = Modifier.padding(Spacing.lg)
                    ) {
                        Text(
                            text = "Example 4: Empty State",
                            style = typography.titleMedium,
                            color = SemanticColors.Foreground.primary,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(Spacing.md))
                        BarChart(
                            data = emptyData
                        )
                    }
                }
            }

            // Features List
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = SemanticColors.Background.surface
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = Spacing.Elevation.sm)
                ) {
                    Column(
                        modifier = Modifier.padding(Spacing.lg)
                    ) {
                        Text(
                            text = "Features",
                            style = typography.titleMedium,
                            color = SemanticColors.Foreground.primary,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(Spacing.md))

                        val features = listOf(
                            "✨ Smooth animations with customizable duration",
                            "🎨 Customizable colors and styling",
                            "📊 Value labels on bars",
                            "📏 Grid lines and axis labels",
                            "📱 Responsive design",
                            "♿ Accessibility support",
                            "🎯 Click handling for interactive charts",
                            "📈 Automatic value formatting (K, M suffixes)",
                            "🎛️ Configurable spacing and appearance",
                            "📊 Empty state handling"
                        )

                        features.forEach { feature ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = Spacing.xs),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = feature,
                                    style = typography.bodyMedium,
                                    color = SemanticColors.Foreground.secondary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
