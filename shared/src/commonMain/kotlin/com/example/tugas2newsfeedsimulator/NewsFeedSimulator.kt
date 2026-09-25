/*
 * TUGAS PRAKTIKUM PENGEMBANGAN APLIKASI MOBILE
 * Nama : Hezkiel Rajani Aritonang
 * NIM  : 1231401118
 */
package com.example.tugas2newsfeedsimulator

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow

// --- Data Models ---
data class News(val id: Int, val title: String, val category: String)
data class NewsDisplay(val id: Int, val displayText: String)

class NewsFeedSimulator {
    // [4. StateFlow untuk menyimpan jumlah berita yang sudah dibaca]
    private val _readCount = MutableStateFlow(0)
    val readCount: StateFlow<Int> = _readCount.asStateFlow()

    private val categories = listOf("Tech", "Sports", "Politics")

    private val techTitles = listOf(
        "RILIS PEMBARUAN FITUR KOTLIN 2.0",
        "PEMANFAATAN AI GENERATIF DI APLIKASI MOBILE",
        "JETPACK COMPOSE KINI MENDUKUNG KMP",
        "TIPS OPTIMASI COROUTINES DI ANDROID",
        "PANDUAN MIGRASI KE KOTLIN FLOW"
    )

    // [1. Flow yang mensimulasikan data berita baru setiap 2 detik]
    fun getNewsStream(): Flow<News> = flow {
        var id = 1
        while (true) {
            delay(2000) 
            val category = categories.random()
            val title = if (category == "Tech") techTitles.random() else "Berita $category Terkini #$id"
            emit(News(id, title, category))
            
            // [Bonus: Simulasi error untuk di-catch]
            if (id % 5 == 0) throw RuntimeException("Koneksi server terputus sejenak!")
            id++
        }
    }

    // [5. Coroutines untuk mengambil detail berita secara async]
    suspend fun fetchNewsDetail(newsId: Int): String {
        delay(1000) // Simulasi waktu download
        return "Isi konten detail untuk berita ID: $newsId"
    }

    fun markAsRead() {
        _readCount.value++
    }
}
