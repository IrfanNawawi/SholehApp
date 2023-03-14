package id.heycoding.sholehapp.persentation.alquran.detailalquran

import id.heycoding.sholehapp.domain.model.alquran.Ayat

data class DetailAlquranState(
    val isLoading: Boolean = false,
    val detailAlquranList: List<Ayat> = emptyList(),
    val error: String = ""
)