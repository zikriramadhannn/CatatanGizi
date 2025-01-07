package com.example.monitoringgizi.data.model

data class IbuHamil(
    val noKTP: String = "",
    val nama: String = "",
    val tanggalLahir: String = "",
    val usia: Int = 0,
    val usiaKehamilan: Int = 0,
    val beratBadanAwal: Float = 0f,
    val tinggiBadanAwal: Float = 0f,
    val imtPraHamil: Float = 0f,
    val lingkarLuarAtas: Float = 0f,
    val kadarHemoglobin: Float = 0f,
    val alamat: String = ""
)