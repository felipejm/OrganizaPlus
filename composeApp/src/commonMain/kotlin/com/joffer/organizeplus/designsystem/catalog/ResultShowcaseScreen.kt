package com.joffer.organizeplus.designsystem.catalog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.joffer.organizeplus.designsystem.components.AppTopAppBarWithBackButton
import com.joffer.organizeplus.designsystem.components.OrganizeCard
import com.joffer.organizeplus.designsystem.components.OrganizePrimaryButton
import com.joffer.organizeplus.designsystem.components.OrganizeResult
import com.joffer.organizeplus.designsystem.components.OrganizeResultCard
import com.joffer.organizeplus.designsystem.components.OrganizeSecondaryButton
import com.joffer.organizeplus.designsystem.components.ResultType
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.Typography
import org.jetbrains.compose.resources.stringResource
import organizeplus.composeapp.generated.resources.Res
import organizeplus.composeapp.generated.resources.result_showcase_title

@Composable
fun ResultShowcaseScreen(
    onNavigateBack: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        AppTopAppBarWithBackButton(
            title = stringResource(Res.string.result_showcase_title),
            onBackClick = onNavigateBack
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(Spacing.md),
            verticalArrangement = Arrangement.spacedBy(Spacing.lg)
        ) {
            item {
                OrganizeCard(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(Spacing.md),
                        verticalArrangement = Arrangement.spacedBy(Spacing.md)
                    ) {
                        Text(
                            text = "Success Result",
                            style = Typography.titleMedium
                        )

                        OrganizeResult(
                            type = ResultType.SUCCESS,
                            title = "Success!",
                            description = "Your action was completed successfully.",
                            actions = {
                                OrganizePrimaryButton(
                                    text = "Continue",
                                    onClick = { }
                                )
                            }
                        )
                    }
                }
            }

            item {
                OrganizeCard(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(Spacing.md),
                        verticalArrangement = Arrangement.spacedBy(Spacing.md)
                    ) {
                        Text(
                            text = "Error Result",
                            style = Typography.titleMedium
                        )

                        OrganizeResult(
                            type = ResultType.ERROR,
                            title = "Error!",
                            description = "Something went wrong. Please try again.",
                            actions = {
                                OrganizePrimaryButton(
                                    text = "Retry",
                                    onClick = { }
                                )
                                OrganizeSecondaryButton(
                                    text = "Cancel",
                                    onClick = { }
                                )
                            }
                        )
                    }
                }
            }

            item {
                OrganizeCard(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(Spacing.md),
                        verticalArrangement = Arrangement.spacedBy(Spacing.md)
                    ) {
                        Text(
                            text = "Warning Result",
                            style = Typography.titleMedium
                        )

                        OrganizeResult(
                            type = ResultType.WARNING,
                            title = "Warning!",
                            description = "Please review your information before proceeding."
                        )
                    }
                }
            }

            item {
                OrganizeCard(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(Spacing.md),
                        verticalArrangement = Arrangement.spacedBy(Spacing.md)
                    ) {
                        Text(
                            text = "Info Result",
                            style = Typography.titleMedium
                        )

                        OrganizeResult(
                            type = ResultType.INFO,
                            title = "Information",
                            description = "Here's some important information for you."
                        )
                    }
                }
            }

            item {
                OrganizeCard(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(Spacing.md),
                        verticalArrangement = Arrangement.spacedBy(Spacing.md)
                    ) {
                        Text(
                            text = "Result Card",
                            style = Typography.titleMedium
                        )

                        OrganizeResultCard(
                            type = ResultType.SUCCESS,
                            title = "Card Result",
                            description = "This result is displayed in a card container.",
                            actions = {
                                OrganizePrimaryButton(
                                    text = "Action",
                                    onClick = { }
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}
