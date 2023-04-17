package id.heycoding.sholehapp.data.local

import id.heycoding.sholehapp.data.SholehDataSource
import id.heycoding.sholehapp.data.local.dao.SholehDao
import id.heycoding.sholehapp.data.mapper.AyatMapper.mappingAyatEntityToAyat
import id.heycoding.sholehapp.data.mapper.AyatMapper.mappingAyatToAyatEntity
import id.heycoding.sholehapp.data.mapper.JadwalSholatMapper.mappingJadwalSholatEntityToJadwalSholat
import id.heycoding.sholehapp.data.mapper.JadwalSholatMapper.mappingJadwalSholatToJadwalSholatEntity
import id.heycoding.sholehapp.data.mapper.KotaSholatMapper.mappingKotaSholatEntityToKotaSholat
import id.heycoding.sholehapp.data.mapper.KotaSholatMapper.mappingKotaSholatToKotaSholatEntity
import id.heycoding.sholehapp.data.mapper.SurahMapper.mappingSurahEntityToSurah
import id.heycoding.sholehapp.data.mapper.SurahMapper.mappingSurahToSurahEntity
import id.heycoding.sholehapp.data.mapper.ZakatMapper.mappingZakatEntityToZakat
import id.heycoding.sholehapp.data.mapper.ZakatMapper.mappingZakatToZakatEntity
import id.heycoding.sholehapp.domain.model.alquran.Ayat
import id.heycoding.sholehapp.domain.model.alquran.Surah
import id.heycoding.sholehapp.domain.model.sholat.CityPrayer
import id.heycoding.sholehapp.domain.model.sholat.PrayerSchedule
import id.heycoding.sholehapp.domain.model.zakat.Zakat
import javax.inject.Inject

class SholehLocalDataSource @Inject constructor(private val sholehDao: SholehDao) :
    SholehDataSource {
    override suspend fun getAllSurah(): List<Surah> {
        return sholehDao.getAllSurah().mappingSurahEntityToSurah()
    }

    override suspend fun getAllAyat(number: String): List<Ayat> {
        return sholehDao.getAllAyat(number).mappingAyatEntityToAyat()
    }

    override suspend fun insertSurahDao(data: List<Surah>) {
        val dataEntities = data.mappingSurahToSurahEntity()
        return sholehDao.insertSurah(dataEntities)
    }

    override suspend fun insertAyatDao(number: String, data: List<Ayat>) {
        val dataEntities = data.mappingAyatToAyatEntity(number)
        return sholehDao.insertAyat(dataEntities)
    }

    override suspend fun getAllCityPrayer(kotaSholatUrl: String): List<CityPrayer> {
        return sholehDao.getAllCityPrayer().mappingKotaSholatEntityToKotaSholat()
    }

    override suspend fun getAllPrayerSchedule(
        jadwalSholatUrl: String,
        idKota: String,
        tanggal: String
    ): List<PrayerSchedule> {
        return sholehDao.getAllPrayerSchedule(idKota, tanggal)
            .mappingJadwalSholatEntityToJadwalSholat()
    }

    override suspend fun insertCityPrayerDao(data: List<CityPrayer>) {
        val dataEntities = data.mappingKotaSholatToKotaSholatEntity()
        return sholehDao.insertCityPrayer(dataEntities)
    }

    override suspend fun insertPrayerScheduleDao(data: List<PrayerSchedule>) {
        val dataEntities = data.mappingJadwalSholatToJadwalSholatEntity()
        return sholehDao.insertPrayerSchedule(dataEntities)
    }

    override suspend fun getAllPaymentZakat(): List<Zakat> {
        return sholehDao.getListPaymentZakat().mappingZakatEntityToZakat()
    }

    override suspend fun insertPaymentZakat(dataZakat: List<Zakat>) {
        val dataEntities = dataZakat.mappingZakatToZakatEntity()
        return sholehDao.insertPaymentZakat(dataEntities)
    }

    override suspend fun getSumZakatPerson(): Int {
        return sholehDao.getSumZakatPerson()
    }

    override suspend fun getSumZakatRice(): Double {
        return sholehDao.getSumZakatRice()
    }

    override suspend fun getSumZakatMoney(): Int {
        return sholehDao.getSumZakatMoney()
    }

    override suspend fun getSumZakatInfak(): Int {
        return sholehDao.getSumInfak()
    }
}