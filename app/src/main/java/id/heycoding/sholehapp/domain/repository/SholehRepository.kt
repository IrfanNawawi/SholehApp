package id.heycoding.sholehapp.domain.repository

import id.heycoding.sholehapp.domain.model.alquran.Ayat
import id.heycoding.sholehapp.domain.model.alquran.Surah
import id.heycoding.sholehapp.domain.model.sholat.CityPrayer
import id.heycoding.sholehapp.domain.model.sholat.PrayerSchedule
import id.heycoding.sholehapp.domain.model.zakat.Zakat

interface SholehRepository {

    // Feature Alquran
    suspend fun getAllSurah(): List<Surah>
    suspend fun getAllAyat(number: String): List<Ayat>

    // Feature Sholat
    suspend fun getAllKotaSholat(kotaSholatUrl: String): List<CityPrayer>
    suspend fun getAllJadwalSholat(
        jadwalSholatUrl: String,
        idKota: String,
        tanggal: String
    ): List<PrayerSchedule>

    // Feature Zakat
    suspend fun getAllPaymentZakat(): List<Zakat>
    suspend fun insertPaymentZakat(dataZakat: List<Zakat>)
    suspend fun getSumJumlahJiwa(): Int
    suspend fun getSumUangZakat(): Int
    suspend fun getSumBerasZakat(): Double
    suspend fun getSumInfakZakat(): Int
}