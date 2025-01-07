package com.example.monitoringgizi.ui.screens.balita

import android.Manifest
import android.content.pm.PackageManager
import android.icu.number.Scale
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.monitoringgizi.viewmodel.BalitaViewModel
import kotlinx.coroutines.launch
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChildCare
import androidx.compose.material.icons.filled.MonitorWeight
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material.icons.filled.BabyChangingStation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BalitaScreen(
    viewModel: BalitaViewModel,
    onPencatatanClick: (String) -> Unit,
    onRiwayatClick: (String) -> Unit,
    onPemantauanClick: (String) -> Unit,
    onRiwayatPemantauanClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val errorMessage by viewModel.errorMessage.collectAsState()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val balitaList by viewModel.balitaList.collectAsState()

    LaunchedEffect(errorMessage) {
        errorMessage?.let {
            scope.launch {
                snackbarHostState.showSnackbar(it)
                viewModel.clearError()
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = modifier
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                // Health Tips Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Tips Kesehatan Balita",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        // Tips dengan icons
                        Row(
                            modifier = Modifier.padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Filled.MonitorWeight,
                                contentDescription = "Gizi",
                                modifier = Modifier.size(24.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Perhatikan asupan gizi seimbang",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }

                        Row(
                            modifier = Modifier.padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Filled.ChildCare,
                                contentDescription = "Kebersihan",
                                modifier = Modifier.size(24.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Jaga kebersihan dan kesehatan",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }

                        Row(
                            modifier = Modifier.padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Medication,
                                contentDescription = "Pemantauan",
                                modifier = Modifier.size(24.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Lakukan pemantauan rutin",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }

                        Row(
                            modifier = Modifier.padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Filled.BabyChangingStation,
                                contentDescription = "ASI",
                                modifier = Modifier.size(24.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Berikan ASI eksklusif (0-6 bulan)",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }

                // Content area
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    if (balitaList.isEmpty()) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Belum ada data balita",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                text = "Tekan tombol + di bawah untuk menambah data",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    } else {
                        BalitaList(
                            balitaList = balitaList,
                            onItemClick = { /* Akan diimplementasikan nanti */ },
                            onPencatatanClick = onPencatatanClick,
                            onRiwayatClick = onRiwayatClick,
                            onPemantauanClick = onPemantauanClick,
                            onRiwayatPemantauanClick = onRiwayatPemantauanClick,
                            onGenerateLaporanHarian = { nama ->
                                scope.launch {
                                    try {
                                        viewModel.generateLaporanHarian(context, nama)
                                        snackbarHostState.showSnackbar("Laporan harian berhasil dibuat")
                                    } catch (e: Exception) {
                                        snackbarHostState.showSnackbar("Gagal membuat laporan: ${e.message}")
                                    }
                                }
                            },
                            onGenerateLaporanBulanan = { nama ->
                                scope.launch {
                                    try {
                                        viewModel.generateLaporanBulanan(context, nama)
                                        snackbarHostState.showSnackbar("Laporan bulanan berhasil dibuat")
                                    } catch (e: Exception) {
                                        snackbarHostState.showSnackbar("Gagal membuat laporan: ${e.message}")
                                    }
                                }
                            }
                        )
                    }
                }

                // Tombol Tambah Data
                Button(
                    onClick = { showDialog = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text("+ Tambah Data Balita")
                }
            }
        }
    }

    if (showDialog) {
        TambahBalitaDialog(
            onDismiss = { showDialog = false },
            onSave = { balita ->
                viewModel.tambahBalita(balita)
                showDialog = false
            }
        )
    }
}