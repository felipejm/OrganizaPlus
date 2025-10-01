package com.joffer.organizeplus.designsystem.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.Typography
import kotlinx.coroutines.delay

private const val MESSAGE_ENTER_ANIMATION_DURATION = 200
private const val MESSAGE_EXIT_ANIMATION_DURATION = 150

enum class MessageType {
    INFO, WARNING, ERROR, SUCCESS
}

@Composable
fun OrganizeMessage(
    message: String,
    type: MessageType = MessageType.INFO,
    icon: ImageVector? = null,
    modifier: Modifier = Modifier,
    autoHide: Boolean = true,
    duration: Long = 4000L,
    onDismiss: (() -> Unit)? = null
) {
    var isVisible by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        if (autoHide) {
            delay(duration)
            isVisible = false
            onDismiss?.invoke()
        }
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(
            initialOffsetY = { -it },
            animationSpec = tween(MESSAGE_ENTER_ANIMATION_DURATION)
        ) + fadeIn(animationSpec = tween(MESSAGE_ENTER_ANIMATION_DURATION)),
        exit = slideOutVertically(
            targetOffsetY = { -it },
            animationSpec = tween(MESSAGE_EXIT_ANIMATION_DURATION)
        ) + fadeOut(animationSpec = tween(MESSAGE_EXIT_ANIMATION_DURATION))
    ) {
        val (colors, iconVector) = when (type) {
            MessageType.INFO -> InfoColors to (icon ?: Icons.Default.Info)
            MessageType.WARNING -> WarningColors to (icon ?: Icons.Default.Warning)
            MessageType.ERROR -> ErrorColors to (icon ?: Icons.Default.Warning)
            MessageType.SUCCESS -> SuccessColors to (icon ?: Icons.Default.CheckCircle)
        }

        Box(
            modifier = modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(8.dp),
                    ambientColor = Color.Black.copy(alpha = 0.08f),
                    spotColor = Color.Black.copy(alpha = 0.12f)
                )
                .background(
                    color = colors.background,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(horizontal = Spacing.md, vertical = Spacing.sm)
                .semantics {
                    contentDescription = message
                }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    imageVector = iconVector,
                    contentDescription = null,
                    tint = colors.icon,
                    modifier = Modifier.size(16.dp)
                )

                Spacer(modifier = Modifier.width(Spacing.sm))

                Text(
                    text = message,
                    style = Typography.body,
                    color = colors.text,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}

@Composable
fun OrganizeMessageStack(
    messages: List<MessageData>,
    modifier: Modifier = Modifier,
    onDismiss: (String) -> Unit = {}
) {
    Box(modifier = modifier) {
        messages.take(3).forEachIndexed { index, messageData ->
            OrganizeMessage(
                message = messageData.message,
                type = messageData.type,
                icon = messageData.icon,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = (index * 8).dp + (index * 4).dp),
                autoHide = messageData.autoHide,
                duration = messageData.duration,
                onDismiss = { onDismiss(messageData.id) }
            )
        }
    }
}

data class MessageData(
    val id: String,
    val message: String,
    val type: MessageType = MessageType.INFO,
    val icon: ImageVector? = null,
    val autoHide: Boolean = true,
    val duration: Long = 4000L
)

// Color schemes for each message type
private val InfoColors = MessageColors(
    background = Color(0xFFE4F3FF), // Info 100
    text = Color(0xFF1A70D2), // Info 700
    icon = Color(0xFF2E8CF0) // Info 600
)

private val WarningColors = MessageColors(
    background = Color(0xFFFFF6D2), // Warning 100
    text = Color(0xFFF29D0E), // Warning 700
    icon = Color(0xFFF5B42C) // Warning 600
)

private val ErrorColors = MessageColors(
    background = Color(0xFFFFE7E2), // Danger 100
    text = Color(0xFFE42312), // Danger 700
    icon = Color(0xFFF54A2C) // Danger 600
)

private val SuccessColors = MessageColors(
    background = Color(0xFFE0F7EB), // Success 100
    text = Color(0xFF1E9E4B), // Success 700
    icon = Color(0xFF44B76C) // Success 600
)

private data class MessageColors(
    val background: Color,
    val text: Color,
    val icon: Color
)
