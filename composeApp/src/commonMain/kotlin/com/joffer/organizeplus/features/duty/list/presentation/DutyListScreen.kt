package com.joffer.organizeplus.features.duty.list.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.joffer.organizeplus.common.utils.DateUtils
import com.joffer.organizeplus.designsystem.components.*
import com.joffer.organizeplus.features.duty.list.components.DutyListItem
import com.joffer.organizeplus.designsystem.colors.ColorScheme as AppColorScheme
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.Typography
import com.joffer.organizeplus.features.dashboard.domain.entities.Duty
import com.joffer.organizeplus.utils.CategoryIconProvider
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.stringResource
import organizeplus.composeapp.generated.resources.Res
import organizeplus.composeapp.generated.resources.duty_list_title
import organizeplus.composeapp.generated.resources.duty_list_search_hint
import organizeplus.composeapp.generated.resources.duty_list_empty_title
import organizeplus.composeapp.generated.resources.duty_list_empty_subtitle
import organizeplus.composeapp.generated.resources.duty_list_error_title
import organizeplus.composeapp.generated.resources.duty_list_error_subtitle
import organizeplus.composeapp.generated.resources.duty_list_retry
import organizeplus.composeapp.generated.resources.duty_list_mark_paid
import organizeplus.composeapp.generated.resources.duty_list_edit
import organizeplus.composeapp.generated.resources.duty_list_occurrences
import organizeplus.composeapp.generated.resources.duty_list_delete

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DutyListScreen(
    viewModel: DutyListViewModel,
    onNavigateToCreateDuty: () -> Unit,
    onNavigateToEditDuty: (String) -> Unit,
    onNavigateBack: () -> Unit,
    onNavigateToOccurrences: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    
    LaunchedEffect(uiState.error) {
        if (uiState.error != null) {
            // Error will be shown in the UI below
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(Res.string.duty_list_title),
                        style = Typography.headlineSmall,
                        color = AppColorScheme.onSurface
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Voltar",
                            tint = AppColorScheme.onSurface
                        )
                    }
                },
                actions = {},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppColorScheme.surface
                )
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
            SearchBar(
                query = uiState.searchQuery,
                onQueryChange = { viewModel.onIntent(DutyListIntent.SearchDuties(it)) },
                modifier = Modifier.padding(Spacing.md)
            )
            
            Spacer(modifier = Modifier.height(Spacing.sm))
            
            if (uiState.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = AppColorScheme.primary
                    )
                }
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
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(Res.string.duty_list_empty_title),
                            style = Typography.titleMedium,
                            color = AppColorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(Spacing.sm))
                        Text(
                            text = stringResource(Res.string.duty_list_empty_subtitle),
                            style = Typography.bodyMedium,
                            color = AppColorScheme.formSecondaryText
                        )
                        Spacer(modifier = Modifier.height(Spacing.md))
                        Button(onClick = onNavigateToCreateDuty) {
                            Text("Add Duty")
                        }
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(Spacing.md),
                    verticalArrangement = Arrangement.spacedBy(Spacing.sm)
                ) {
                    items(uiState.duties) { duty ->
                        DutyListItem(
                            duty = duty,
                            onViewOccurrences = onNavigateToOccurrences
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = {
            Text(
                text = stringResource(Res.string.duty_list_search_hint),
                color = AppColorScheme.formPlaceholder
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = AppColorScheme.formPlaceholder
            )
        },
        modifier = modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = AppColorScheme.primary,
            unfocusedBorderColor = AppColorScheme.formBorder,
            focusedTextColor = AppColorScheme.formText,
            unfocusedTextColor = AppColorScheme.formText
        ),
        shape = RoundedCornerShape(Spacing.borderRadius)
    )
}
