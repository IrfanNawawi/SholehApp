package id.heycoding.sholehapp.data.source.service

import id.heycoding.sholehapp.data.source.response.alquran.AyatResponse
import id.heycoding.sholehapp.data.source.response.sholat.KotaSholatResponse
import id.heycoding.sholehapp.data.source.response.alquran.SurahResponse
import id.heycoding.sholehapp.data.source.response.sholat.JadwalSholatResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

// TODO 1 Buat package data, di, domain, persentation, utils
// TODO 2 Buat route Services API

interface SholehApi {

    // Feature Alquran
    @GET("/99c279bb173a6e28359c/data")
    suspend fun getListSurah(): SurahResponse

    @GET("/99c279bb173a6e28359c/surat/{nomor}")
    suspend fun getDetailSurah(
        @Path("nomor") nomor: String
    ): AyatResponse

    // Feature Sholat
    @GET
    suspend fun getKotaSholat(
        @Url sholatKotaUrl: String
    ): KotaSholatResponse

    @GET
    suspend fun getJadwalSholat(
        @Url sholatJadwalUrl: String
    ): JadwalSholatResponse
}