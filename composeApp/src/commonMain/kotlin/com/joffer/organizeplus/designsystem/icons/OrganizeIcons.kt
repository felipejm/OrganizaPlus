package com.joffer.organizeplus.designsystem.icons

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import org.jetbrains.compose.resources.vectorResource
import organizeplus.composeapp.generated.resources.*
import organizeplus.composeapp.generated.resources.Res

/**
 * Custom icon set for OrganizePlus using SVG resources
 * 
 * Provides outlined and filled variants of common icons
 */
object OrganizeIcons {
    
    object Navigation {
        val Home: ImageVector
            @Composable
            get() = vectorResource(Res.drawable.home_outlined)
        
        val HomeFilled: ImageVector
            @Composable
            get() = vectorResource(Res.drawable.home_filled)
        
        val User: ImageVector
            @Composable
            get() = vectorResource(Res.drawable.user_outlined)
        
        val UserFilled: ImageVector
            @Composable
            get() = vectorResource(Res.drawable.user_filled)
        
        val Building: ImageVector
            @Composable
            get() = vectorResource(Res.drawable.building_outlined)
        
        val BuildingFilled: ImageVector
            @Composable
            get() = vectorResource(Res.drawable.building_outlined) // No filled version available
        
        val UserCircle: ImageVector
            @Composable
            get() = vectorResource(Res.drawable.user_circle_outlined)
        
        val UserCircleFilled: ImageVector
            @Composable
            get() = vectorResource(Res.drawable.user_circle_outlined) // No filled version available
    }
    
    object Actions {
        val Plus: ImageVector
            @Composable
            get() = vectorResource(Res.drawable.plus_outlined)
        
        val CirclePlus: ImageVector
            @Composable
            get() = vectorResource(Res.drawable.circle_plus_outlined)
        
        val CirclePlusFilled: ImageVector
            @Composable
            get() = vectorResource(Res.drawable.circle_plus_filled)
    }
}

