package com.example.monitoringgizi.data.model

data class PemantauanBulananIbuHamil(
    val noKTP: String,
    val tanggal: String,
    val pemantauanKe: Int,
    val usiaKehamilan: Int,
    val beratBadanKader: Float,
    val beratBadanNakes: Float?,
    val lingkarLuarAtas: Float,
    val keterangan: String = ""
)