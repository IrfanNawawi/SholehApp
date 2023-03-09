package id.heycoding.sholehapp.persentation.alquran

import id.heycoding.sholehapp.domain.model.Surah

interface AlquranCallback {
    fun onDetailSurahAlquran(surah: Surah)
}