package id.heycoding.sholehapp.data.mapper

import id.heycoding.sholehapp.data.local.entity.PrayerScheduleEntity
import id.heycoding.sholehapp.data.remote.response.sholat.JadwalSholatResponse
import id.heycoding.sholehapp.domain.model.sholat.PrayerSchedule

object JadwalSholatMapper {
    fun JadwalSholatResponse?.mappingJadwalSholatResponseToJadwalSholat(): List<PrayerSchedule> {
        val newList: MutableList<PrayerSchedule> = mutableListOf()

        this?.let {
            newList.add(
                PrayerSchedule(
                    kota = it.query.kota,
                    tanggal = it.query.tanggal,
                    imsak = it.jadwal.data.imsak,
                    subuh = it.jadwal.data.subuh,
                    terbit = it.jadwal.data.terbit,
                    dhuha = it.jadwal.data.dhuha,
                    dzuhur = it.jadwal.data.dzuhur,
                    ashar = it.jadwal.data.ashar,
                    maghrib = it.jadwal.data.maghrib,
                    isya = it.jadwal.data.isya
                )
            )
        }

        return newList
    }

    fun List<PrayerSchedule>?.mappingJadwalSholatToJadwalSholatEntity(): List<PrayerScheduleEntity> {
        val newList: MutableList<PrayerScheduleEntity> = mutableListOf()

        this?.forEach {
            newList.add(
                PrayerScheduleEntity(
                    id = 0,
                    kota = it.kota,
                    tanggal = it.tanggal,
                    imsak = it.imsak,
                    subuh = it.subuh,
                    terbit = it.terbit,
                    dhuha = it.dhuha,
                    dzuhur = it.dzuhur,
                    ashar = it.ashar,
                    maghrib = it.maghrib,
                    isya = it.isya
                )
            )
        }

        return if (this.isNullOrEmpty()) {
            emptyList()
        } else {
            newList
        }

    }

    fun List<PrayerScheduleEntity>?.mappingJadwalSholatEntityToJadwalSholat(): List<PrayerSchedule> {
        val newList: MutableList<PrayerSchedule> = mutableListOf()

        this?.forEach {
            newList.add(
                PrayerSchedule(
                    kota = it.kota,
                    tanggal = it.tanggal,
                    imsak = it.imsak,
                    subuh = it.subuh,
                    terbit = it.terbit,
                    dhuha = it.dhuha,
                    dzuhur = it.dzuhur,
                    ashar = it.ashar,
                    maghrib = it.maghrib,
                    isya = it.isya
                )
            )
        }

        return if (this.isNullOrEmpty()) {
            emptyList()
        } else {
            newList
        }
    }
}