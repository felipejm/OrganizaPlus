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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.joffer.organizeplus.designsystem.colors.ColorScheme as AppColorScheme
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.Typography

enum class FormLayout {
    LEFT_ALIGNED, // Label above field (mobile default)
    RIGHT_ALIGNED, // Label left, field right (desktop)
    DISPLAY_ONLY // Read-only
}

@Composable
fun OrganizeForm(
    modifier: Modifier = Modifier,
    layout: FormLayout = FormLayout.LEFT_ALIGNED,
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
                            color = Color(0xFFFAFAFA), // Neutral 100
                            shape = RoundedCornerShape(4.dp)
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
            color = Color(0xFF1F1F1F), // Neutral 700
            fontWeight = FontWeight.Medium
        )
        
        if (description != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = description,
                style = Typography.body,
                color = Color(0xFF8F8F8F) // Neutral 500
            )
        }
        
        Spacer(modifier = Modifier.height(Spacing.md))
        
        content()
        
        Spacer(modifier = Modifier.height(Spacing.lg))
        
        Divider(
            color = Color(0xFFF5F5F5), // Neutral 200
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
            color = Color(0xFF4F4F4F), // Neutral 600
            fontWeight = FontWeight.Normal
        )
        if (required) {
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "*",
                style = Typography.body,
                color = Color(0xFFFB612F), // Danger 500
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
            color = if (error != null) Color(0xFFFB612F) else Color(0xFF8F8F8F),
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
                color = Color(0xFFFFE7E2), // Danger 100
                shape = RoundedCornerShape(4.dp)
            )
            .padding(Spacing.md)
    ) {
        Text(
            text = message,
            style = Typography.body,
            color = Color(0xFFE42312), // Danger 700
            fontWeight = FontWeight.Medium
        )
    }
}
