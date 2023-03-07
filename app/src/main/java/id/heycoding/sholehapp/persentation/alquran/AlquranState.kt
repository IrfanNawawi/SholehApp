package id.heycoding.sholehapp.persentation.alquran

import id.heycoding.sholehapp.domain.model.Surah

data class AlquranState(
    val isLoading: Boolean = false,
    val alquranList: List<Surah> = emptyList(),
    val error: String = ""
)