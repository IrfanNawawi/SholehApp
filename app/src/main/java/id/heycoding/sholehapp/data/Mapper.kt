package id.heycoding.sholehapp.data

import id.heycoding.sholehapp.data.source.response.alquran.AyatResponseItem
import id.heycoding.sholehapp.data.source.response.alquran.SurahResponseItem
import id.heycoding.sholehapp.data.source.response.sholat.Data
import id.heycoding.sholehapp.data.source.response.sholat.Kota
import id.heycoding.sholehapp.domain.model.alquran.Ayat
import id.heycoding.sholehapp.domain.model.alquran.Surah
import id.heycoding.sholehapp.domain.model.sholat.JadwalSholat
import id.heycoding.sholehapp.domain.model.sholat.KotaSholat

// TODO 10 Buat class mapper untuk menghubungkan POJO response dengan POJO mapper


// Feature Alquran
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

// Feature Sholat
fun List<Kota>?.mappingKotaToUseCaseEntity(): List<KotaSholat> {
    val newList: MutableList<KotaSholat> = mutableListOf()

    this?.forEach {
        newList.add(
            KotaSholat(
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

fun Data.mappingJadwalToUseCaseEntity(): List<JadwalSholat> {
    val newList: MutableList<JadwalSholat> = mutableListOf()

    newList.add(
        JadwalSholat(
            tanggal = tanggal,
            imsak = imsak,
            subuh = subuh,
            terbit = terbit,
            dhuha = dhuha,
            dzuhur = dzuhur,
            ashar = ashar,
            maghrib = maghrib,
            isya = isya
        )
    )

    return newList
}
