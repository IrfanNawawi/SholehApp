package id.heycoding.sholehapp.data.source.response.sholat


import com.google.gson.annotations.SerializedName

data class Jadwal(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("status")
    val status: String
)