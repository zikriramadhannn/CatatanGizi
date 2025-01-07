package com.example.monitoringgizi.data.model

data class PencatatanHarianIbuHamil(
    val noKTP: String,
    val tanggal: String,
    val waktu: String,
    val pemberianKe: Int,
    val statusMakanan: Boolean,    // true = habis, false = tidak habis
    val statusKesehatan: Boolean,  // true = sehat, false = sakit
    val keteranganSakit: String? = null
)