package id.heycoding.sholehapp.domain.model

import com.google.gson.annotations.SerializedName

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
)
