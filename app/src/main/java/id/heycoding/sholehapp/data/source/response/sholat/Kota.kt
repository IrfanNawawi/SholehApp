package id.heycoding.sholehapp.data.source.response.sholat


import com.google.gson.annotations.SerializedName

data class Kota(
    @SerializedName("id")
    val id: String,
    @SerializedName("nama")
    val nama: String
)