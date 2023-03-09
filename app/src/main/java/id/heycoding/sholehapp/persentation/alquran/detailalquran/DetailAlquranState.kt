package id.heycoding.sholehapp.persentation.alquran.detailalquran

import id.heycoding.sholehapp.domain.model.Ayat
import id.heycoding.sholehapp.domain.model.Surah

data class DetailAlquranState(
    val isLoading: Boolean = false,
    val detailAlquranList: List<Ayat> = emptyList(),
    val error: String = ""
)