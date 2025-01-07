package com.example.monitoringgizi.data.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "pemantauan_bulanan_ibu_hamil",
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
data class PemantauanBulananIbuHamilEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val noKTP: String,
    val tanggal: String,
    val pemantauanKe: Int,
    val usiaKehamilan: Int,
    val beratBadanKader: Float,
    val beratBadanNakes: Float?,
    val lingkarLuarAtas: Float,
    val keterangan: String
)