package id.heycoding.sholehapp.domain.repository

import id.heycoding.sholehapp.data.source.response.alquran.AyatResponse
import id.heycoding.sholehapp.data.source.response.alquran.SurahResponse
import id.heycoding.sholehapp.data.source.response.sholat.JadwalSholatResponse
import id.heycoding.sholehapp.data.source.response.sholat.KotaSholatResponse

// TODO 4 Buat interface repository untuk penyambung ke response API

interface SholehRepository {

    // Feature Alquran
    suspend fun getAllSurah(): SurahResponse
    suspend fun getAllAyat(number: String): AyatResponse

    // Feature Sholat
    suspend fun getAllKotaSholat(kotaSholatUrl: String): KotaSholatResponse
    suspend fun getAllJadwalSholat(jadwalSholatUrl: String): JadwalSholatResponse
}