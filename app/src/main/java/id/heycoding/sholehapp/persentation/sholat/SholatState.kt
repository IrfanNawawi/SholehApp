package id.heycoding.sholehapp.persentation.sholat

import id.heycoding.sholehapp.domain.model.sholat.JadwalSholat
import id.heycoding.sholehapp.domain.model.sholat.KotaSholat

data class SholatState(
    val isLoading: Boolean = false,
    val kotaSholatList: List<KotaSholat> = emptyList(),
    val jadwalSholatList: List<JadwalSholat> = emptyList(),
    val error: String = ""
)
