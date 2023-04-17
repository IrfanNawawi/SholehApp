package id.heycoding.sholehapp.data.local.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_jadwalsholat_sholeh")
data class PrayerScheduleEntity(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    val id: Int,
    val kota: String,
    val ashar: String,
    val dhuha: String,
    val dzuhur: String,
    val imsak: String,
    val isya: String,
    val maghrib: String,
    val subuh: String,
    val tanggal: String,
    val terbit: String
)
