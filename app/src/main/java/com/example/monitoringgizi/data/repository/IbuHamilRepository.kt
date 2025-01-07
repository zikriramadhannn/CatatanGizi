package com.example.monitoringgizi.data.repository

import com.example.monitoringgizi.data.room.dao.IbuHamilDao
import com.example.monitoringgizi.data.room.entity.IbuHamilEntity
import com.example.monitoringgizi.data.room.entity.PencatatanHarianIbuHamilEntity
import com.example.monitoringgizi.data.room.entity.PemantauanBulananIbuHamilEntity
import kotlinx.coroutines.flow.Flow

class IbuHamilRepository(private val ibuHamilDao: IbuHamilDao) {

    // Ibu Hamil
    fun getAllIbuHamil(): Flow<List<IbuHamilEntity>> = ibuHamilDao.getAllIbuHamil()

    suspend fun getIbuHamilByNoKTP(noKTP: String): IbuHamilEntity? =
        ibuHamilDao.getIbuHamilByNoKTP(noKTP)

    suspend fun insertIbuHamil(ibuHamil: IbuHamilEntity) =
        ibuHamilDao.insertIbuHamil(ibuHamil)

    suspend fun deleteIbuHamil(ibuHamil: IbuHamilEntity) =
        ibuHamilDao.deleteIbuHamil(ibuHamil)

    // Pencatatan Harian
    fun getPencatatanByNoKTP(noKTP: String): Flow<List<PencatatanHarianIbuHamilEntity>> =
        ibuHamilDao.getPencatatanByNoKTP(noKTP)

    suspend fun insertPencatatan(pencatatan: PencatatanHarianIbuHamilEntity) =
        ibuHamilDao.insertPencatatan(pencatatan)

    // Pemantauan Bulanan
    fun getPemantauanByNoKTP(noKTP: String): Flow<List<PemantauanBulananIbuHamilEntity>> =
        ibuHamilDao.getPemantauanByNoKTP(noKTP)

    suspend fun insertPemantauan(pemantauan: PemantauanBulananIbuHamilEntity) =
        ibuHamilDao.insertPemantauan(pemantauan)
}