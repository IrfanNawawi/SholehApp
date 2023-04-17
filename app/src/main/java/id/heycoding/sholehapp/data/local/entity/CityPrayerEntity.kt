package id.heycoding.sholehapp.data.local.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_kotasholat_sholeh")
data class CityPrayerEntity(
    @PrimaryKey
    @NonNull
    val id: String,
    val nama: String
)
