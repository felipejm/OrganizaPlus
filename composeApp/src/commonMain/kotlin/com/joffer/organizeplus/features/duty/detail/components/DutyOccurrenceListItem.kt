package com.joffer.organizeplus.features.duty.detail.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.joffer.organizeplus.common.utils.DateUtils
import com.joffer.organizeplus.common.utils.toCurrencyFormat
import com.joffer.organizeplus.designsystem.colors.ColorScheme as AppColorScheme
import com.joffer.organizeplus.designsystem.components.OrganizeCard
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.Typography
import com.joffer.organizeplus.features.duty.occurrence.domain.entities.DutyOccurrence
import org.jetbrains.compose.resources.stringResource
import organizeplus.composeapp.generated.resources.Res
import organizeplus.composeapp.generated.resources.duty_occurrence_list_amount
import organizeplus.composeapp.generated.resources.duty_occurrence_list_delete

@Composable
fun DutyOccurrenceListItem(
    occurrence: DutyOccurrence,
    onDelete: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OrganizeCard(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(Spacing.md)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    // Show month and year label
                    val monthYearText = "${DateUtils.getMonthName(occurrence.completedDate.monthNumber)} ${occurrence.completedDate.year}"
                    
                    Text(
                        text = monthYearText,
                        style = Typography.labelLarge,
                        color = AppColorScheme.formSecondaryText,
                        fontWeight = FontWeight.Bold
                    )

                    // Show paid amount only if it exists (for payable duties)
                    if (occurrence.paidAmount != null && occurrence.paidAmount > 0) {
                        Spacer(modifier = Modifier.height(Spacing.xs))
                        Text(
                            text = occurrence.paidAmount.toCurrencyFormat(),
                            style = Typography.labelLarge,
                            color = AppColorScheme.formSecondaryText,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
                
                TextButton(
                    onClick = { onDelete(occurrence.id) },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = AppColorScheme.error
                    )
                ) {
                    Text(stringResource(Res.string.duty_occurrence_list_delete))
                }
            }
        }
    }
}
