package id.heycoding.sholehapp.domain.usecase.zakat

import id.heycoding.sholehapp.domain.model.zakat.Zakat
import id.heycoding.sholehapp.domain.repository.SholehRepository
import id.heycoding.sholehapp.utils.ResultState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ZakatInsertUseCase @Inject constructor(private val repository: SholehRepository) {
    operator fun invoke(dataZakat: List<Zakat>): Flow<ResultState<List<Zakat>>> = flow {
        try {
            emit(ResultState.Loading())
            repository.insertPaymentZakat(dataZakat)
        } catch (e: HttpException) {
            emit(ResultState.Error(e.localizedMessage ?: " An Unexpected Error Occurred"))
        } catch (e: IOException) {
            emit(ResultState.Error("Error Occurred"))
        }
    }
}