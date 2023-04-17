package id.heycoding.sholehapp.data.mapper

import id.heycoding.sholehapp.data.local.entity.CityPrayerEntity
import id.heycoding.sholehapp.data.remote.response.sholat.KotaSholatResponse
import id.heycoding.sholehapp.domain.model.sholat.CityPrayer

object KotaSholatMapper {
    fun KotaSholatResponse?.mappingKotaSholatResponseToKotaSholat(): List<CityPrayer> {
        val newList: MutableList<CityPrayer> = mutableListOf()

        this?.kota?.forEach {
            newList.add(
                CityPrayer(
                    id = it.id,
                    nama = it.nama,
                )
            )
        }

        return newList
    }

    fun List<CityPrayer>?.mappingKotaSholatToKotaSholatEntity(): List<CityPrayerEntity> {
        val newList: MutableList<CityPrayerEntity> = mutableListOf()

        this?.forEach {
            newList.add(
                CityPrayerEntity(
                    id = it.id,
                    nama = it.nama,
                )
            )
        }

        return if (this.isNullOrEmpty()) {
            emptyList()
        } else {
            newList
        }

    }

    fun List<CityPrayerEntity>?.mappingKotaSholatEntityToKotaSholat(): List<CityPrayer> {
        val newList: MutableList<CityPrayer> = mutableListOf()

        this?.forEach {
            newList.add(
                CityPrayer(
                    id = it.id,
                    nama = it.nama
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