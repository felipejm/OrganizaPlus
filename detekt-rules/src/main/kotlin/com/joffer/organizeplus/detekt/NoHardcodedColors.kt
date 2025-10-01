package com.joffer.organizeplus.detekt

import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity
import org.jetbrains.kotlin.psi.KtCallExpression
import org.jetbrains.kotlin.psi.KtValueArgument

/**
 * Custom rule to prevent hardcoded Color values.
 * Encourages use of the design system's ColorScheme.
 */
class NoHardcodedColors(config: Config = Config.empty) : Rule(config) {
    
    override val issue = Issue(
        id = "NoHardcodedColors",
        severity = Severity.Style,
        description = "Hardcoded colors should not be used. " +
            "Use colors from com.joffer.organizeplus.designsystem.colors.ColorScheme instead.",
        debt = Debt.FIVE_MINS
    )

    override fun visitCallExpression(expression: KtCallExpression) {
        super.visitCallExpression(expression)
        
        // Skip if we're in the ColorScheme.kt file itself or in catalog/showcase files
        val filePath = expression.containingKtFile.virtualFilePath
        if (filePath.contains("designsystem/colors/ColorScheme.kt") ||
            filePath.contains("/catalog/") ||
            filePath.contains("/showcase/")) {
            return
        }
        
        val calleeText = expression.calleeExpression?.text
        
        // Detect Color(...) constructor calls with hex values
        if (calleeText == "Color") {
            val arguments = expression.valueArguments
            if (arguments.isNotEmpty()) {
                val firstArg = arguments[0].getArgumentExpression()?.text
                // Check if it's a hex color value (0xFF...)
                if (firstArg != null && firstArg.startsWith("0x")) {
                    report(
                        CodeSmell(
                            issue,
                            Entity.from(expression),
                            "Hardcoded color value $firstArg detected. " +
                                "Use AppColorScheme from the design system instead."
                        )
                    )
                }
            }
        }
    }
}

