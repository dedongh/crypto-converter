package com.engineerskasa.oparetaconvertor.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "currency")
data class OfflineCurrency(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String? = null,
    var symbol: String? = null,
    var price: Double? = null,
    var slug: String? = null,
    @ColumnInfo(name = "last_updated")
    var lastUpdated: String? = null,
    var currency: String? = null,
)
