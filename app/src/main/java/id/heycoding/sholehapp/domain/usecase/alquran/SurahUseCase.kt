package id.heycoding.sholehapp.domain.usecase.alquran

import id.heycoding.sholehapp.domain.model.alquran.Surah
import id.heycoding.sholehapp.domain.repository.SholehRepository
import id.heycoding.sholehapp.utils.ResultState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

// TODO 12 Buat class usecase untuk mengatur hasil balikan dari fetch api dengan state

class SurahUseCase @Inject constructor(
    private val repository: SholehRepository
) {
    operator fun invoke(): Flow<ResultState<List<Surah>>> = flow {
        try {
            emit(ResultState.Loading())
            val surah = repository.getAllSurah()
            emit(ResultState.Success(surah))
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