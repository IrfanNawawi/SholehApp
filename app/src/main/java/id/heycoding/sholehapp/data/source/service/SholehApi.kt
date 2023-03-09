package id.heycoding.sholehapp.data.source.service

import id.heycoding.sholehapp.data.source.response.AyatResponse
import id.heycoding.sholehapp.data.source.response.SurahResponse
import retrofit2.http.GET
import retrofit2.http.Path

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
}