package com.example.monitoringgizi.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ibu_hamil")
data class IbuHamilEntity(
    @PrimaryKey
    val noKTP: String,
    val nama: String,
    val tanggalLahir: String,
    val usia: Int,
    val usiaKehamilan: Int,
    val beratBadanAwal: Float,
    val tinggiBadanAwal: Float,
    val imtPraHamil: Float,
    val lingkarLuarAtas: Float,
    val kadarHemoglobin: Float,
    val alamat: String
)