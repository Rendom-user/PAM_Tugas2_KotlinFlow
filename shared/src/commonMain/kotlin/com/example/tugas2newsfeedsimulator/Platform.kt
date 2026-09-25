package com.example.tugas2newsfeedsimulator

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform