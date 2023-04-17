package id.heycoding.sholehapp.data.remote

import id.heycoding.sholehapp.data.SholehDataSource
import id.heycoding.sholehapp.data.mapper.AyatMapper.mappingAyatResponseToAyat
import id.heycoding.sholehapp.data.mapper.JadwalSholatMapper.mappingJadwalSholatResponseToJadwalSholat
import id.heycoding.sholehapp.data.mapper.KotaSholatMapper.mappingKotaSholatResponseToKotaSholat
import id.heycoding.sholehapp.data.mapper.SurahMapper.mappingSurahResponseToSurah
import id.heycoding.sholehapp.data.remote.service.SholehApi
import id.heycoding.sholehapp.domain.model.alquran.Ayat
import id.heycoding.sholehapp.domain.model.alquran.Surah
import id.heycoding.sholehapp.domain.model.sholat.CityPrayer
import id.heycoding.sholehapp.domain.model.sholat.PrayerSchedule
import id.heycoding.sholehapp.domain.model.zakat.Zakat
import javax.inject.Inject

// TODO 5 Buat implementasi dari interface repository dengan return ke route services API

class SholehRemoteDataSource @Inject constructor(
    private val sholehApi: SholehApi
) : SholehDataSource {
    override suspend fun getAllSurah(): List<Surah> {
        return sholehApi.getListSurah().mappingSurahResponseToSurah()
    }

    override suspend fun insertSurahDao(data: List<Surah>) {

    }

    override suspend fun getAllAyat(number: String): List<Ayat> {
        return sholehApi.getDetailSurah(nomor = number).mappingAyatResponseToAyat()
    }

    override suspend fun insertAyatDao(number: String, data: List<Ayat>) {

    }

    override suspend fun getAllCityPrayer(kotaSholatUrl: String): List<CityPrayer> {
        return sholehApi.getKotaSholat(sholatKotaUrl = kotaSholatUrl)
            .mappingKotaSholatResponseToKotaSholat()
    }

    override suspend fun getAllPrayerSchedule(
        jadwalSholatUrl: String,
        idKota: String,
        tanggal: String
    ): List<PrayerSchedule> {
        return sholehApi.getJadwalSholat(sholatJadwalUrl = jadwalSholatUrl)
            .mappingJadwalSholatResponseToJadwalSholat()
    }

    override suspend fun insertCityPrayerDao(data: List<CityPrayer>) {
        TODO("Not yet implemented")
    }

    override suspend fun insertPrayerScheduleDao(data: List<PrayerSchedule>) {
        TODO("Not yet implemented")
    }

    override suspend fun getAllPaymentZakat(): List<Zakat> {
        TODO("Not yet implemented")
    }

    override suspend fun insertPaymentZakat(dataZakat: List<Zakat>) {
        TODO("Not yet implemented")
    }

    override suspend fun getSumZakatPerson(): Int {
        TODO("Not yet implemented")
    }

    override suspend fun getSumZakatRice(): Double {
        TODO("Not yet implemented")
    }

    override suspend fun getSumZakatMoney(): Int {
        TODO("Not yet implemented")
    }

    override suspend fun getSumZakatInfak(): Int {
        TODO("Not yet implemented")
    }

}