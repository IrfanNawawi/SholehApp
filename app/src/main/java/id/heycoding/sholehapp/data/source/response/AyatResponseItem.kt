package id.heycoding.sholehapp.data.source.response


import com.google.gson.annotations.SerializedName

data class AyatResponseItem(
    @SerializedName("ar")
    val ar: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("nomor")
    val nomor: String,
    @SerializedName("tr")
    val tr: String
)