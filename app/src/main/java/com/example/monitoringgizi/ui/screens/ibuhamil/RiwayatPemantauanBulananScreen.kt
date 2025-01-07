package com.example.monitoringgizi.ui.screens.ibuhamil

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.monitoringgizi.data.model.IbuHamil
import com.example.monitoringgizi.data.model.PemantauanBulananIbuHamil

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RiwayatPemantauanBulananScreen(
    ibuHamil: IbuHamil,
    pemantauanList: List<PemantauanBulananIbuHamil>,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Riwayat Pemantauan Bulanan") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Kembali")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            // Info Ibu Hamil
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Data Ibu Hamil",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Nama: ${ibuHamil.nama}")
                    Text("No KTP: ${ibuHamil.noKTP}")
                    Text("Usia Kehamilan: ${ibuHamil.usiaKehamilan} bulan")
                }
            }

            // Daftar Pemantauan
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                items(pemantauanList.sortedByDescending { it.tanggal }) { pemantauan ->
                    PemantauanBulananItem(pemantauan = pemantauan)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
private fun PemantauanBulananItem(
    pemantauan: PemantauanBulananIbuHamil,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Tanggal: ${pemantauan.tanggal}",
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = "Pemantauan ke-${pemantauan.pemantauanKe}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Usia Kehamilan: ${pemantauan.usiaKehamilan} bulan",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Berat Badan (Kader): ${pemantauan.beratBadanKader} kg",
                style = MaterialTheme.typography.bodyMedium
            )
            pemantauan.beratBadanNakes?.let {
                Text(
                    text = "Berat Badan (Nakes): $it kg",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Text(
                text = "Lingkar Lengan Atas: ${pemantauan.lingkarLuarAtas} cm",
                style = MaterialTheme.typography.bodyMedium
            )
            if (pemantauan.keterangan.isNotBlank()) {
                Text(
                    text = "Keterangan: ${pemantauan.keterangan}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}