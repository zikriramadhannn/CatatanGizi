package com.example.monitoringgizi.data.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "pemantauan_bulanan_balita",
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
data class PemantauanBulananBalitaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val namaBalita: String,
    val tanggal: String,
    val pemantauanKe: Int,
    val beratBadanKader: Float,
    val beratBadanNakes: Float,
    val statusBeratBadan: String,
    val panjangBadanKader: Float,
    val panjangBadanNakes: Float
)