package com.engineerskasa.oparetaconvertor.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.engineerskasa.oparetaconvertor.model.LastSync

@Dao
interface LastSyncDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveLastSync(lastSync: LastSync)

    @Query("select * from last_sync")
    suspend fun getLastSync(): LastSync

    @Query("select count(*) from last_sync")
    suspend fun getCountOfSync(): Int
}