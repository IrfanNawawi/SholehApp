package id.heycoding.sholehapp.domain.repository

import id.heycoding.sholehapp.data.source.response.AyatResponse
import id.heycoding.sholehapp.data.source.response.SurahResponse

// TODO 4 Buat interface repository untuk penyambung ke response API

interface AlquranRepository {
    suspend fun getAllSurah(): SurahResponse
    suspend fun getAllAyat(number: String): AyatResponse
}