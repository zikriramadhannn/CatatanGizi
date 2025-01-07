package com.example.monitoringgizi.ui.screens.balita

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.monitoringgizi.data.model.Balita
import com.example.monitoringgizi.viewmodel.BalitaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TambahBalitaScreen(
    viewModel: BalitaViewModel,
    onNavigateBack: () -> Unit
) {
    var namaBalita by remember { mutableStateOf("") }
    var namaIbu by remember { mutableStateOf("") }
    var tanggalLahir by remember { mutableStateOf("") }
    var beratBadan by remember { mutableStateOf("") }
    var tinggiBadan by remember { mutableStateOf("") }
    var alamat by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tambah Balita") },
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
                value = namaBalita,
                onValueChange = { namaBalita = it },
                label = { Text("Nama Balita") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = namaIbu,
                onValueChange = { namaIbu = it },
                label = { Text("Nama Ibu") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = tanggalLahir,
                onValueChange = { tanggalLahir = it },
                label = { Text("Tanggal Lahir (DD/MM/YYYY)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = beratBadan,
                onValueChange = { beratBadan = it },
                label = { Text("Berat Badan (kg)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = tinggiBadan,
                onValueChange = { tinggiBadan = it },
                label = { Text("Tinggi Badan (cm)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = alamat,
                onValueChange = { alamat = it },
                label = { Text("Alamat") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    viewModel.tambahBalita(
                        Balita(
                            namaBalita = namaBalita,
                            namaIbu = namaIbu,
                            tanggalLahir = tanggalLahir,
                            beratBadanAwal = beratBadan.toDoubleOrNull() ?: 0.0,
                            tinggiBadanAwal = tinggiBadan.toDoubleOrNull() ?: 0.0,
                            alamat = alamat
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