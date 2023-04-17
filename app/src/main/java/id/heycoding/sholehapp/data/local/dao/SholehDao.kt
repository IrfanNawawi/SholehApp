package id.heycoding.sholehapp.data.local.dao

import androidx.room.*
import id.heycoding.sholehapp.data.local.entity.*

@Dao
interface SholehDao {

    @Query("SELECT * FROM tbl_surah_sholeh")
    fun getAllSurah(): List<SurahEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSurah(surahEntity: List<SurahEntity>)

    @Query("SELECT * FROM tbl_ayat_sholeh WHERE nomorSurah=:nomor")
    fun getAllAyat(nomor: String): List<AyatEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAyat(ayatEntity: List<AyatEntity>)

    @Query("SELECT * FROM tbl_kotasholat_sholeh")
    fun getAllCityPrayer(): List<CityPrayerEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCityPrayer(kotaSholat: List<CityPrayerEntity>)

    @Query("SELECT * FROM tbl_jadwalsholat_sholeh WHERE kota=:idkota AND tanggal=:tanggal")
    fun getAllPrayerSchedule(idkota: String, tanggal: String): List<PrayerScheduleEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPrayerSchedule(prayerSchedule: List<PrayerScheduleEntity>)

    @Query("SELECT * FROM tbl_zakat_sholeh order by createdDate DESC")
    fun getListPaymentZakat(): List<ZakatEntity>

    @Query("SELECT SUM(jumlahJiwa) AS TotalJiwa FROM tbl_zakat_sholeh")
    fun getSumZakatPerson(): Int

    @Query("SELECT SUM(uangZakat) AS TotalUang FROM tbl_zakat_sholeh")
    fun getSumZakatMoney(): Int

    @Query("SELECT SUM(berasZakat) AS TotalBeras FROM tbl_zakat_sholeh")
    fun getSumZakatRice(): Double

    @Query("SELECT SUM(infak) AS TotalInfak FROM tbl_zakat_sholeh")
    fun getSumInfak(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPaymentZakat(zakatEntity: List<ZakatEntity>)

    @Update
    fun updatePaymentZakat(zakatEntity: ZakatEntity)

    @Delete
    fun deletePaymentZakat(zakatEntity: ZakatEntity)

    @Query("SELECT * FROM tbl_zakat_sholeh WHERE id=:id")
    fun getPaymentZakat(id: Int): List<ZakatEntity>
}