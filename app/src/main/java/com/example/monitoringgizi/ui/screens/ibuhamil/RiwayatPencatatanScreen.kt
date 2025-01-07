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
import com.example.monitoringgizi.data.model.PencatatanHarianIbuHamil

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RiwayatPencatatanScreen(
    ibuHamil: IbuHamil,
    pencatatanList: List<PencatatanHarianIbuHamil>,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Riwayat Pencatatan") },
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

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                items(pencatatanList.sortedByDescending { "${it.tanggal}${it.waktu}" }) { pencatatan ->
                    PencatatanItem(pencatatan = pencatatan)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
private fun PencatatanItem(
    pencatatan: PencatatanHarianIbuHamil,
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
                text = "Tanggal: ${pencatatan.tanggal}",
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = "Waktu: ${pencatatan.waktu}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Pemberian ke-${pencatatan.pemberianKe}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Status Makanan: ${if (pencatatan.statusMakanan) "Habis" else "Tidak Habis"}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Status Kesehatan: ${if (pencatatan.statusKesehatan) "Sehat" else "Sakit"}",
                style = MaterialTheme.typography.bodyMedium
            )
            if (!pencatatan.statusKesehatan && pencatatan.keteranganSakit != null) {
                Text(
                    text = "Keterangan: ${pencatatan.keteranganSakit}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}