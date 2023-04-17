package id.heycoding.sholehapp.data.remote.response.alquran


import com.google.gson.annotations.SerializedName

// TODO 3 Buat response POJO dari balikan API

data class SurahResponseItem(
    @SerializedName("arti")
    val arti: String,
    @SerializedName("asma")
    val asma: String,
    @SerializedName("audio")
    val audio: String,
    @SerializedName("ayat")
    val ayat: Int,
    @SerializedName("keterangan")
    val keterangan: String,
    @SerializedName("nama")
    val nama: String,
    @SerializedName("nomor")
    val nomor: String,
    @SerializedName("rukuk")
    val rukuk: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("urut")
    val urut: String
)