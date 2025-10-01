package com.joffer.organizeplus

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
