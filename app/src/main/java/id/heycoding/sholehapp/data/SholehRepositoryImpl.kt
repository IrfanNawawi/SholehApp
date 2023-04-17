package id.heycoding.sholehapp.data

import id.heycoding.sholehapp.domain.model.alquran.Ayat
import id.heycoding.sholehapp.domain.model.alquran.Surah
import id.heycoding.sholehapp.domain.model.sholat.CityPrayer
import id.heycoding.sholehapp.domain.model.sholat.PrayerSchedule
import id.heycoding.sholehapp.domain.model.zakat.Zakat
import id.heycoding.sholehapp.domain.repository.SholehRepository
import id.heycoding.sholehapp.utils.Source
import javax.inject.Inject

class SholehRepositoryImpl @Inject constructor(private val sholehSourceFactory: SholehSourceFactory) :
    SholehRepository {
    override suspend fun getAllSurah(): List<Surah> {
        return sholehSourceFactory.create(Source.LOCAL).getAllSurah()
            .ifEmpty { syncSurahRemote() }
    }

    private suspend fun syncSurahRemote(): List<Surah> {
        return sholehSourceFactory.create(Source.NETWORK).getAllSurah()
            .also { sholehSourceFactory.create(Source.LOCAL).insertSurahDao(it) }
    }

    override suspend fun getAllAyat(number: String): List<Ayat> {
        return sholehSourceFactory.create(Source.LOCAL).getAllAyat(number)
            .ifEmpty { syncAyatRemote(number) }
    }

    private suspend fun syncAyatRemote(number: String): List<Ayat> {
        return sholehSourceFactory.create(Source.NETWORK).getAllAyat(number)
            .also { sholehSourceFactory.create(Source.LOCAL).insertAyatDao(number, it) }
    }

    override suspend fun getAllKotaSholat(kotaSholatUrl: String): List<CityPrayer> {
        return sholehSourceFactory.create(Source.LOCAL).getAllCityPrayer(kotaSholatUrl)
            .ifEmpty { syncKotaSholatRemote(kotaSholatUrl) }
    }

    private suspend fun syncKotaSholatRemote(kotaSholatUrl: String): List<CityPrayer> {
        return sholehSourceFactory.create(Source.NETWORK).getAllCityPrayer(kotaSholatUrl).also {
            sholehSourceFactory.create(Source.LOCAL).insertCityPrayerDao(it)
        }
    }

    override suspend fun getAllJadwalSholat(
        jadwalSholatUrl: String,
        idKota: String,
        tanggal: String
    ): List<PrayerSchedule> {
        return sholehSourceFactory.create(Source.LOCAL)
            .getAllPrayerSchedule(jadwalSholatUrl, idKota, tanggal)
            .ifEmpty { syncJadwalSholatRemote(jadwalSholatUrl, idKota, tanggal) }
    }

    private suspend fun syncJadwalSholatRemote(
        jadwalSholatUrl: String,
        idKota: String,
        tanggal: String
    ): List<PrayerSchedule> {
        return sholehSourceFactory.create(Source.NETWORK)
            .getAllPrayerSchedule(jadwalSholatUrl, idKota, tanggal).also {
                sholehSourceFactory.create(Source.LOCAL).insertPrayerScheduleDao(it)
            }

    }

    override suspend fun getAllPaymentZakat(): List<Zakat> {
        return sholehSourceFactory.create(Source.LOCAL).getAllPaymentZakat()
    }

    override suspend fun insertPaymentZakat(dataZakat: List<Zakat>) {
        return sholehSourceFactory.create(Source.LOCAL).insertPaymentZakat(dataZakat)
    }

    override suspend fun getSumJumlahJiwa(): Int {
        return sholehSourceFactory.create(Source.LOCAL).getSumZakatPerson()
    }

    override suspend fun getSumBerasZakat(): Double {
        return sholehSourceFactory.create(Source.LOCAL).getSumZakatRice()
    }

    override suspend fun getSumUangZakat(): Int {
        return sholehSourceFactory.create(Source.LOCAL).getSumZakatMoney()
    }

    override suspend fun getSumInfakZakat(): Int {
        return sholehSourceFactory.create(Source.LOCAL).getSumZakatInfak()
    }
}