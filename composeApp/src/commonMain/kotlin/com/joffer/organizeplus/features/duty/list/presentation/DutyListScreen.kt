package com.joffer.organizeplus.features.duty.list.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.joffer.organizeplus.designsystem.components.*
import com.joffer.organizeplus.designsystem.components.ResultType
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.Typography
import com.joffer.organizeplus.features.duty.list.components.DutyListItem
import com.joffer.organizeplus.features.duty.list.domain.DutyCategoryFilter
import org.jetbrains.compose.resources.stringResource
import organizeplus.composeapp.generated.resources.Res
import organizeplus.composeapp.generated.resources.add_duty
import organizeplus.composeapp.generated.resources.dashboard_company_duties
import organizeplus.composeapp.generated.resources.dashboard_personal_duties
import organizeplus.composeapp.generated.resources.duty_list_empty_subtitle
import organizeplus.composeapp.generated.resources.duty_list_empty_title
import organizeplus.composeapp.generated.resources.duty_list_error_subtitle
import organizeplus.composeapp.generated.resources.duty_list_error_title
import organizeplus.composeapp.generated.resources.duty_list_retry
import organizeplus.composeapp.generated.resources.duty_list_title
import com.joffer.organizeplus.designsystem.colors.ColorScheme as AppColorScheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DutyListScreen(
    viewModel: DutyListViewModel,
    categoryFilter: DutyCategoryFilter,
    onNavigateToCreateDuty: () -> Unit,
    onNavigateBack: () -> Unit,
    onNavigateToOccurrences: (Long) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.error) {
        if (uiState.error != null) {
            // Error will be shown in the UI below
        }
    }

    Scaffold(
        topBar = {
            AppTopAppBarWithBackButton(
                title = when (categoryFilter) {
                    DutyCategoryFilter.All -> stringResource(Res.string.duty_list_title)
                    DutyCategoryFilter.Personal -> stringResource(Res.string.dashboard_personal_duties)
                    DutyCategoryFilter.Company -> stringResource(Res.string.dashboard_company_duties)
                    is DutyCategoryFilter.Custom -> "${categoryFilter.name} Duties"
                },
                onBackClick = onNavigateBack,
                backIcon = Icons.Default.ArrowBack,
                navigationIconContentColor = AppColorScheme.onSurface
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToCreateDuty,
                containerColor = AppColorScheme.primary,
                contentColor = AppColorScheme.onPrimary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Duty"
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (uiState.isLoading) {
                OrganizeProgressIndicatorFullScreen()
            } else if (uiState.error != null) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(Res.string.duty_list_error_title),
                            style = Typography.titleMedium,
                            color = AppColorScheme.error
                        )
                        Spacer(modifier = Modifier.height(Spacing.sm))
                        Text(
                            text = uiState.error ?: stringResource(Res.string.duty_list_error_subtitle),
                            style = Typography.bodyMedium,
                            color = AppColorScheme.formSecondaryText
                        )
                        Spacer(modifier = Modifier.height(Spacing.md))
                        Button(
                            onClick = { viewModel.onIntent(DutyListIntent.Retry) }
                        ) {
                            Text(stringResource(Res.string.duty_list_retry))
                        }
                    }
                }
            } else if (uiState.duties.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    OrganizeResult(
                        type = ResultType.INFO,
                        title = stringResource(Res.string.duty_list_empty_title),
                        description = stringResource(Res.string.duty_list_empty_subtitle),
                        actions = {
                            OrganizePrimaryButton(
                                text = stringResource(Res.string.add_duty),
                                onClick = onNavigateToCreateDuty
                            )
                        }
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(Spacing.md),
                    verticalArrangement = Arrangement.spacedBy(Spacing.sm)
                ) {
                    items(uiState.duties) { dutyWithOccurrence ->
                        DutyListItem(
                            dutyWithOccurrence = dutyWithOccurrence,
                            onViewOccurrences = onNavigateToOccurrences,
                            onDelete = { dutyId -> viewModel.onIntent(DutyListIntent.DeleteDuty(dutyId)) }
                        )
                    }
                }
            }
        }
    }
}
