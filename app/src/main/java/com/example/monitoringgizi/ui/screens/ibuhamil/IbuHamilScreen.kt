package com.example.monitoringgizi.ui.screens.ibuhamil

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.monitoringgizi.viewmodel.IbuHamilViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IbuHamilScreen(
    viewModel: IbuHamilViewModel,
    onPencatatanClick: (String) -> Unit,
    onRiwayatClick: (String) -> Unit,
    onPemantauanClick: (String) -> Unit,
    onRiwayatPemantauanClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val errorMessage by viewModel.errorMessage.collectAsState()
    val ibuHamilList by viewModel.ibuHamilList.collectAsState()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = modifier
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                // Tips Section
                TipsKesehatan()
            }

            if (ibuHamilList.isEmpty()) {
                item {
                    Text(
                        text = "Belum ada data ibu hamil",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                }
            } else {
                items(ibuHamilList) { ibuHamil ->
                    IbuHamilItem(
                        ibuHamil = ibuHamil,
                        onPencatatanHarian = { onPencatatanClick(ibuHamil.noKTP) },
                        onPemantauanClick = { onPemantauanClick(ibuHamil.noKTP) },
                        onRiwayatClick = { onRiwayatClick(ibuHamil.noKTP) },
                        onRiwayatPemantauanClick = { onRiwayatPemantauanClick(ibuHamil.noKTP) },
                        onGenerateLaporanHarian = {
                            scope.launch {
                                try {
                                    viewModel.generateLaporanHarianIbuHamil(context, ibuHamil.noKTP)
                                } catch (e: Exception) {
                                    snackbarHostState.showSnackbar(
                                        "Gagal membuat laporan: ${e.message}"
                                    )
                                }
                            }
                        },
                        onGenerateLaporanBulanan = {
                            scope.launch {
                                try {
                                    viewModel.generateLaporanBulananIbuHamil(context, ibuHamil.noKTP)
                                } catch (e: Exception) {
                                    snackbarHostState.showSnackbar(
                                        "Gagal membuat laporan: ${e.message}"
                                    )
                                }
                            }
                        }
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { showDialog = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("+ Tambah Data Ibu Hamil")
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    // Show error message if any
    LaunchedEffect(errorMessage) {
        errorMessage?.let {
            scope.launch {
                snackbarHostState.showSnackbar(it)
                viewModel.clearError()
            }
        }
    }

    if (showDialog) {
        TambahIbuHamilDialog(
            onDismiss = { showDialog = false },
            onSave = { ibuHamil ->
                scope.launch {
                    viewModel.tambahIbuHamil(ibuHamil)
                }
                showDialog = false
            }
        )
    }
}