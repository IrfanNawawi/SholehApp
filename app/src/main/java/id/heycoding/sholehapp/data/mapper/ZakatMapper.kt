package id.heycoding.sholehapp.data.mapper

import id.heycoding.sholehapp.data.local.entity.ZakatEntity
import id.heycoding.sholehapp.domain.model.zakat.Zakat

object ZakatMapper {

    fun List<Zakat>?.mappingZakatToZakatEntity(): List<ZakatEntity> {
        val newList: MutableList<ZakatEntity> = mutableListOf()

        this?.forEach {
            newList.add(
                ZakatEntity(
                    id = it.id,
                    nama = it.nama,
                    rt = it.rt,
                    typeZakat = it.typeZakat,
                    jumlahJiwa = it.jumlahJiwa,
                    uangZakat = it.uangZakat,
                    berasZakat = it.berasZakat,
                    infak = it.infak,
                    createdDate = it.createdDate
                )
            )
        }

        return if (this.isNullOrEmpty()) {
            emptyList()
        } else {
            newList
        }

    }

    fun List<ZakatEntity>?.mappingZakatEntityToZakat(): List<Zakat> {
        val newList: MutableList<Zakat> = mutableListOf()

        this?.forEach {
            newList.add(
                Zakat(
                    id = it.id,
                    nama = it.nama,
                    rt = it.rt,
                    typeZakat = it.typeZakat,
                    jumlahJiwa = it.jumlahJiwa,
                    uangZakat = it.uangZakat,
                    berasZakat = it.berasZakat,
                    infak = it.infak,
                    createdDate = it.createdDate
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