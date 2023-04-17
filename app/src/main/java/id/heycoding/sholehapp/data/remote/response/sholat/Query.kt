package id.heycoding.sholehapp.data.remote.response.sholat


import com.google.gson.annotations.SerializedName

data class Query(
    @SerializedName("format")
    val format: String
)