package id.heycoding.sholehapp.data.remote.response.sholat


import com.google.gson.annotations.SerializedName

data class Jadwal(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("status")
    val status: String
)