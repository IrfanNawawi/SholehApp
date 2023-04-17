package id.heycoding.sholehapp.data.local.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_surah_sholeh")
data class SurahEntity(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    val id: Int,
    val arti: String,
    val asma: String,
    val audio: String,
    val ayat: Int,
    val keterangan: String,
    val nama: String,
    val nomor: String,
    val rukuk: String,
    val type: String,
    val urut: String
)
