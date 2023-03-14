package id.heycoding.sholehapp.persentation.alquran

import id.heycoding.sholehapp.domain.model.alquran.Surah

interface AlquranCallback {
    fun onDetailSurahAlquran(surah: Surah)
}