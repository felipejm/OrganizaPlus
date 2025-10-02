package com.joffer.organizeplus.features.duty.review.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.joffer.organizeplus.designsystem.colors.SemanticColors
import com.joffer.organizeplus.designsystem.components.*
import com.joffer.organizeplus.common.utils.DateUtils
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.DesignSystemTypography
import com.joffer.organizeplus.features.duty.review.domain.entities.DutyReviewData
import com.joffer.organizeplus.features.duty.review.domain.entities.MonthlyDutyReview
import com.joffer.organizeplus.features.duty.review.presentation.components.MonthlyDutySection
import org.jetbrains.compose.resources.stringResource
import organizeplus.composeapp.generated.resources.Res
import organizeplus.composeapp.generated.resources.duty_review_empty_subtitle
import organizeplus.composeapp.generated.resources.duty_review_empty_title
import organizeplus.composeapp.generated.resources.duty_review_error_subtitle
import organizeplus.composeapp.generated.resources.duty_review_error_title
import organizeplus.composeapp.generated.resources.duty_review_retry
import organizeplus.composeapp.generated.resources.duty_review_title

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DutyReviewScreen(
    viewModel: DutyReviewViewModel,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = SemanticColors.Background.primary,
        topBar = {
            AppTopAppBarWithBackButton(
                onBackClick = onNavigateBack,
            )
        },
    ) { paddingValues ->
        DutyReviewContent(
            uiState = uiState,
            viewModel = viewModel,
            paddingValues = paddingValues
        )
    }
}

@Composable
private fun DutyReviewContent(
    uiState: DutyReviewUiState,
    viewModel: DutyReviewViewModel,
    paddingValues: PaddingValues
) {
    when {
        uiState.isLoading -> {
            DutyReviewLoadingContent(paddingValues = paddingValues)
        }

        uiState.error != null -> {
            DutyReviewErrorContent(
                error = uiState.error,
                onRetry = { viewModel.onIntent(DutyReviewIntent.Retry) },
                paddingValues = paddingValues
            )
        }

        uiState.dutyReviewData?.monthlyReviews?.isEmpty() == true -> {
            DutyReviewEmptyContent(paddingValues = paddingValues)
        }

        else -> {
            DutyReviewDataContent(
                data = uiState.dutyReviewData,
                paddingValues = paddingValues
            )
        }
    }
}

@Composable
private fun DutyReviewLoadingContent(
    paddingValues: PaddingValues
) {
    DutyReviewCenteredContent(paddingValues = paddingValues) {
        OrganizeProgressIndicatorFullScreen()
    }
}

@Composable
private fun DutyReviewErrorContent(
    error: String?,
    onRetry: () -> Unit,
    paddingValues: PaddingValues
) {
    DutyReviewCenteredContent(paddingValues = paddingValues) {
        OrganizeResult(
            type = ResultType.ERROR,
            title = stringResource(Res.string.duty_review_error_title),
            description = error ?: stringResource(Res.string.duty_review_error_subtitle),
            actions = {
                OrganizePrimaryButton(
                    text = stringResource(Res.string.duty_review_retry),
                    onClick = onRetry
                )
            }
        )
    }
}

@Composable
private fun DutyReviewEmptyContent(
    paddingValues: PaddingValues
) {
    DutyReviewCenteredContent(paddingValues = paddingValues) {
        OrganizeResult(
            type = ResultType.INFO,
            title = stringResource(Res.string.duty_review_empty_title),
            description = stringResource(Res.string.duty_review_empty_subtitle)
        )
    }
}

@Composable
private fun DutyReviewCenteredContent(
    paddingValues: PaddingValues,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Composable
private fun DutyReviewDataContent(
    data: DutyReviewData?,
    paddingValues: PaddingValues
) {
    val listState = rememberLazyListState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = listState,
        contentPadding = PaddingValues(
            start = Spacing.md,
            end = Spacing.md,
            top = paddingValues.calculateTopPadding() + Spacing.md,
            bottom = paddingValues.calculateBottomPadding() + Spacing.md
        ),
        verticalArrangement = Arrangement.spacedBy(Spacing.md)
    ) {
        data?.let { reviewData ->
            // Header section
            item {
                Text(
                    text = stringResource(Res.string.duty_review_title),
                    style = DesignSystemTypography().headlineLarge,
                    color = SemanticColors.Foreground.primary,
                    fontWeight = FontWeight.Black
                )
                Spacer(modifier = Modifier.height(Spacing.lg))
            }

            // Monthly Amount Paid Chart
            item {
                DutyReviewChart(
                    monthlyReviews = reviewData.monthlyReviews,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(Spacing.lg))
            }

            // Monthly sections
            items(
                items = reviewData.monthlyReviews,
                key = { "${it.year}-${it.monthNumber}" },
                contentType = { "monthly_section" }
            ) { monthlyReview ->
                MonthlyDutySection(monthlyReview = monthlyReview)
            }
        }
    }
}


@Composable
private fun DutyReviewChart(
    monthlyReviews: List<MonthlyDutyReview>,
    modifier: Modifier = Modifier
) {
    val chartData = monthlyReviews.map { review ->
        ChartDataPoint(
            label = DateUtils.getShortMonthName(review.monthNumber),
            value = review.totalPaid.toFloat()
        )
    }

    val chartConfig = ChartConfig(
        barColors = listOf(SemanticColors.Foreground.brand),
        showValues = true,
        showLabels = true,
        animate = true,
        animationDuration = 1000
    )

    AppBarChart(
        title = "Monthly Amount Paid",
        data = chartData,
        config = chartConfig,
        modifier = modifier
    )
}
