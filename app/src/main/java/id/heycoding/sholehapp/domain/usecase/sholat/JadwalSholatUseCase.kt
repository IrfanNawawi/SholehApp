package id.heycoding.sholehapp.domain.usecase.sholat

import id.heycoding.sholehapp.domain.model.sholat.PrayerSchedule
import id.heycoding.sholehapp.domain.repository.SholehRepository
import id.heycoding.sholehapp.utils.ResultState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class JadwalSholatUseCase @Inject constructor(private val repository: SholehRepository) {
    operator fun invoke(
        jadwalSholatUrl: String,
        idKota: String,
        tanggal: String
    ): Flow<ResultState<List<PrayerSchedule>>> = flow {
        try {
            emit(ResultState.Loading())
            val jadwalSholat =
                repository.getAllJadwalSholat(jadwalSholatUrl, idKota, tanggal)
            emit(ResultState.Success(jadwalSholat))
        } catch (e: HttpException) {
            emit(
                ResultState.Error(e.localizedMessage ?: " An Unexpected Error Occurred")
            )
        } catch (e: IOException) {
            emit(ResultState.Error("Error Occurred"))
        }
    }
}