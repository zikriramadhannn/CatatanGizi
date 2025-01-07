package com.example.monitoringgizi.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "balita")
data class BalitaEntity(
    @PrimaryKey
    val namaBalita: String,
    val namaIbu: String,
    val tanggalLahir: String,
    val beratBadanAwal: Float,
    val tinggiBadanAwal: Float,
    val alamat: String
)
