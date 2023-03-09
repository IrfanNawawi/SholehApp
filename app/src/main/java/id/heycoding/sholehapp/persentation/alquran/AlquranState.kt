package id.heycoding.sholehapp.persentation.alquran

import id.heycoding.sholehapp.domain.model.Surah

// TODO 14 Buat state untuk viewmodel sesuai dengan POJO

data class AlquranState(
    val isLoading: Boolean = false,
    val alquranList: List<Surah> = emptyList(),
    val error: String = ""
)