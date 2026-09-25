package com.example.tugas2newsfeedsimulator

import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class NewsFeedSimulatorTest {

    @Test
    fun testStateFlowCounterBertambahSaatMarkAsReadDipanggil() = runTest {
        val simulator = NewsFeedSimulator()
        
        assertEquals(0, simulator.readCount.value, "Nilai awal harus 0")
        
        simulator.markAsRead()
        simulator.markAsRead()
        
        assertEquals(2, simulator.readCount.value, "Nilai harus 2 setelah dipanggil 2 kali")
    }

    @Test
    fun testFlowMengambil2DataBeritaSuksesDipancarkan() = runTest {
        val simulator = NewsFeedSimulator()
        
        // Memakai operator 'take' untuk hanya mengambil 2 emisi pertama agar tidak infinite loop
        val newsList = simulator.getNewsStream().take(2).toList()
        
        assertEquals(2, newsList.size, "Flow harus memancarkan 2 item")
    }
}
