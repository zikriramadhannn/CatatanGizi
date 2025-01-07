package com.example.monitoringgizi.ui.screens.ibuhamil

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.monitoringgizi.data.model.IbuHamil
import java.util.Locale

@Composable
fun TipsKesehatan() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Tips Kesehatan Ibu Hamil",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Tips List
            TipsItem(icon = Icons.Default.Restaurant, text = "Jaga pola makan seimbang")
            TipsItem(icon = Icons.Default.MonitorHeart, text = "Rutin kontrol kehamilan")
            TipsItem(icon = Icons.Default.Bed, text = "Istirahat yang cukup")
            TipsItem(icon = Icons.Default.ShowChart, text = "Pantau berat badan")
        }
    }
}

@Composable
fun TipsItem(icon: ImageVector, text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun ActionButtons(
    onPencatatanClick: () -> Unit,
    onPemantauanClick: () -> Unit,
    onRiwayatClick: () -> Unit,
    onRiwayatPemantauanClick: () -> Unit,
    onGenerateLaporanHarian: () -> Unit,
    onGenerateLaporanBulanan: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Primary Actions
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = onPencatatanClick,
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

        // Secondary Actions
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

        // Report Actions
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick = onGenerateLaporanHarian,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text("Laporan Harian")
            }
            OutlinedButton(
                onClick = onGenerateLaporanBulanan,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text("Laporan Bulanan")
            }
        }
    }
}

@Composable
fun IbuHamilItem(
    ibuHamil: IbuHamil,
    onPencatatanHarian: () -> Unit,
    onPemantauanClick: () -> Unit,
    onRiwayatClick: () -> Unit,
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
            modifier = Modifier.padding(16.dp)
        ) {
            // Header Info
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = ibuHamil.nama.split(" ").map { word ->
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
                        text = "No KTP: ${ibuHamil.noKTP}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Usia Kehamilan: ${ibuHamil.usiaKehamilan} bulan",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "IMT: %.1f".format(ibuHamil.imtPraHamil),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Action Buttons
            ActionButtons(
                onPencatatanClick = onPencatatanHarian,
                onPemantauanClick = onPemantauanClick,
                onRiwayatClick = onRiwayatClick,
                onRiwayatPemantauanClick = onRiwayatPemantauanClick,
                onGenerateLaporanHarian = onGenerateLaporanHarian,
                onGenerateLaporanBulanan = onGenerateLaporanBulanan
            )
        }
    }
}