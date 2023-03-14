package id.heycoding.sholehapp.domain.usecase.sholat

import id.heycoding.sholehapp.data.mappingKotaToUseCaseEntity
import id.heycoding.sholehapp.domain.model.sholat.KotaSholat
import id.heycoding.sholehapp.domain.repository.SholehRepository
import id.heycoding.sholehapp.utils.ResultState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class KotaUseCase @Inject constructor(private val repository: SholehRepository) {
    operator fun invoke(kotaSholatUrl: String): Flow<ResultState<List<KotaSholat>>> = flow {
        try {
            emit(ResultState.Loading())
            val kota = repository.getAllKotaSholat(kotaSholatUrl).kota.mappingKotaToUseCaseEntity()
            emit(ResultState.Success(kota))
        } catch (e: HttpException) {
            emit(
                ResultState.Error(e.localizedMessage ?: " An Unexpected Error Occurred")
            )
        } catch (e: IOException) {
            emit(ResultState.Error("Error Occurred"))
        }
    }
}