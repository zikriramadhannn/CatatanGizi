package com.example.monitoringgizi.data.model

data class PencatatanHarianBalita(
    val namaBalita: String,
    val tanggal: String,
    val waktu: String,
    val pemberianKe: Int,
    val habis: Boolean,
    val sehat: Boolean,
    val keteranganSakit: String? = null
)