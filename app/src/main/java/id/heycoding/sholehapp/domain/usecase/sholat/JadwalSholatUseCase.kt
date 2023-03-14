package id.heycoding.sholehapp.domain.usecase.sholat

import id.heycoding.sholehapp.data.mappingJadwalToUseCaseEntity
import id.heycoding.sholehapp.domain.model.sholat.JadwalSholat
import id.heycoding.sholehapp.domain.repository.SholehRepository
import id.heycoding.sholehapp.utils.ResultState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class JadwalSholatUseCase @Inject constructor(private val repository: SholehRepository) {
    operator fun invoke(jadwalSholatUrl: String): Flow<ResultState<List<JadwalSholat>>> = flow {
        try {
            emit(ResultState.Loading())
            val jadwalSholat =
                repository.getAllJadwalSholat(jadwalSholatUrl).jadwal.data.mappingJadwalToUseCaseEntity()
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