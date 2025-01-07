package com.example.monitoringgizi.data.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "pencatatan_harian_balita",
    foreignKeys = [
        ForeignKey(
            entity = BalitaEntity::class,
            parentColumns = ["namaBalita"],
            childColumns = ["namaBalita"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("namaBalita")]
)
data class PencatatanHarianBalitaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val namaBalita: String,
    val tanggal: String,
    val waktu: String,
    val pemberianKe: Int,
    val habis: Boolean,
    val sehat: Boolean,
    val keteranganSakit: String?
)