package com.joffer.organizeplus.features.duty.review.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joffer.organizeplus.features.duty.review.domain.entities.MonthlyDutyReview

@Composable
fun MonthlyDutySection(
    monthlyReview: MonthlyDutyReview,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFF8F9FA))
            .padding(bottom = 16.dp)
    ) {
        // Header with date and total (like in the image)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Date section
            Column {
                Text(
                    text = "Date",
                    fontSize = 12.sp,
                    color = Color(0xFF999999)
                )
                Text(
                    text = "${monthlyReview.month} ${monthlyReview.year}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF000000)
                )
            }
            
            // Total section
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "Total",
                    fontSize = 12.sp,
                    color = Color(0xFF999999)
                )
                Text(
                    text = "-$${String.format("%.2f", monthlyReview.totalPaid)}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF000000)
                )
            }
        }
        
        // Duty items (white background for each item)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .clip(RoundedCornerShape(8.dp))
        ) {
            monthlyReview.dutyItems.forEachIndexed { index, item ->
                DutyReviewItem(item = item)
                
                // Divider between items (except for the last one)
                if (index < monthlyReview.dutyItems.size - 1) {
                    Divider(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        color = Color(0xFFF0F0F0),
                        thickness = 1.dp
                    )
                }
            }
        }
    }
}
