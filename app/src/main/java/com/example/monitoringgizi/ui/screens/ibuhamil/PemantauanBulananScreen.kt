package com.example.monitoringgizi.ui.screens.ibuhamil

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.monitoringgizi.data.model.IbuHamil
import com.example.monitoringgizi.data.model.PemantauanBulananIbuHamil
import com.example.monitoringgizi.utils.PDFGenerator
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PemantauanBulananScreen(
    ibuHamil: IbuHamil,
    pemantauanList: List<PemantauanBulananIbuHamil>,
    onSavePemantauan: (PemantauanBulananIbuHamil) -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var pemantauanKe by remember { mutableStateOf("") }
    var usiaKehamilan by remember { mutableStateOf(ibuHamil.usiaKehamilan.toString()) }
    var beratBadanKader by remember { mutableStateOf("") }
    var beratBadanNakes by remember { mutableStateOf("") }
    var lingkarLuarAtas by remember { mutableStateOf("") }
    var keterangan by remember { mutableStateOf("") }

    val context = LocalContext.current

    // Permission launcher
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission Granted
            generateAndOpenPDF(context, ibuHamil, pemantauanList)
        } else {
            // Permission Denied
            Toast.makeText(
                context,
                "Izin penyimpanan diperlukan untuk membuat PDF",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    // Fungsi validasi
    fun isInputValid(): Boolean {
        return pemantauanKe.isNotBlank() &&
                usiaKehamilan.isNotBlank() &&
                beratBadanKader.isNotBlank() &&
                lingkarLuarAtas.isNotBlank() &&
                pemantauanKe.toIntOrNull() != null &&
                usiaKehamilan.toIntOrNull() != null &&
                beratBadanKader.toFloatOrNull() != null &&
                lingkarLuarAtas.toFloatOrNull() != null
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pemantauan Bulanan") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Kembali")
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            when (PackageManager.PERMISSION_GRANTED) {
                                ContextCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                                ) -> {
                                    // Permission already granted
                                    generateAndOpenPDF(context, ibuHamil, pemantauanList)
                                }
                                else -> {
                                    // Request permission
                                    launcher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
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
        Column(
            modifier = modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text("Pemantauan Bulanan untuk: ${ibuHamil.nama}")
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = pemantauanKe,
                onValueChange = { pemantauanKe = it },
                label = { Text("Pemantauan Ke-") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = usiaKehamilan,
                onValueChange = { usiaKehamilan = it },
                label = { Text("Usia Kehamilan (Bulan)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = beratBadanKader,
                onValueChange = { beratBadanKader = it },
                label = { Text("Berat Badan - Pengukuran Kader (kg)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = beratBadanNakes,
                onValueChange = { beratBadanNakes = it },
                label = { Text("Berat Badan - Konfirmasi Nakes (kg)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = lingkarLuarAtas,
                onValueChange = { lingkarLuarAtas = it },
                label = { Text("Lingkar Lengan Atas (cm)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = keterangan,
                onValueChange = { keterangan = it },
                label = { Text("Keterangan") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 2
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val pemantauan = PemantauanBulananIbuHamil(
                        noKTP = ibuHamil.noKTP,
                        tanggal = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE),
                        pemantauanKe = pemantauanKe.toIntOrNull() ?: 0,
                        usiaKehamilan = usiaKehamilan.toIntOrNull() ?: 0,
                        beratBadanKader = beratBadanKader.toFloatOrNull() ?: 0f,
                        beratBadanNakes = beratBadanNakes.toFloatOrNull(),
                        lingkarLuarAtas = lingkarLuarAtas.toFloatOrNull() ?: 0f,
                        keterangan = keterangan
                    )
                    onSavePemantauan(pemantauan)
                    onNavigateBack()
                },
                enabled = isInputValid(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Simpan Pemantauan")
            }
        }
    }
}

private fun generateAndOpenPDF(
    context: android.content.Context,
    ibuHamil: IbuHamil,
    pemantauanList: List<PemantauanBulananIbuHamil>
) {
    try {
        val file = PDFGenerator.generateLaporanBulananIbuHamil(
            context = context,
            ibuHamil = ibuHamil,
            pemantauanList = pemantauanList
        )

        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            file
        )

        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, "application/pdf")
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        }
        context.startActivity(intent)

    } catch (e: Exception) {
        Toast.makeText(
            context,
            "Gagal membuat PDF: ${e.message}",
            Toast.LENGTH_LONG
        ).show()
    }
}