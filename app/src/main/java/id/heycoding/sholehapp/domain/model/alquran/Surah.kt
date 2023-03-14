package id.heycoding.sholehapp.domain.model.alquran

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// TODO 9 Buat class POJO untuk mapper

@Parcelize
data class Surah(
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
) : Parcelable
