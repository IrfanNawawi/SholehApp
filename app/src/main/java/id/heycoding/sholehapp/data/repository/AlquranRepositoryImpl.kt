package id.heycoding.sholehapp.data.repository

import id.heycoding.sholehapp.data.source.response.AyatResponse
import id.heycoding.sholehapp.data.source.response.SurahResponse
import id.heycoding.sholehapp.data.source.service.SholehApi
import id.heycoding.sholehapp.domain.repository.AlquranRepository
import javax.inject.Inject

// TODO 5 Buat implementasi dari interface repository dengan return ke route services API

class AlquranRepositoryImpl @Inject constructor(
    private val sholehApi: SholehApi
) : AlquranRepository {
    override suspend fun getAllSurah(): SurahResponse {
        return sholehApi.getListSurah()
    }

    override suspend fun getAllAyat(number: String): AyatResponse {
        return sholehApi.getDetailSurah(nomor = number)
    }

}