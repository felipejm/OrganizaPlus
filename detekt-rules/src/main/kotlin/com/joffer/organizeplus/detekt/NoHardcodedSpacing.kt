package com.joffer.organizeplus.detekt

import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity
import org.jetbrains.kotlin.psi.KtDotQualifiedExpression
import org.jetbrains.kotlin.psi.KtNameReferenceExpression

/**
 * Custom rule to prevent hardcoded spacing values.
 * Encourages use of the design system's Spacing object.
 */
class NoHardcodedSpacing(config: Config = Config.empty) : Rule(config) {
    
    override val issue = Issue(
        id = "NoHardcodedSpacing",
        severity = Severity.Style,
        description = "Hardcoded spacing values should not be used. " +
            "Use values from com.joffer.organizeplus.designsystem.spacing.Spacing instead.",
        debt = Debt.FIVE_MINS
    )

    private val allowedConstants = setOf(
        "CHART_HEIGHT", "CHART_PADDING", "Y_AXIS_LABEL_PADDING",
        "RIGHT_PADDING", "BAR_WIDTH", "BAR_SPACING", "BAR_CORNER_RADIUS",
        "CHART_LABEL_PADDING"
    )

    override fun visitDotQualifiedExpression(expression: KtDotQualifiedExpression) {
        super.visitDotQualifiedExpression(expression)
        
        // Check for patterns like: 16.dp, 8.dp, etc.
        val receiver = expression.receiverExpression.text
        val selector = expression.selectorExpression?.text
        
        if (selector == "dp" && receiver.toIntOrNull() != null) {
            val number = receiver.toInt()
            
            // Allow 0.dp and 1.dp as they're often needed for borders/dividers
            if (number <= 1) {
                return
            }
            
            // Check if it's used in a constant declaration (which is allowed)
            var parent = expression.parent
            var isConstant = false
            while (parent != null && !isConstant) {
                val parentText = parent.text
                if (parentText.contains("private const val") || 
                    parentText.contains("private val") ||
                    allowedConstants.any { parentText.contains(it) }) {
                    isConstant = true
                }
                parent = parent.parent
            }
            
            if (!isConstant) {
                report(
                    CodeSmell(
                        issue,
                        Entity.from(expression),
                        "Hardcoded spacing value ${receiver}.dp detected. " +
                            "Use Spacing.xs, Spacing.sm, Spacing.md, Spacing.lg, etc. from the design system instead."
                    )
                )
            }
        }
    }
}

