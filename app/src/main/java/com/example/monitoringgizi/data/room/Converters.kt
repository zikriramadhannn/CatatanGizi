package com.example.monitoringgizi.data.room

import com.example.monitoringgizi.data.model.PencatatanHarianIbuHamil
import com.example.monitoringgizi.data.model.PemantauanBulananIbuHamil
import com.example.monitoringgizi.data.room.entity.PencatatanHarianIbuHamilEntity
import com.example.monitoringgizi.data.room.entity.PemantauanBulananIbuHamilEntity
import com.example.monitoringgizi.data.model.Balita
import com.example.monitoringgizi.data.model.PemantauanBulananBalita
import com.example.monitoringgizi.data.model.PencatatanHarianBalita
import com.example.monitoringgizi.data.room.entity.BalitaEntity
import com.example.monitoringgizi.data.room.entity.PemantauanBulananBalitaEntity
import com.example.monitoringgizi.data.room.entity.PencatatanHarianBalitaEntity

fun PencatatanHarianIbuHamil.toEntity() = PencatatanHarianIbuHamilEntity(
    noKTP = noKTP,
    tanggal = tanggal,
    waktu = waktu,
    pemberianKe = pemberianKe,
    statusMakanan = statusMakanan,
    statusKesehatan = statusKesehatan,
    keteranganSakit = keteranganSakit
)

fun PencatatanHarianIbuHamilEntity.toPencatatanHarian() = PencatatanHarianIbuHamil(
    noKTP = noKTP,
    tanggal = tanggal,
    waktu = waktu,
    pemberianKe = pemberianKe,
    statusMakanan = statusMakanan,
    statusKesehatan = statusKesehatan,
    keteranganSakit = keteranganSakit
)

fun PemantauanBulananIbuHamil.toEntity() = PemantauanBulananIbuHamilEntity(
    noKTP = noKTP,
    tanggal = tanggal,
    pemantauanKe = pemantauanKe,
    usiaKehamilan = usiaKehamilan,
    beratBadanKader = beratBadanKader,
    beratBadanNakes = beratBadanNakes,
    lingkarLuarAtas = lingkarLuarAtas,
    keterangan = keterangan
)

fun PemantauanBulananIbuHamilEntity.toPemantauanBulanan() = PemantauanBulananIbuHamil(
    noKTP = noKTP,
    tanggal = tanggal,
    pemantauanKe = pemantauanKe,
    usiaKehamilan = usiaKehamilan,
    beratBadanKader = beratBadanKader,
    beratBadanNakes = beratBadanNakes,
    lingkarLuarAtas = lingkarLuarAtas,
    keterangan = keterangan
)

// Balita Converters
fun Balita.toEntity() = BalitaEntity(
    namaBalita = namaBalita,
    namaIbu = namaIbu,
    tanggalLahir = tanggalLahir,
    beratBadanAwal = beratBadanAwal.toFloat(),
    tinggiBadanAwal = tinggiBadanAwal.toFloat(),
    alamat = alamat
)

fun BalitaEntity.toBalita() = Balita(
    namaBalita = namaBalita,
    namaIbu = namaIbu,
    tanggalLahir = tanggalLahir,
    beratBadanAwal = beratBadanAwal.toDouble(),
    tinggiBadanAwal = tinggiBadanAwal.toDouble(),
    alamat = alamat
)

// Pencatatan Harian Converters
fun PencatatanHarianBalita.toEntity() = PencatatanHarianBalitaEntity(
    namaBalita = namaBalita,
    tanggal = tanggal,
    waktu = waktu,
    pemberianKe = pemberianKe,
    habis = habis,
    sehat = sehat,
    keteranganSakit = keteranganSakit
)

fun PencatatanHarianBalitaEntity.toPencatatanHarian() = PencatatanHarianBalita(
    namaBalita = namaBalita,
    tanggal = tanggal,
    waktu = waktu,
    pemberianKe = pemberianKe,
    habis = habis,
    sehat = sehat,
    keteranganSakit = keteranganSakit
)

// Pemantauan Bulanan Converters
fun PemantauanBulananBalita.toEntity() = PemantauanBulananBalitaEntity(
    namaBalita = namaBalita,
    tanggal = tanggal,
    pemantauanKe = pemantauanKe,
    beratBadanKader = beratBadanKader.toFloat(),
    beratBadanNakes = beratBadanNakes.toFloat(),
    statusBeratBadan = statusBeratBadan,
    panjangBadanKader = panjangBadanKader.toFloat(),
    panjangBadanNakes = panjangBadanNakes.toFloat()
)

fun PemantauanBulananBalitaEntity.toPemantauanBulanan() = PemantauanBulananBalita(
    namaBalita = namaBalita,
    tanggal = tanggal,
    pemantauanKe = pemantauanKe,
    beratBadanKader = beratBadanKader.toDouble(),
    beratBadanNakes = beratBadanNakes.toDouble(),
    statusBeratBadan = statusBeratBadan,
    panjangBadanKader = panjangBadanKader.toDouble(),
    panjangBadanNakes = panjangBadanNakes.toDouble()
)