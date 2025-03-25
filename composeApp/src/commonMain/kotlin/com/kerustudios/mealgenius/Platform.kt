package com.kerustudios.mealgenius

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform