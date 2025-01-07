package com.example.monitoringgizi.viewmodel

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.monitoringgizi.MonitoringGiziApp
import com.example.monitoringgizi.data.model.Balita
import com.example.monitoringgizi.data.model.PencatatanHarianBalita
import com.example.monitoringgizi.data.model.PemantauanBulananBalita
import com.example.monitoringgizi.data.repository.BalitaRepository
import com.example.monitoringgizi.data.room.toBalita
import com.example.monitoringgizi.data.room.toEntity
import com.example.monitoringgizi.data.room.toPencatatanHarian
import com.example.monitoringgizi.data.room.toPemantauanBulanan
import com.example.monitoringgizi.utils.PDFGenerator
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class BalitaViewModel(private val repository: BalitaRepository) : ViewModel() {
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    val balitaList: StateFlow<List<Balita>> = repository.getAllBalita()
        .map { entities -> entities.map { it.toBalita() } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun clearError() {
        _errorMessage.value = null
    }

    suspend fun getBalitaByNama(nama: String): Balita? =
        repository.getBalitaByNama(nama)?.toBalita()

    fun getPencatatanByNama(nama: String): StateFlow<List<PencatatanHarianBalita>> =
        repository.getPencatatanByNama(nama)
            .map { entities -> entities.map { it.toPencatatanHarian() } }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    fun getPemantauanByNama(nama: String): StateFlow<List<PemantauanBulananBalita>> =
        repository.getPemantauanByNama(nama)
            .map { entities -> entities.map { it.toPemantauanBulanan() } }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    fun tambahBalita(balita: Balita) {
        viewModelScope.launch {
            try {
                repository.insertBalita(balita.toEntity())
            } catch (e: Exception) {
                _errorMessage.value = "Gagal menambahkan data: ${e.message}"
            }
        }
    }

    fun tambahPencatatanHarian(pencatatan: PencatatanHarianBalita) {
        viewModelScope.launch {
            try {
                repository.insertPencatatan(pencatatan.toEntity())
            } catch (e: Exception) {
                _errorMessage.value = "Gagal menambahkan pencatatan harian: ${e.message}"
            }
        }
    }

    fun tambahPemantauanBulanan(pemantauan: PemantauanBulananBalita) {
        viewModelScope.launch {
            try {
                repository.insertPemantauan(pemantauan.toEntity())
            } catch (e: Exception) {
                _errorMessage.value = "Gagal menambahkan pemantauan: ${e.message}"
            }
        }
    }

    fun generateLaporanHarian(context: Context, namaBalita: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                println("Debug VM - Mulai generate laporan untuk: $namaBalita")

                val balitaEntity = repository.getBalitaByNama(namaBalita)
                requireNotNull(balitaEntity) { "Balita tidak ditemukan: $namaBalita" }

                val pencatatanEntityList = repository.getPencatatanByNama(namaBalita).first()
                println("Debug VM - Jumlah data pencatatan: ${pencatatanEntityList.size}")

                // Konversi langsung menggunakan extension function
                val balita = balitaEntity.toBalita()
                val pencatatanList = pencatatanEntityList.map {
                    it.toPencatatanHarian()
                }

                val file = PDFGenerator.generateLaporanHarianBalita(context, balita, pencatatanList)
                println("Debug VM - PDF berhasil dibuat")

                // Membuka PDF
                val uri = FileProvider.getUriForFile(
                    context,
                    "${context.packageName}.provider",
                    file
                )

                val intent = Intent(Intent.ACTION_VIEW).apply {
                    setDataAndType(uri, "application/pdf")
                    flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                }
                context.startActivity(Intent.createChooser(intent, "Buka PDF dengan..."))

            } catch (e: Exception) {
                println("Debug VM - Error: ${e.message}")
                e.printStackTrace()
                _errorMessage.value = "Gagal generate laporan: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun generateLaporanBulanan(context: Context, namaBalita: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                println("Debug VM - Mulai generate laporan bulanan untuk: $namaBalita")

                val balitaEntity = repository.getBalitaByNama(namaBalita)
                requireNotNull(balitaEntity) { "Balita tidak ditemukan: $namaBalita" }

                val pemantauanEntityList = repository.getPemantauanByNama(namaBalita).first()

                // Konversi langsung
                val balita = balitaEntity.toBalita()
                val pemantauanList = pemantauanEntityList.map {
                    it.toPemantauanBulanan()
                }

                println("Debug VM - Jumlah data pemantauan: ${pemantauanList.size}")

                val file = PDFGenerator.generateLaporanBulananBalita(context, balita, pemantauanList)
                println("Debug VM - PDF berhasil dibuat")

                // Membuka PDF
                val uri = FileProvider.getUriForFile(
                    context,
                    "${context.packageName}.provider",
                    file
                )

                val intent = Intent(Intent.ACTION_VIEW).apply {
                    setDataAndType(uri, "application/pdf")
                    flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                }
                context.startActivity(Intent.createChooser(intent, "Buka PDF dengan..."))

            } catch (e: Exception) {
                println("Debug VM - Error: ${e.message}")
                e.printStackTrace()
                _errorMessage.value = "Gagal generate laporan bulanan: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    companion object {
        class Factory(private val app: MonitoringGiziApp) : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(BalitaViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return BalitaViewModel(app.balitaRepository) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}