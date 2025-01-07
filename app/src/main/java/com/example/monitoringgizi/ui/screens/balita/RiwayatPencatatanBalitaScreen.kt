package com.example.monitoringgizi.ui.screens.balita

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.monitoringgizi.viewmodel.BalitaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RiwayatPencatatanBalitaScreen(
    namaBalita: String,
    viewModel: BalitaViewModel,
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val pencatatanList by viewModel.getPencatatanByNama(namaBalita)
        .collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Riwayat Pencatatan Harian") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "Kembali")
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            scope.launch {
                                try {
                                    viewModel.generateLaporanHarian(context, namaBalita)
                                    Toast.makeText(context, "PDF berhasil dibuat", Toast.LENGTH_SHORT).show()
                                } catch (e: Exception) {
                                    Toast.makeText(context, "Gagal membuat PDF: ${e.message}", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    ) {
                        Icon(Icons.Default.PictureAsPdf, "Generate PDF")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            if (pencatatanList.isEmpty()) {
                item {
                    Text(
                        text = "Belum ada data pencatatan untuk balita ini",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                }
            } else {
                items(pencatatanList.sortedBy { it.tanggal }) { pencatatan ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Tanggal: ${pencatatan.tanggal}",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("Waktu: ${pencatatan.waktu}")
                            Text("Pemberian Ke-${pencatatan.pemberianKe}")
                            Text("Status Makanan: ${if (pencatatan.habis) "Habis" else "Tidak Habis"}")
                            Text("Kondisi: ${if (pencatatan.sehat) "Sehat" else "Sakit"}")
                            if (!pencatatan.sehat && !pencatatan.keteranganSakit.isNullOrBlank()) {
                                Text("Keterangan: ${pencatatan.keteranganSakit}")
                            }
                        }
                    }
                }
            }
        }
    }
}