package com.joffer.organizeplus.designsystem.icons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Custom icon set for OrganizePlus using Material Icons
 * 
 * Provides outlined and filled variants of common icons
 */
object OrganizeIcons {
    
    object Navigation {
        val Home: ImageVector = Icons.Outlined.Home
        val HomeFilled: ImageVector = Icons.Filled.Home
        
        val User: ImageVector = Icons.Outlined.PersonOutline
        val UserFilled: ImageVector = Icons.Filled.Person
        
        val Building: ImageVector = Icons.Outlined.BusinessCenter
        val BuildingFilled: ImageVector = Icons.Filled.BusinessCenter
        
        val UserCircle: ImageVector = Icons.Outlined.AccountCircle
        val UserCircleFilled: ImageVector = Icons.Filled.AccountCircle
        
        val Settings: ImageVector = Icons.Outlined.Settings
        val SettingsFilled: ImageVector = Icons.Filled.Settings
    }
    
    object Actions {
        val Plus: ImageVector = Icons.Outlined.Add
        val CirclePlus: ImageVector = Icons.Outlined.AddCircle
        val CirclePlusFilled: ImageVector = Icons.Filled.AddCircle
        
        val Edit: ImageVector = Icons.Outlined.Edit
        val Delete: ImageVector = Icons.Outlined.Delete
        val Check: ImageVector = Icons.Outlined.Check
        val Close: ImageVector = Icons.Outlined.Close
        
        val Search: ImageVector = Icons.Outlined.Search
        val Filter: ImageVector = Icons.Outlined.FilterList
        val Sort: ImageVector = Icons.Outlined.Sort
        
        val Visibility: ImageVector = Icons.Filled.Visibility
        val VisibilityOff: ImageVector = Icons.Filled.VisibilityOff
    }
    
    object Duty {
        val Calendar: ImageVector = Icons.Outlined.CalendarToday
        val CalendarFilled: ImageVector = Icons.Filled.CalendarToday
        
        val Clock: ImageVector = Icons.Outlined.Schedule
        val ClockFilled: ImageVector = Icons.Filled.Schedule
        
        val Task: ImageVector = Icons.Outlined.Task
        val TaskFilled: ImageVector = Icons.Filled.Task
        
        val CheckCircle: ImageVector = Icons.Outlined.CheckCircle
        val CheckCircleFilled: ImageVector = Icons.Filled.CheckCircle
        
        val Notification: ImageVector = Icons.Outlined.Notifications
        val NotificationFilled: ImageVector = Icons.Filled.Notifications
    }
    
    object System {
        val Info: ImageVector = Icons.Outlined.Info
        val Warning: ImageVector = Icons.Outlined.Warning
        val Error: ImageVector = Icons.Outlined.Error
        
        val Menu: ImageVector = Icons.Outlined.Menu
        val MoreVert: ImageVector = Icons.Outlined.MoreVert
        val MoreHoriz: ImageVector = Icons.Outlined.MoreHoriz
        
        val ArrowBack: ImageVector = Icons.Outlined.ArrowBack
        val ArrowBackIos: ImageVector = Icons.Outlined.ArrowBackIosNew
        val ArrowForward: ImageVector = Icons.Outlined.ArrowForward
        val ArrowUp: ImageVector = Icons.Outlined.ArrowUpward
        val ArrowDown: ImageVector = Icons.Outlined.ArrowDownward
        
        val Refresh: ImageVector = Icons.Outlined.Refresh
        
        val Star: ImageVector = Icons.Outlined.Star
        val StarFilled: ImageVector = Icons.Filled.Star
    }
}
