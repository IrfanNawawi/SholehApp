package id.heycoding.sholehapp.data.source.response.sholat


import com.google.gson.annotations.SerializedName

data class QueryX(
    @SerializedName("format")
    val format: String,
    @SerializedName("kota")
    val kota: String,
    @SerializedName("tanggal")
    val tanggal: String
)