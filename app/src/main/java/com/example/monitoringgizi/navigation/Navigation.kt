package com.example.monitoringgizi.navigation

sealed class Screen(val route: String) {
    object Landing : Screen("landing")
    object IbuHamil : Screen("ibu_hamil")
    object Balita : Screen("balita")


    // Route Ibu Hamil
    object PencatatanHarian : Screen("pencatatan_harian/{noKTP}") {
        fun createRoute(noKTP: String) = "pencatatan_harian/$noKTP"
    }
    object RiwayatPencatatan : Screen("riwayat_pencatatan/{noKTP}") {
        fun createRoute(noKTP: String) = "riwayat_pencatatan/$noKTP"
    }
    object PemantauanBulanan : Screen("pemantauan_bulanan/{noKTP}") {
        fun createRoute(noKTP: String) = "pemantauan_bulanan/$noKTP"
    }
    object RiwayatPemantauanBulanan : Screen("riwayat_pemantauan_bulanan/{noKTP}") {
        fun createRoute(noKTP: String) = "riwayat_pemantauan_bulanan/$noKTP"
    }


    // Route Balita
    object TambahBalita : Screen("tambah_balita")
    object PencatatanHarianBalita : Screen("pencatatan_harian_balita/{nama}") {
        fun createRoute(nama: String) = "pencatatan_harian_balita/$nama"
    }
    object PemantauanBulananBalita : Screen("pemantauan_bulanan_balita/{nama}") {
        fun createRoute(nama: String) = "pemantauan_bulanan_balita/$nama"
    }
    object RiwayatPencatatanBalita : Screen("riwayat_pencatatan_balita/{nama}") {
        fun createRoute(nama: String) = "riwayat_pencatatan_balita/$nama"
    }
    object RiwayatPemantauanBalita : Screen("riwayat_pemantauan_balita/{nama}") {
        fun createRoute(nama: String) = "riwayat_pemantauan_balita/$nama"
    }
}