package id.heycoding.sholehapp.data

import id.heycoding.sholehapp.domain.model.alquran.Ayat
import id.heycoding.sholehapp.domain.model.alquran.Surah
import id.heycoding.sholehapp.domain.model.sholat.CityPrayer
import id.heycoding.sholehapp.domain.model.sholat.PrayerSchedule
import id.heycoding.sholehapp.domain.model.zakat.Zakat

// TODO 4 Buat interface repository untuk penyambung ke response API

interface SholehDataSource {

    // Feature Alquran
    suspend fun getAllSurah(): List<Surah>
    suspend fun getAllAyat(number: String): List<Ayat>
    suspend fun insertSurahDao(data: List<Surah>)
    suspend fun insertAyatDao(number: String, data: List<Ayat>)

    // Feature Sholat
    suspend fun getAllCityPrayer(kotaSholatUrl: String): List<CityPrayer>
    suspend fun getAllPrayerSchedule(
        jadwalSholatUrl: String,
        idKota: String,
        tanggal: String
    ): List<PrayerSchedule>

    suspend fun insertCityPrayerDao(data: List<CityPrayer>)
    suspend fun insertPrayerScheduleDao(data: List<PrayerSchedule>)

    // Feature Zakat
    suspend fun getAllPaymentZakat(): List<Zakat>
    suspend fun insertPaymentZakat(dataZakat: List<Zakat>)
    suspend fun getSumZakatPerson(): Int
    suspend fun getSumZakatRice(): Double
    suspend fun getSumZakatMoney(): Int
    suspend fun getSumZakatInfak(): Int
}