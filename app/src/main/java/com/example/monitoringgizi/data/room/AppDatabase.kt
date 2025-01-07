package com.example.monitoringgizi.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.monitoringgizi.data.room.dao.BalitaDao
import com.example.monitoringgizi.data.room.dao.IbuHamilDao
import com.example.monitoringgizi.data.room.entity.BalitaEntity
import com.example.monitoringgizi.data.room.entity.IbuHamilEntity
import com.example.monitoringgizi.data.room.entity.PemantauanBulananBalitaEntity
import com.example.monitoringgizi.data.room.entity.PencatatanHarianIbuHamilEntity
import com.example.monitoringgizi.data.room.entity.PemantauanBulananIbuHamilEntity
import com.example.monitoringgizi.data.room.entity.PencatatanHarianBalitaEntity

@Database(
    entities = [
        IbuHamilEntity::class,
        PencatatanHarianIbuHamilEntity::class,
        PemantauanBulananIbuHamilEntity::class,
        BalitaEntity::class,
        PencatatanHarianBalitaEntity::class,
        PemantauanBulananBalitaEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ibuHamilDao(): IbuHamilDao
    abstract fun balitaDao(): BalitaDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "monitoring_gizi_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}