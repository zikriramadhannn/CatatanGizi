package com.example.monitoringgizi.ui.screens.balita

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.monitoringgizi.data.model.Balita

@Composable
fun TambahBalitaDialog(
    onDismiss: () -> Unit,
    onSave: (Balita) -> Unit
) {
    var namaBalita by remember { mutableStateOf("") }
    var namaIbu by remember { mutableStateOf("") }
    var tanggalLahir by remember { mutableStateOf("") }
    var beratBadan by remember { mutableStateOf("") }
    var tinggiBadan by remember { mutableStateOf("") }
    var alamat by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Tambah Data Balita") },
        text = {
            Column {
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
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = tinggiBadan,
                    onValueChange = { tinggiBadan = it },
                    label = { Text("Tinggi Badan (cm)") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = alamat,
                    onValueChange = { alamat = it },
                    label = { Text("Alamat") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSave(
                        Balita(
                            namaBalita = namaBalita,
                            namaIbu = namaIbu,
                            tanggalLahir = tanggalLahir,
                            beratBadanAwal = beratBadan.toDoubleOrNull() ?: 0.0,
                            tinggiBadanAwal = tinggiBadan.toDoubleOrNull() ?: 0.0,
                            alamat = alamat
                        )
                    )
                }
            ) {
                Text("Simpan")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Batal")
            }
        }
    )
}