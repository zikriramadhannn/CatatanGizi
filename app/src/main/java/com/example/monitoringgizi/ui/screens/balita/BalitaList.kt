package com.example.monitoringgizi.ui.screens.balita

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.monitoringgizi.data.model.Balita
import java.util.Locale

@Composable
fun BalitaList(
    balitaList: List<Balita>,
    onItemClick: (Balita) -> Unit,
    onPencatatanClick: (String) -> Unit,
    onRiwayatClick: (String) -> Unit,
    onPemantauanClick: (String) -> Unit,
    onRiwayatPemantauanClick: (String) -> Unit,
    onGenerateLaporanHarian: (String) -> Unit,
    onGenerateLaporanBulanan: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(balitaList) { balita ->
            BalitaItem(
                balita = balita,
                onClick = { onItemClick(balita) },
                onPencatatanHarian = { onPencatatanClick(balita.namaBalita) },
                onRiwayatClick = { onRiwayatClick(balita.namaBalita) },
                onPemantauanClick = { onPemantauanClick(balita.namaBalita) },
                onRiwayatPemantauanClick = { onRiwayatPemantauanClick(balita.namaBalita) },
                onGenerateLaporanHarian = { onGenerateLaporanHarian(balita.namaBalita) },
                onGenerateLaporanBulanan = { onGenerateLaporanBulanan(balita.namaBalita) }
            )
        }
    }
}

@Composable
fun BalitaItem(
    balita: Balita,
    onClick: () -> Unit,
    onPencatatanHarian: () -> Unit,
    onRiwayatClick: () -> Unit,
    onPemantauanClick: () -> Unit,
    onRiwayatPemantauanClick: () -> Unit,
    onGenerateLaporanHarian: () -> Unit,
    onGenerateLaporanBulanan: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            // Info Section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = balita.namaBalita.split(" ").map { word ->
                            word.replaceFirstChar {
                                if (it.isLowerCase()) it.titlecase(Locale.getDefault())
                                else it.toString()
                            }
                        }.joinToString(" "),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = "Anak dari Ibu ${balita.namaIbu}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Lahir: ${balita.tanggalLahir}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Divider(modifier = Modifier.padding(vertical = 8.dp))

            // Action Buttons
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = onPencatatanHarian,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Catat Harian")
                    }
                    Button(
                        onClick = onPemantauanClick,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Pemantauan")
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = onRiwayatClick,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Riwayat Harian")
                    }
                    OutlinedButton(
                        onClick = onRiwayatPemantauanClick,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Riwayat Bulanan")
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TextButton(
                        onClick = onGenerateLaporanHarian,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Laporan Harian")
                    }
                    TextButton(
                        onClick = onGenerateLaporanBulanan,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Laporan Bulanan")
                    }
                }
            }
        }
    }
}