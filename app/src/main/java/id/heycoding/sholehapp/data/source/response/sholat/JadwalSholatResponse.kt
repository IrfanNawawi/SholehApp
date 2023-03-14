package id.heycoding.sholehapp.data.source.response.sholat


import com.google.gson.annotations.SerializedName

data class JadwalSholatResponse(
    @SerializedName("jadwal")
    val jadwal: Jadwal,
    @SerializedName("query")
    val query: QueryX,
    @SerializedName("status")
    val status: String
)