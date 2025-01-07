package com.example.monitoringgizi.data.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "pencatatan_harian_ibu_hamil",
    foreignKeys = [
        ForeignKey(
            entity = IbuHamilEntity::class,
            parentColumns = ["noKTP"],
            childColumns = ["noKTP"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("noKTP")] // Tambahkan ini
)
data class PencatatanHarianIbuHamilEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val noKTP: String,
    val tanggal: String,
    val waktu: String,
    val pemberianKe: Int,
    val statusMakanan: Boolean,
    val statusKesehatan: Boolean,
    val keteranganSakit: String?
)