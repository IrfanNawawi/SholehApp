package id.heycoding.sholehapp.data.local.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_zakat_sholeh")
data class ZakatEntity(
    @NonNull
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val nama: String,
    val rt: String,
    val typeZakat: String,
    val jumlahJiwa: String,
    val uangZakat: String,
    val berasZakat: String,
    val infak: String,
    val createdDate: String
)