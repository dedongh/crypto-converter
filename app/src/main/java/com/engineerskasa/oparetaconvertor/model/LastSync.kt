package com.engineerskasa.oparetaconvertor.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "last_sync")
data class LastSync(
    @PrimaryKey(autoGenerate = false)
    var id: Long = 1,
    @ColumnInfo(name = "last_updated")
    var lastUpdated: String? = null,
)
