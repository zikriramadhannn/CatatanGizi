package com.example.monitoringgizi.ui.screens.ibuhamil

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.monitoringgizi.data.model.IbuHamil
import com.example.monitoringgizi.utils.IMTCalculator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TambahIbuHamilDialog(
    onDismiss: () -> Unit,
    onSave: (IbuHamil) -> Unit
) {
    var noKTP by remember { mutableStateOf("") }
    var nama by remember { mutableStateOf("") }
    var tanggalLahir by remember { mutableStateOf("") }
    var usia by remember { mutableStateOf("") }
    var usiaKehamilan by remember { mutableStateOf("") }
    var beratBadanAwal by remember { mutableStateOf("") }
    var tinggiBadanAwal by remember { mutableStateOf("") }
    var kadarHemoglobin by remember { mutableStateOf("") }
    var lingkarLuarAtas by remember { mutableStateOf("") }
    var alamat by remember { mutableStateOf("") }

    val imt = remember(beratBadanAwal, tinggiBadanAwal) {
        val bb = beratBadanAwal.toFloatOrNull() ?: 0f
        val tb = tinggiBadanAwal.toFloatOrNull() ?: 0f
        IMTCalculator.calculateIMT(bb, tb)
    }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Tambah Data Ibu Hamil",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = noKTP,
                    onValueChange = { if (it.length <= 16) noKTP = it },
                    label = { Text("No KTP") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = nama,
                    onValueChange = { nama = it },
                    label = { Text("Nama Lengkap") },
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
                    value = usia,
                    onValueChange = { if (it.length <= 2) usia = it },
                    label = { Text("Usia (Tahun)") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = usiaKehamilan,
                    onValueChange = { if (it.toIntOrNull() in 0..9) usiaKehamilan = it },
                    label = { Text("Usia Kehamilan (Bulan)") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = beratBadanAwal,
                    onValueChange = { beratBadanAwal = it },
                    label = { Text("Berat Badan (kg)") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = tinggiBadanAwal,
                    onValueChange = { tinggiBadanAwal = it },
                    label = { Text("Tinggi Badan (cm)") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )

                if (imt > 0) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "IMT: %.1f (${IMTCalculator.getKategoriIMT(imt)})"
                            .format(imt),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = lingkarLuarAtas,
                    onValueChange = { lingkarLuarAtas = it },
                    label = { Text("Lingkar Lengan Atas (cm)") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = kadarHemoglobin,
                    onValueChange = { kadarHemoglobin = it },
                    label = { Text("Kadar Hemoglobin (g/dl)") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = alamat,
                    onValueChange = { alamat = it },
                    label = { Text("Alamat") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 2
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Batal")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            val ibuHamil = IbuHamil(
                                noKTP = noKTP,
                                nama = nama,
                                tanggalLahir = tanggalLahir,
                                usia = usia.toIntOrNull() ?: 0,
                                usiaKehamilan = usiaKehamilan.toIntOrNull() ?: 0,
                                beratBadanAwal = beratBadanAwal.toFloatOrNull() ?: 0f,
                                tinggiBadanAwal = tinggiBadanAwal.toFloatOrNull() ?: 0f,
                                imtPraHamil = imt,
                                lingkarLuarAtas = lingkarLuarAtas.toFloatOrNull() ?: 0f,
                                kadarHemoglobin = kadarHemoglobin.toFloatOrNull() ?: 0f,
                                alamat = alamat
                            )
                            onSave(ibuHamil)
                        },
                        enabled = validateInput(noKTP, nama, tanggalLahir, usia, usiaKehamilan,
                            beratBadanAwal, tinggiBadanAwal, kadarHemoglobin, lingkarLuarAtas, alamat)
                    ) {
                        Text("Simpan")
                    }
                }
            }
        }
    }
}

private fun validateInput(
    noKTP: String,
    nama: String,
    tanggalLahir: String,
    usia: String,
    usiaKehamilan: String,
    beratBadanAwal: String,
    tinggiBadanAwal: String,
    kadarHemoglobin: String,
    lingkarLuarAtas: String,
    alamat: String
): Boolean {
    // Validasi dasar
    if (noKTP.isBlank() || nama.isBlank() || tanggalLahir.isBlank() ||
        usia.isBlank() || usiaKehamilan.isBlank() || beratBadanAwal.isBlank() ||
        tinggiBadanAwal.isBlank() || kadarHemoglobin.isBlank() ||
        lingkarLuarAtas.isBlank() || alamat.isBlank()) {
        return false
    }

    // Validasi format
    val tanggalLahirRegex = """^\d{2}/\d{2}/\d{4}$""".toRegex()
    if (!tanggalLahirRegex.matches(tanggalLahir)) {
        return false
    }

    return true
}