package com.joffer.organizeplus.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.Typography
import com.joffer.organizeplus.designsystem.colors.ColorScheme as AppColorScheme

enum class FormLayout {
    LEFT_ALIGNED, // Label above field (mobile default)
    RIGHT_ALIGNED, // Label left, field right (desktop)
    DISPLAY_ONLY // Read-only
}

@Composable
fun OrganizeForm(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Spacing.lg)
    ) {
        content()
    }
}

@Composable
fun OrganizeFormField(
    label: String,
    content: @Composable () -> Unit,
    helper: String? = null,
    error: String? = null,
    modifier: Modifier = Modifier,
    layout: FormLayout = FormLayout.LEFT_ALIGNED,
    required: Boolean = false
) {
    when (layout) {
        FormLayout.LEFT_ALIGNED -> {
            Column(modifier = modifier) {
                FormLabel(
                    text = label,
                    required = required,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                content()
                FormHelperOrError(
                    helper = helper,
                    error = error,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
        FormLayout.RIGHT_ALIGNED -> {
            Row(
                modifier = modifier,
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(Spacing.lg)
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    FormLabel(
                        text = label,
                        required = required
                    )
                }
                Column(
                    modifier = Modifier.weight(2f)
                ) {
                    content()
                    FormHelperOrError(
                        helper = helper,
                        error = error,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }
        FormLayout.DISPLAY_ONLY -> {
            Column(modifier = modifier) {
                FormLabel(
                    text = label,
                    required = required,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = AppColorScheme.neutral100,
                            shape = RoundedCornerShape(Spacing.Radius.xs)
                        )
                        .padding(Spacing.md)
                ) {
                    content()
                }
            }
        }
    }
}

@Composable
fun OrganizeFormSection(
    title: String,
    description: String? = null,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = title,
            style = Typography.titleMedium,
            color = AppColorScheme.neutral700,
            fontWeight = FontWeight.Medium
        )

        if (description != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = description,
                style = Typography.body,
                color = AppColorScheme.neutral500
            )
        }

        Spacer(modifier = Modifier.height(Spacing.md))

        content()

        Spacer(modifier = Modifier.height(Spacing.lg))

        Divider(
            color = AppColorScheme.neutral200,
            thickness = 1.dp
        )
    }
}

@Composable
fun OrganizeFormActions(
    primaryAction: @Composable () -> Unit,
    secondaryAction: (@Composable () -> Unit)? = null,
    modifier: Modifier = Modifier,
    layout: FormLayout = FormLayout.LEFT_ALIGNED
) {
    when (layout) {
        FormLayout.LEFT_ALIGNED -> {
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(Spacing.sm)
            ) {
                primaryAction()
                secondaryAction?.invoke()
            }
        }
        FormLayout.RIGHT_ALIGNED, FormLayout.DISPLAY_ONLY -> {
            Row(
                modifier = modifier,
                horizontalArrangement = Arrangement.spacedBy(Spacing.sm)
            ) {
                if (secondaryAction != null) {
                    Box(modifier = Modifier.weight(1f)) {
                        secondaryAction()
                    }
                }
                Box(modifier = Modifier.weight(1f)) {
                    primaryAction()
                }
            }
        }
    }
}

@Composable
private fun FormLabel(
    text: String,
    required: Boolean = false,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = Typography.body,
            color = AppColorScheme.neutral600,
            fontWeight = FontWeight.Normal
        )
        if (required) {
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "*",
                style = Typography.body,
                color = AppColorScheme.danger500,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun FormHelperOrError(
    helper: String? = null,
    error: String? = null,
    modifier: Modifier = Modifier
) {
    if (error != null || helper != null) {
        Text(
            text = error ?: helper ?: "",
            style = Typography.caption,
            color = if (error != null) AppColorScheme.danger500 else AppColorScheme.neutral500,
            modifier = modifier
        )
    }
}

@Composable
fun OrganizeFormValidationBanner(
    message: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = AppColorScheme.danger100,
                shape = RoundedCornerShape(Spacing.Radius.xs)
            )
            .padding(Spacing.md)
    ) {
        Text(
            text = message,
            style = Typography.body,
            color = AppColorScheme.danger700,
            fontWeight = FontWeight.Medium
        )
    }
}
