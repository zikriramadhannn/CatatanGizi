package com.example.monitoringgizi.ui.screens.balita

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.monitoringgizi.data.model.PencatatanHarianBalita
import com.example.monitoringgizi.viewmodel.BalitaViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PencatatanHarianBalitaScreen(
    namaBalita: String,
    viewModel: BalitaViewModel,
    onNavigateBack: () -> Unit
) {
    // States
    var waktu by remember { mutableStateOf("") }
    var pemberianKe by remember { mutableStateOf("") }
    var habis by remember { mutableStateOf(true) }
    var sehat by remember { mutableStateOf(true) }
    var keteranganSakit by remember { mutableStateOf("") }

    val currentDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pencatatan Harian Balita") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "Kembali")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // Info Balita
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Nama Balita: $namaBalita",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "Tanggal: $currentDate",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            // Form
            OutlinedTextField(
                value = waktu,
                onValueChange = { waktu = it },
                label = { Text("Waktu (HH:mm)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = pemberianKe,
                onValueChange = { pemberianKe = it },
                label = { Text("Pemberian Ke-") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Switch untuk status makanan
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Makanan Habis")
                Switch(
                    checked = habis,
                    onCheckedChange = { habis = it }
                )
            }

            // Switch untuk status kesehatan
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Kondisi Sehat")
                Switch(
                    checked = sehat,
                    onCheckedChange = { sehat = it }
                )
            }

            // Keterangan sakit (jika tidak sehat)
            if (!sehat) {
                OutlinedTextField(
                    value = keteranganSakit,
                    onValueChange = { keteranganSakit = it },
                    label = { Text("Keterangan Sakit") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Tombol Simpan
            Button(
                onClick = {
                    viewModel.tambahPencatatanHarian(
                        PencatatanHarianBalita(
                            namaBalita = namaBalita,
                            tanggal = currentDate,
                            waktu = waktu,
                            pemberianKe = pemberianKe.toIntOrNull() ?: 0,
                            habis = habis,
                            sehat = sehat,
                            keteranganSakit = if (!sehat) keteranganSakit else null
                        )
                    )
                    onNavigateBack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Simpan")
            }
        }
    }
}