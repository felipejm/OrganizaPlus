package com.joffer.organizeplus.designsystem.catalog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.joffer.organizeplus.designsystem.components.AppTopAppBarWithBackButton
import com.joffer.organizeplus.designsystem.components.MessageData
import com.joffer.organizeplus.designsystem.components.MessageType
import com.joffer.organizeplus.designsystem.components.OrganizeCard
import com.joffer.organizeplus.designsystem.components.OrganizeMessage
import com.joffer.organizeplus.designsystem.components.OrganizeMessageStack
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.Typography
import org.jetbrains.compose.resources.stringResource
import organizeplus.composeapp.generated.resources.Res
import organizeplus.composeapp.generated.resources.message_showcase_title

@Composable
fun MessageShowcaseScreen(
    onNavigateBack: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        AppTopAppBarWithBackButton(
            title = stringResource(Res.string.message_showcase_title),
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
                            text = "Basic Messages",
                            style = Typography.titleMedium
                        )

                        OrganizeMessage(
                            message = "This is an info message",
                            type = MessageType.INFO
                        )

                        OrganizeMessage(
                            message = "This is a warning message",
                            type = MessageType.WARNING
                        )

                        OrganizeMessage(
                            message = "This is an error message",
                            type = MessageType.ERROR
                        )

                        OrganizeMessage(
                            message = "This is a success message",
                            type = MessageType.SUCCESS
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
                            text = "Messages with Custom Icons",
                            style = Typography.titleMedium
                        )

                        OrganizeMessage(
                            message = "Custom info message with icon",
                            type = MessageType.INFO,
                            icon = Icons.Default.Info
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
                            text = "Message Stack (Multiple Messages)",
                            style = Typography.titleMedium
                        )

                        var messages by remember {
                            mutableStateOf(
                                listOf(
                                    MessageData(
                                        id = "1",
                                        message = "First message",
                                        type = MessageType.INFO
                                    ),
                                    MessageData(
                                        id = "2",
                                        message = "Second message",
                                        type = MessageType.SUCCESS
                                    ),
                                    MessageData(
                                        id = "3",
                                        message = "Third message",
                                        type = MessageType.WARNING
                                    )
                                )
                            )
                        }

                        OrganizeMessageStack(
                            messages = messages,
                            onDismiss = { id ->
                                messages = messages.filter { it.id != id }
                            }
                        )
                    }
                }
            }
        }
    }
}
