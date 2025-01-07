package com.example.monitoringgizi.data.repository

import com.example.monitoringgizi.data.model.PemantauanBulananBalita
import com.example.monitoringgizi.data.room.dao.BalitaDao
import com.example.monitoringgizi.data.room.entity.BalitaEntity
import com.example.monitoringgizi.data.room.entity.PencatatanHarianBalitaEntity
import com.example.monitoringgizi.data.room.entity.PemantauanBulananBalitaEntity
import com.example.monitoringgizi.data.room.toPemantauanBulanan
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BalitaRepository(private val balitaDao: BalitaDao) {
    fun getAllBalita(): Flow<List<BalitaEntity>> = balitaDao.getAllBalita()

    suspend fun getBalitaByNama(nama: String): BalitaEntity? =
        balitaDao.getBalitaByNama(nama)

    suspend fun insertBalita(balita: BalitaEntity) =
        balitaDao.insertBalita(balita)

    fun getPencatatanByNama(nama: String): Flow<List<PencatatanHarianBalitaEntity>> =
        balitaDao.getPencatatanByNama(nama)

    suspend fun insertPencatatan(pencatatan: PencatatanHarianBalitaEntity) =
        balitaDao.insertPencatatan(pencatatan)

    fun getPemantauanByNama(nama: String): Flow<List<PemantauanBulananBalitaEntity>> =
        balitaDao.getPemantauanByNama(nama)

    suspend fun insertPemantauan(pemantauan: PemantauanBulananBalitaEntity) =
        balitaDao.insertPemantauan(pemantauan)
}