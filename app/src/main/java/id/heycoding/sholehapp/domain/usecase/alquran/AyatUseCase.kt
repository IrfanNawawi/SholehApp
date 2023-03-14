package id.heycoding.sholehapp.domain.usecase.alquran

import id.heycoding.sholehapp.data.mappingAyatToUseCaseEntity
import id.heycoding.sholehapp.domain.model.alquran.Ayat
import id.heycoding.sholehapp.domain.repository.SholehRepository
import id.heycoding.sholehapp.utils.ResultState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AyatUseCase @Inject constructor(
    private val repository: SholehRepository
) {
    operator fun invoke(number: String): Flow<ResultState<List<Ayat>>> = flow {
        try {
            emit(ResultState.Loading())
            val ayat = repository.getAllAyat(number).mappingAyatToUseCaseEntity()
            emit(ResultState.Success(ayat))
        } catch (e: HttpException) {
            emit(
                ResultState.Error(
                    e.localizedMessage ?: " An Unexpected Error Occurred"
                )
            )
        } catch (e: IOException) {
            emit(ResultState.Error("Error Occurred"))
        }
    }
}