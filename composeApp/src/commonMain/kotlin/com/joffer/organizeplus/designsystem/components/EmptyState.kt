package com.joffer.organizeplus.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.joffer.organizeplus.designsystem.colors.SemanticColors
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.DesignSystemTypography

@Composable
fun EmptyState(
    onAddObligation: () -> Unit,
    modifier: Modifier = Modifier
) {
    val typography = DesignSystemTypography()
    OrganizeCard(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Icon placeholder - can be replaced with actual icon later
            Box(
                modifier = Modifier
                    .size(Spacing.listItemHeight)
                    .background(
                        SemanticColors.OnBackground.tertiary.copy(alpha = 0.3f),
                        CircleShape
                    )
            )

            Spacer(modifier = Modifier.height(Spacing.lg))

            Text(
                text = "Nenhuma obrigação criada ainda",
                style = typography.titleMedium,
                fontWeight = FontWeight.Medium,
                color = SemanticColors.Foreground.primary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(Spacing.sm))

            Text(
                text = "Comece organizando suas obrigações financeiras e mantenha tudo sob controle.",
                style = typography.bodyMedium,
                color = SemanticColors.Foreground.secondary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(Spacing.xl))

            OrganizePrimaryButton(
                text = "Adicionar agora",
                onClick = onAddObligation,
                icon = null
            )
        }
    }
}
