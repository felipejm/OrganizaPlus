package com.joffer.organizeplus.designsystem.catalog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joffer.organizeplus.designsystem.components.*
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.DesignSystemTypography
import com.joffer.organizeplus.designsystem.colors.ColorScheme as AppColorScheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BannerShowcaseScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val typography = DesignSystemTypography()
    Scaffold(
        topBar = {
            AppTopAppBarWithBackButton(
                title = "Banners",
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
                    text = "Banner Components",
                    style = typography.headlineMedium,
                    color = AppColorScheme.onSurface
                )
            }

            items(BannerShowcaseItem.values()) { item ->
                BannerShowcaseItem(item = item)
            }
        }
    }
}

@Composable
private fun BannerShowcaseItem(
    item: BannerShowcaseItem,
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
                text = item.title,
                style = typography.titleMedium,
                color = AppColorScheme.onSurface
            )

            Text(
                text = item.description,
                style = typography.bodyMedium,
                color = AppColorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(Spacing.md))

            when (item) {
                BannerShowcaseItem.SUCCESS -> {
                    SuccessBannerExamples()
                }
                BannerShowcaseItem.ERROR -> {
                    ErrorBannerExamples()
                }
                BannerShowcaseItem.INFO -> {
                    InfoBannerExamples()
                }
                BannerShowcaseItem.WARNING -> {
                    WarningBannerExamples()
                }
            }
        }
    }
}

@Composable
private fun SuccessBannerExamples() {
    val typography = DesignSystemTypography()
    Column(
        verticalArrangement = Arrangement.spacedBy(Spacing.sm)
    ) {
        Text(
            text = "Success Banners",
            style = typography.titleSmall,
            color = AppColorScheme.onSurface
        )

        SuccessBanner(
            message = "Operation completed successfully!"
        )

        SuccessBanner(
            message = "Data saved successfully. You can continue working."
        )
    }
}

@Composable
private fun ErrorBannerExamples() {
    val typography = DesignSystemTypography()
    Column(
        verticalArrangement = Arrangement.spacedBy(Spacing.sm)
    ) {
        Text(
            text = "Error Banners",
            style = typography.titleSmall,
            color = AppColorScheme.onSurface
        )

        ErrorBanner(
            message = "Something went wrong. Please try again.",
            onRetry = { },
            onDismiss = { }
        )

        ErrorBanner(
            message = "Network connection failed. Check your internet connection.",
            onRetry = { },
            onDismiss = { }
        )
    }
}

@Composable
private fun InfoBannerExamples() {
    val typography = DesignSystemTypography()
    Column(
        verticalArrangement = Arrangement.spacedBy(Spacing.sm)
    ) {
        Text(
            text = "Info Banners",
            style = typography.titleSmall,
            color = AppColorScheme.onSurface
        )

        InfoBanner(
            message = "New features are available in this update.",
            onDismiss = { }
        )

        InfoBanner(
            message = "Your data is synced with the cloud.",
            onDismiss = { }
        )
    }
}

@Composable
private fun WarningBannerExamples() {
    val typography = DesignSystemTypography()
    Column(
        verticalArrangement = Arrangement.spacedBy(Spacing.sm)
    ) {
        Text(
            text = "Warning Banners",
            style = typography.titleSmall,
            color = AppColorScheme.onSurface
        )

        WarningBanner(
            message = "This action cannot be undone.",
            onDismiss = { }
        )

        WarningBanner(
            message = "Your session will expire in 5 minutes.",
            onDismiss = { }
        )
    }
}

@Composable
private fun InfoBanner(
    message: String,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val typography = DesignSystemTypography()
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = AppColorScheme.info100
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.md),
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            Text(
                text = message,
                style = typography.bodyMedium,
                color = AppColorScheme.info700,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(Spacing.sm))

            OrganizeIconButton(
                onClick = onDismiss
            ) {
                Text(
                    text = "×",
                    style = MaterialTheme.typography.bodyLarge,
                    color = AppColorScheme.info700
                )
            }
        }
    }
}

@Composable
private fun WarningBanner(
    message: String,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val typography = DesignSystemTypography()
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = AppColorScheme.warning100
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.md),
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            Text(
                text = message,
                style = typography.bodyMedium,
                color = AppColorScheme.warning700,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(Spacing.sm))

            OrganizeIconButton(
                onClick = onDismiss
            ) {
                Text(
                    text = "×",
                    style = MaterialTheme.typography.bodyLarge,
                    color = AppColorScheme.warning700
                )
            }
        }
    }
}

enum class BannerShowcaseItem(
    val title: String,
    val description: String
) {
    SUCCESS(
        title = "Success Banners",
        description = "Positive feedback and confirmation messages"
    ),
    ERROR(
        title = "Error Banners",
        description = "Error messages with retry functionality"
    ),
    INFO(
        title = "Info Banners",
        description = "Informational messages and updates"
    ),
    WARNING(
        title = "Warning Banners",
        description = "Cautionary messages and alerts"
    )
}
