package com.example.monitoringgizi.ui.screens.balita

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.monitoringgizi.data.model.PemantauanBulananBalita
import com.example.monitoringgizi.viewmodel.BalitaViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PemantauanBulananBalitaScreen(
    namaBalita: String,
    viewModel: BalitaViewModel,
    onNavigateBack: () -> Unit
) {
    var tanggal by remember { mutableStateOf("") }
    var pemantauanKe by remember { mutableStateOf("") }
    var beratBadanKader by remember { mutableStateOf("") }
    var beratBadanNakes by remember { mutableStateOf("") }
    var statusBeratBadan by remember { mutableStateOf("naik") }
    var panjangBadanKader by remember { mutableStateOf("") }
    var panjangBadanNakes by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pemantauan Bulanan Balita") },
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
            OutlinedTextField(
                value = tanggal,
                onValueChange = { tanggal = it },
                label = { Text("Tanggal (DD/MM/YYYY)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = pemantauanKe,
                onValueChange = { pemantauanKe = it },
                label = { Text("Pemantauan Ke-") },
                modifier = Modifier.fillMaxWidth()
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Berat Badan", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = beratBadanKader,
                        onValueChange = { beratBadanKader = it },
                        label = { Text("Hasil Pengukuran Kader (kg)") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = beratBadanNakes,
                        onValueChange = { beratBadanNakes = it },
                        label = { Text("Hasil Konfirmasi Nakes (kg)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Panjang/Tinggi Badan", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = panjangBadanKader,
                        onValueChange = { panjangBadanKader = it },
                        label = { Text("Hasil Pengukuran Kader (cm)") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = panjangBadanNakes,
                        onValueChange = { panjangBadanNakes = it },
                        label = { Text("Hasil Konfirmasi Nakes (cm)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Status Berat Badan")
                Row {
                    RadioButton(
                        selected = statusBeratBadan == "naik",
                        onClick = { statusBeratBadan = "naik" }
                    )
                    Text("Naik")
                    Spacer(modifier = Modifier.width(8.dp))
                    RadioButton(
                        selected = statusBeratBadan == "tidak naik",
                        onClick = { statusBeratBadan = "tidak naik" }
                    )
                    Text("Tidak Naik")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    viewModel.tambahPemantauanBulanan(
                        PemantauanBulananBalita(
                            namaBalita = namaBalita,
                            tanggal = tanggal,
                            pemantauanKe = pemantauanKe.toIntOrNull() ?: 0,
                            beratBadanKader = beratBadanKader.toDoubleOrNull() ?: 0.0,
                            beratBadanNakes = beratBadanNakes.toDoubleOrNull() ?: 0.0,
                            statusBeratBadan = statusBeratBadan,
                            panjangBadanKader = panjangBadanKader.toDoubleOrNull() ?: 0.0,
                            panjangBadanNakes = panjangBadanNakes.toDoubleOrNull() ?: 0.0
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