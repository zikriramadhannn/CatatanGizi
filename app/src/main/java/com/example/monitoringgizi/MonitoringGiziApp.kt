package com.example.monitoringgizi

import android.app.Application
import com.example.monitoringgizi.data.repository.BalitaRepository
import com.example.monitoringgizi.data.repository.IbuHamilRepository
import com.example.monitoringgizi.data.room.AppDatabase

class MonitoringGiziApp : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val ibuHamilRepository by lazy { IbuHamilRepository(database.ibuHamilDao()) }
    val balitaRepository by lazy { BalitaRepository(database.balitaDao()) }
}