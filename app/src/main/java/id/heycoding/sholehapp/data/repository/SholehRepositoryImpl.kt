package id.heycoding.sholehapp.data.repository

import id.heycoding.sholehapp.data.source.response.alquran.AyatResponse
import id.heycoding.sholehapp.data.source.response.alquran.SurahResponse
import id.heycoding.sholehapp.data.source.response.sholat.JadwalSholatResponse
import id.heycoding.sholehapp.data.source.response.sholat.KotaSholatResponse
import id.heycoding.sholehapp.data.source.service.SholehApi
import id.heycoding.sholehapp.domain.repository.SholehRepository
import javax.inject.Inject

// TODO 5 Buat implementasi dari interface repository dengan return ke route services API

class SholehRepositoryImpl @Inject constructor(
    private val sholehApi: SholehApi
) : SholehRepository {
    override suspend fun getAllSurah(): SurahResponse {
        return sholehApi.getListSurah()
    }

    override suspend fun getAllAyat(number: String): AyatResponse {
        return sholehApi.getDetailSurah(nomor = number)
    }

    override suspend fun getAllKotaSholat(kotaSholatUrl: String): KotaSholatResponse {
        return sholehApi.getKotaSholat(sholatKotaUrl = kotaSholatUrl)
    }

    override suspend fun getAllJadwalSholat(jadwalSholatUrl: String): JadwalSholatResponse {
        return sholehApi.getJadwalSholat(sholatJadwalUrl = jadwalSholatUrl)
    }

}