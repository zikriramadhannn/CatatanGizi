package com.example.monitoringgizi.viewmodel

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.monitoringgizi.MonitoringGiziApp
import com.example.monitoringgizi.data.model.IbuHamil
import com.example.monitoringgizi.data.model.PencatatanHarianIbuHamil
import com.example.monitoringgizi.data.model.PemantauanBulananIbuHamil
import com.example.monitoringgizi.data.repository.IbuHamilRepository
import com.example.monitoringgizi.data.room.entity.IbuHamilEntity
import com.example.monitoringgizi.data.room.toEntity
import com.example.monitoringgizi.data.room.toPemantauanBulanan
import com.example.monitoringgizi.data.room.toPencatatanHarian
import com.example.monitoringgizi.utils.PDFGenerator
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class IbuHamilViewModel(private val repository: IbuHamilRepository) : ViewModel() {

    // State
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // Get all data
    val ibuHamilList: StateFlow<List<IbuHamil>> = repository.getAllIbuHamil()
        .map { entities ->
            entities.map { it.toIbuHamil() }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Pencatatan Harian state
    private val _pencatatanList = MutableStateFlow<List<PencatatanHarianIbuHamil>>(emptyList())
    val pencatatanList: StateFlow<List<PencatatanHarianIbuHamil>> = _pencatatanList.asStateFlow()

    // Pemantauan Bulanan state
    private val _pemantauanList = MutableStateFlow<List<PemantauanBulananIbuHamil>>(emptyList())
    val pemantauanList: StateFlow<List<PemantauanBulananIbuHamil>> = _pemantauanList.asStateFlow()

    fun tambahIbuHamil(ibuHamil: IbuHamil) {
        viewModelScope.launch {
            try {
                repository.insertIbuHamil(ibuHamil.toEntity())
            } catch (e: Exception) {
                _errorMessage.value = "Gagal menambahkan data: ${e.message}"
            }
        }
    }

    suspend fun getIbuHamilByNoKTP(noKTP: String): IbuHamil? {
        return repository.getIbuHamilByNoKTP(noKTP)?.toIbuHamil()
    }

    fun tambahPencatatan(pencatatan: PencatatanHarianIbuHamil) {
        viewModelScope.launch {
            try {
                repository.insertPencatatan(pencatatan.toEntity())
                // Refresh pencatatan list
                loadPencatatanList(pencatatan.noKTP)
            } catch (e: Exception) {
                _errorMessage.value = "Gagal menambahkan pencatatan: ${e.message}"
            }
        }
    }

    fun loadPencatatanList(noKTP: String) {
        viewModelScope.launch {
            try {
                repository.getPencatatanByNoKTP(noKTP)
                    .collect { entities ->
                        _pencatatanList.value = entities.map { it.toPencatatanHarian() }
                    }
            } catch (e: Exception) {
                _errorMessage.value = "Gagal memuat data pencatatan: ${e.message}"
            }
        }
    }

    fun getPencatatanByNoKTP(noKTP: String): Flow<List<PencatatanHarianIbuHamil>> {
        return repository.getPencatatanByNoKTP(noKTP)
            .map { entities ->
                entities.map { it.toPencatatanHarian() }
            }
    }

    fun tambahPemantauanBulanan(pemantauan: PemantauanBulananIbuHamil) {
        viewModelScope.launch {
            try {
                repository.insertPemantauan(pemantauan.toEntity())
                // Refresh pemantauan list
                loadPemantauanList(pemantauan.noKTP)
            } catch (e: Exception) {
                _errorMessage.value = "Gagal menambahkan pemantauan: ${e.message}"
            }
        }
    }

    fun loadPemantauanList(noKTP: String) {
        viewModelScope.launch {
            try {
                repository.getPemantauanByNoKTP(noKTP)
                    .collect { entities ->
                        _pemantauanList.value = entities.map { it.toPemantauanBulanan() }
                    }
            } catch (e: Exception) {
                _errorMessage.value = "Gagal memuat data pemantauan: ${e.message}"
            }
        }
    }

    fun getPemantauanByNoKTP(noKTP: String): Flow<List<PemantauanBulananIbuHamil>> {
        return repository.getPemantauanByNoKTP(noKTP)
            .map { entities ->
                entities.map { it.toPemantauanBulanan() }
            }
    }

    // Extension functions untuk konversi
    private fun IbuHamil.toEntity() = IbuHamilEntity(
        noKTP = noKTP,
        nama = nama,
        tanggalLahir = tanggalLahir,
        usia = usia,
        usiaKehamilan = usiaKehamilan,
        beratBadanAwal = beratBadanAwal,
        tinggiBadanAwal = tinggiBadanAwal,
        imtPraHamil = imtPraHamil,
        lingkarLuarAtas = lingkarLuarAtas,
        kadarHemoglobin = kadarHemoglobin,
        alamat = alamat
    )

    private fun IbuHamilEntity.toIbuHamil() = IbuHamil(
        noKTP = noKTP,
        nama = nama,
        tanggalLahir = tanggalLahir,
        usia = usia,
        usiaKehamilan = usiaKehamilan,
        beratBadanAwal = beratBadanAwal,
        tinggiBadanAwal = tinggiBadanAwal,
        imtPraHamil = imtPraHamil,
        lingkarLuarAtas = lingkarLuarAtas,
        kadarHemoglobin = kadarHemoglobin,
        alamat = alamat
    )

    fun generateLaporanHarianIbuHamil(context: Context, noKTP: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val ibuHamil = getIbuHamilByNoKTP(noKTP) ?: throw Exception("Data ibu hamil tidak ditemukan")
                val pencatatanList = getPencatatanByNoKTP(noKTP).first()

                val file = PDFGenerator.generateLaporanHarianIbuHamil(context, ibuHamil, pencatatanList)
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
                _errorMessage.value = "Gagal membuat laporan harian: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun generateLaporanBulananIbuHamil(context: Context, noKTP: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val ibuHamil = getIbuHamilByNoKTP(noKTP) ?: throw Exception("Data ibu hamil tidak ditemukan")
                val pemantauanList = getPemantauanByNoKTP(noKTP).first()

                val file = PDFGenerator.generateLaporanBulananIbuHamil(context, ibuHamil, pemantauanList)
                val uri = FileProvider.getUriForFile(
                    context,
                    "${context.packageName}.provider",
                    file
                )

                val intent = Intent(Intent.ACTION_VIEW).apply {
                    setDataAndType(uri, "application/pdf")
                    flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                }

                startActivity(context, intent, null)

            } catch (e: Exception) {
                _errorMessage.value = "Gagal membuat laporan bulanan: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    companion object {
        class Factory(private val app: MonitoringGiziApp) : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(IbuHamilViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return IbuHamilViewModel(app.ibuHamilRepository) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }
}