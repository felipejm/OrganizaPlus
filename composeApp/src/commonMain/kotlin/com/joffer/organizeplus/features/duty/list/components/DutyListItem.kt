package com.joffer.organizeplus.features.duty.list.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.joffer.organizeplus.designsystem.components.DutyCard
import com.joffer.organizeplus.features.dashboard.domain.entities.DutyWithLastOccurrence

@Composable
fun DutyListItem(
    dutyWithOccurrence: DutyWithLastOccurrence,
    onViewOccurrences: (Long) -> Unit,
    onDelete: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    DutyCard(
        dutyWithOccurrence = dutyWithOccurrence,
        onDutyClick = { onViewOccurrences(dutyWithOccurrence.duty.id) },
        onDelete = onDelete,
        showDeleteButton = true,
        showPaidChip = true,
        showCategoryInfo = true,
        modifier = modifier
    )
}
