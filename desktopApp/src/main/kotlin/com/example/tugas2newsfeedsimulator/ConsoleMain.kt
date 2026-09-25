package com.example.tugas2newsfeedsimulator

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun main() = runBlocking {
    val simulator = NewsFeedSimulator()
    val targetCategory = "Tech"

    println("==================================================")
    println("  SIMULATOR NEWS FEED (KOTLIN FLOW & COROUTINE)   ")
    println("==================================================")
    println("Nama Mahasiswa : Hezkiel Rajani Aritonang")
    println("NIM            : 1231401118")
    println("Filter Kategori: [$targetCategory]")
    println("Status Awal Dibaca: 0")
    println("--------------------------------------------------")
    
    // Memantau StateFlow secara background
    launch {
        simulator.readCount.collect { count ->
            println("[StateFlow Notification] Total dibaca saat ini: $count")
            println("--------------------------------------------------")
        }
    }

    simulator.getNewsStream()
        // [2. Filter berita berdasarkan kategori tertentu]
        .filter { news -> news.category == targetCategory }
        // [3. Transform data menjadi format yang ditampilkan]
        .map { news -> 
            val formattedText = """
                |BERITA BARU MASUK!
                |ID    : #NEWS-${news.id}
                |Judul : ${news.title}
                |Tag   : [${news.category}]
            """.trimMargin()
            NewsDisplay(news.id, formattedText)
        }
        // [Bonus: Implementasi error handling dengan .catch]
        .catch { e -> 
            println("⚠️ [ERROR]: ${e.message}") 
            println("--------------------------------------------------")
        }
        .collect { displayNews ->
            println(displayNews.displayText)
            println("--> [Async Loading] Mengambil detail berita ID #${displayNews.id} dari server...")

            // [5. Async fetching - tidak memblokir flow]
            launch {
                try {
                    val detail = simulator.fetchNewsDetail(displayNews.id)
                    println("Hasil: Detail lengkap berita ID #${displayNews.id} berhasil dimuat.")
                    simulator.markAsRead()
                } catch (e: Exception) {
                    // [Bonus: Exception handling]
                    println("Hasil: ❌ Gagal mengambil detail (ID #${displayNews.id}): ${e.message}")
                }
            }
        }
}
