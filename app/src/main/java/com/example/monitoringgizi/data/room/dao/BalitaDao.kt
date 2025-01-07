package com.example.monitoringgizi.data.room.dao

import androidx.room.*
import com.example.monitoringgizi.data.room.entity.BalitaEntity
import com.example.monitoringgizi.data.room.entity.PencatatanHarianBalitaEntity
import com.example.monitoringgizi.data.room.entity.PemantauanBulananBalitaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BalitaDao {
    // Balita
    @Query("SELECT * FROM balita")
    fun getAllBalita(): Flow<List<BalitaEntity>>

    @Query("SELECT * FROM balita WHERE namaBalita = :nama")
    suspend fun getBalitaByNama(nama: String): BalitaEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBalita(balita: BalitaEntity)

    @Delete
    suspend fun deleteBalita(balita: BalitaEntity)

    // Pencatatan Harian
    @Query("SELECT * FROM pencatatan_harian_balita WHERE namaBalita = :nama")
    fun getPencatatanByNama(nama: String): Flow<List<PencatatanHarianBalitaEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPencatatan(pencatatan: PencatatanHarianBalitaEntity)

    // Pemantauan Bulanan
    @Query("SELECT * FROM pemantauan_bulanan_balita WHERE namaBalita = :nama")
    fun getPemantauanByNama(nama: String): Flow<List<PemantauanBulananBalitaEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPemantauan(pemantauan: PemantauanBulananBalitaEntity)
}