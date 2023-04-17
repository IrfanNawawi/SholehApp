package id.heycoding.sholehapp.domain.model.sholat

data class PrayerSchedule(
    val ashar: String,
    val dhuha: String,
    val dzuhur: String,
    val imsak: String,
    val isya: String,
    val maghrib: String,
    val subuh: String,
    val kota: String,
    val tanggal: String,
    val terbit: String
)