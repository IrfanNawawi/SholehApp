package id.heycoding.sholehapp.data.source.response.sholat


import com.google.gson.annotations.SerializedName

data class Query(
    @SerializedName("format")
    val format: String
)