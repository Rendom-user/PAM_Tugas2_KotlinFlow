package com.example.tugas2newsfeedsimulator

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Tugas2NewsFeedSimulator",
    ) {
        App()
    }
}