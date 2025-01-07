package com.example.monitoringgizi.data.model

data class PemantauanBulananBalita(
    val namaBalita: String,
    val tanggal: String,
    val pemantauanKe: Int,
    val beratBadanKader: Double,
    val beratBadanNakes: Double,
    val statusBeratBadan: String, // "naik" atau "tidak naik"
    val panjangBadanKader: Double,
    val panjangBadanNakes: Double
)