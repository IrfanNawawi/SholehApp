package id.heycoding.sholehapp.domain.model.zakat

data class Zakat(
    val id: Int,
    val nama: String,
    val rt: String,
    val typeZakat: String,
    val jumlahJiwa: String,
    val uangZakat: String,
    val berasZakat: String,
    val infak: String,
    val createdDate: String
)
