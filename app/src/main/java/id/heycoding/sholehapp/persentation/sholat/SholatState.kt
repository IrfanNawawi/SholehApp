package id.heycoding.sholehapp.persentation.sholat

import id.heycoding.sholehapp.domain.model.sholat.CityPrayer
import id.heycoding.sholehapp.domain.model.sholat.PrayerSchedule

data class SholatState(
    val isLoading: Boolean = false,
    val cityPrayerList: List<CityPrayer> = emptyList(),
    val prayerScheduleList: List<PrayerSchedule> = emptyList(),
    val error: String = ""
)
