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
import androidx.compose.material.icons.filled.ArrowBack
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
import com.example.monitoringgizi.data.model.PencatatanHarianIbuHamil
import com.example.monitoringgizi.utils.PDFGenerator
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PencatatanHarianScreen(
    ibuHamil: IbuHamil,
    pencatatanList: List<PencatatanHarianIbuHamil>,
    onSavePencatatan: (PencatatanHarianIbuHamil) -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var pemberianKe by remember { mutableStateOf("") }
    var isHabis by remember { mutableStateOf(true) }
    var isSehat by remember { mutableStateOf(true) }
    var keteranganSakit by remember { mutableStateOf("") }

    val context = LocalContext.current

    // Permission launcher
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission Granted
            generateAndOpenPDF(context, ibuHamil, pencatatanList)
        } else {
            // Permission Denied
            Toast.makeText(
                context,
                "Izin penyimpanan diperlukan untuk membuat PDF",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pencatatan Harian") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "Kembali")
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
                                    generateAndOpenPDF(context, ibuHamil, pencatatanList)
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
        ) {
            Text("Pencatatan untuk: ${ibuHamil.nama}")
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = pemberianKe,
                onValueChange = { pemberianKe = it },
                label = { Text("Pemberian Ke-") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Status Makanan")
                Switch(
                    checked = isHabis,
                    onCheckedChange = { isHabis = it }
                )
            }
            Text(
                text = if (isHabis) "Habis" else "Tidak Habis",
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Status Kesehatan")
                Switch(
                    checked = isSehat,
                    onCheckedChange = { isSehat = it }
                )
            }
            Text(
                text = if (isSehat) "Sehat" else "Sakit",
                style = MaterialTheme.typography.bodySmall
            )

            if (!isSehat) {
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = keteranganSakit,
                    onValueChange = { keteranganSakit = it },
                    label = { Text("Keterangan Sakit") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    val currentDateTime = LocalDateTime.now()
                    val pencatatan = PencatatanHarianIbuHamil(
                        noKTP = ibuHamil.noKTP,
                        tanggal = currentDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE),
                        waktu = currentDateTime.format(DateTimeFormatter.ISO_LOCAL_TIME),
                        pemberianKe = pemberianKe.toIntOrNull() ?: 0,
                        statusMakanan = isHabis,
                        statusKesehatan = isSehat,
                        keteranganSakit = if (!isSehat) keteranganSakit else null
                    )
                    onSavePencatatan(pencatatan)
                },
                enabled = pemberianKe.isNotBlank() && (isSehat || keteranganSakit.isNotBlank()),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Simpan Pencatatan")
            }
        }
    }
}

private fun generateAndOpenPDF(
    context: android.content.Context,
    ibuHamil: IbuHamil,
    pencatatanList: List<PencatatanHarianIbuHamil>
) {
    try {
        val file = PDFGenerator.generateLaporanHarianIbuHamil(
            context = context,
            ibuHamil = ibuHamil,
            pencatatanList = pencatatanList
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