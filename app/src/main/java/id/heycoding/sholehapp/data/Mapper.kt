package id.heycoding.sholehapp.data

import id.heycoding.sholehapp.data.source.response.AyatResponseItem
import id.heycoding.sholehapp.data.source.response.SurahResponseItem
import id.heycoding.sholehapp.domain.model.Ayat
import id.heycoding.sholehapp.domain.model.Surah

// TODO 10 Buat class mapper untuk menghubungkan POJO response dengan POJO mapper

fun List<SurahResponseItem>?.mappingSurahToUseCaseEntity(): List<Surah> {
    val newList: MutableList<Surah> = mutableListOf()

    this?.forEach {
        newList.add(
            Surah(
                arti = it.arti,
                asma = it.asma,
                audio = it.audio,
                ayat = it.ayat,
                keterangan = it.keterangan,
                nama = it.nama,
                nomor = it.nomor,
                rukuk = it.rukuk,
                type = it.type,
                urut = it.urut
            )
        )
    }

    return if (this.isNullOrEmpty()) {
        emptyList()
    } else {
        newList
    }
}

fun List<AyatResponseItem>?.mappingAyatToUseCaseEntity(): List<Ayat> {
    val newList: MutableList<Ayat> = mutableListOf()

    this?.forEach {
        newList.add(
            Ayat(
                ar = it.ar,
                id = it.id,
                nomor = it.nomor,
                tr = it.tr
            )
        )
    }

    return if (this.isNullOrEmpty()) {
        emptyList()
    } else {
        newList
    }
}