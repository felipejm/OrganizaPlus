package com.joffer.organizeplus.features.dashboard.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.joffer.organizeplus.designsystem.components.*
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.Typography
import com.joffer.organizeplus.features.dashboard.components.UpcomingSection
import com.joffer.organizeplus.designsystem.components.EmptyState
import com.joffer.organizeplus.designsystem.components.ErrorBanner
import com.joffer.organizeplus.features.dashboard.presentation.DashboardViewModel
import com.joffer.organizeplus.features.dashboard.DashboardIntent
import org.jetbrains.compose.resources.stringResource
import organizeplus.composeapp.generated.resources.Res
import organizeplus.composeapp.generated.resources.dashboard_greeting
import organizeplus.composeapp.generated.resources.dashboard_new_duty

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel,
    onNavigateToCreateDuty: () -> Unit,
    onNavigateToDuties: () -> Unit,
    onNavigateToEditDuty: (String) -> Unit,
    onNavigateToSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val userName by viewModel.userName.collectAsState()
    
    LaunchedEffect(Unit) {
        viewModel.onIntent(DashboardIntent.LoadDashboard)
    }
    
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(Res.string.dashboard_greeting, userName),
                        style = Typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    IconButton(onClick = onNavigateToSettings) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings",
                            tint = com.joffer.organizeplus.designsystem.colors.ColorScheme.onSurface
                        )
                    }
                    OrganizePrimaryButton(
                        text = stringResource(Res.string.dashboard_new_duty),
                        onClick = onNavigateToCreateDuty,
                        modifier = Modifier.padding(Spacing.sm)
                    )
                }
            )
        },
        floatingActionButton = {
            OrganizeFAB(
                onClick = onNavigateToCreateDuty,
                contentDescription = stringResource(Res.string.dashboard_new_duty)
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(Spacing.Screen.padding),
            verticalArrangement = Arrangement.spacedBy(Spacing.Screen.contentSpacing)
        ) {
            if (uiState.error != null) {
                item {
                    ErrorBanner(
                        message = uiState.error!!,
                        onRetry = { viewModel.onIntent(DashboardIntent.Retry) },
                        onDismiss = { viewModel.onIntent(DashboardIntent.ClearError) }
                    )
                }
            }
            
            if (uiState.isLoading) {
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            } else {
                item {
                    UpcomingSection(
                        duties = uiState.upcomingDuties,
                        onMarkPaid = { viewModel.onIntent(DashboardIntent.MarkObligationPaid(it)) },
                        onEdit = onNavigateToEditDuty,
                        onViewAll = onNavigateToDuties
                    )
                }
                
                if (uiState.upcomingDuties.isEmpty()) {
                    item {
                        EmptyState(
                            onAddObligation = onNavigateToCreateDuty
                        )
                    }
                }
            }
        }
    }
}

