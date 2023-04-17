package id.heycoding.sholehapp.data.remote.response.sholat


import com.google.gson.annotations.SerializedName

data class KotaSholatResponse(
    @SerializedName("kota")
    val kota: List<Kota>,
    @SerializedName("query")
    val query: Query,
    @SerializedName("status")
    val status: String
)