package com.joffer.organizeplus.common.utils

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.stringResource
import organizeplus.composeapp.generated.resources.Res
import organizeplus.composeapp.generated.resources.month_january
import organizeplus.composeapp.generated.resources.month_february
import organizeplus.composeapp.generated.resources.month_march
import organizeplus.composeapp.generated.resources.month_april
import organizeplus.composeapp.generated.resources.month_may
import organizeplus.composeapp.generated.resources.month_june
import organizeplus.composeapp.generated.resources.month_july
import organizeplus.composeapp.generated.resources.month_august
import organizeplus.composeapp.generated.resources.month_september
import organizeplus.composeapp.generated.resources.month_october
import organizeplus.composeapp.generated.resources.month_november
import organizeplus.composeapp.generated.resources.month_december
import organizeplus.composeapp.generated.resources.month_jan
import organizeplus.composeapp.generated.resources.month_feb
import organizeplus.composeapp.generated.resources.month_mar
import organizeplus.composeapp.generated.resources.month_apr
import organizeplus.composeapp.generated.resources.month_may_short
import organizeplus.composeapp.generated.resources.month_jun
import organizeplus.composeapp.generated.resources.month_jul
import organizeplus.composeapp.generated.resources.month_aug
import organizeplus.composeapp.generated.resources.month_sep
import organizeplus.composeapp.generated.resources.month_oct
import organizeplus.composeapp.generated.resources.month_nov
import organizeplus.composeapp.generated.resources.month_dec
import organizeplus.composeapp.generated.resources.month_unknown

object DateUtils {
    @Composable
    fun getMonthName(monthNumber: Int): String {
        return when (monthNumber) {
            1 -> stringResource(Res.string.month_january)
            2 -> stringResource(Res.string.month_february)
            3 -> stringResource(Res.string.month_march)
            4 -> stringResource(Res.string.month_april)
            5 -> stringResource(Res.string.month_may)
            6 -> stringResource(Res.string.month_june)
            7 -> stringResource(Res.string.month_july)
            8 -> stringResource(Res.string.month_august)
            9 -> stringResource(Res.string.month_september)
            10 -> stringResource(Res.string.month_october)
            11 -> stringResource(Res.string.month_november)
            12 -> stringResource(Res.string.month_december)
            else -> stringResource(Res.string.month_unknown)
        }
    }
    
    @Composable
    fun getShortMonthName(month: Int): String {
        return when (month) {
            1 -> stringResource(Res.string.month_jan)
            2 -> stringResource(Res.string.month_feb)
            3 -> stringResource(Res.string.month_mar)
            4 -> stringResource(Res.string.month_apr)
            5 -> stringResource(Res.string.month_may_short)
            6 -> stringResource(Res.string.month_jun)
            7 -> stringResource(Res.string.month_jul)
            8 -> stringResource(Res.string.month_aug)
            9 -> stringResource(Res.string.month_sep)
            10 -> stringResource(Res.string.month_oct)
            11 -> stringResource(Res.string.month_nov)
            12 -> stringResource(Res.string.month_dec)
            else -> month.toString() // fallback to month number
        }
    }
}
