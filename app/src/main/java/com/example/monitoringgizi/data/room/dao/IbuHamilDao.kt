package com.example.monitoringgizi.data.room.dao

import androidx.room.*
import com.example.monitoringgizi.data.room.entity.IbuHamilEntity
import com.example.monitoringgizi.data.room.entity.PencatatanHarianIbuHamilEntity
import com.example.monitoringgizi.data.room.entity.PemantauanBulananIbuHamilEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface IbuHamilDao {
    // Ibu Hamil
    @Query("SELECT * FROM ibu_hamil")
    fun getAllIbuHamil(): Flow<List<IbuHamilEntity>>

    @Query("SELECT * FROM ibu_hamil WHERE noKTP = :noKTP")
    suspend fun getIbuHamilByNoKTP(noKTP: String): IbuHamilEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIbuHamil(ibuHamil: IbuHamilEntity)

    @Delete
    suspend fun deleteIbuHamil(ibuHamil: IbuHamilEntity)

    // Pencatatan Harian
    @Query("SELECT * FROM pencatatan_harian_ibu_hamil WHERE noKTP = :noKTP")
    fun getPencatatanByNoKTP(noKTP: String): Flow<List<PencatatanHarianIbuHamilEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPencatatan(pencatatan: PencatatanHarianIbuHamilEntity)

    // Pemantauan Bulanan
    @Query("SELECT * FROM pemantauan_bulanan_ibu_hamil WHERE noKTP = :noKTP")
    fun getPemantauanByNoKTP(noKTP: String): Flow<List<PemantauanBulananIbuHamilEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPemantauan(pemantauan: PemantauanBulananIbuHamilEntity)
}