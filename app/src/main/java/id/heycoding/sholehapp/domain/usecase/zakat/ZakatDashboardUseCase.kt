package id.heycoding.sholehapp.domain.usecase.zakat

import id.heycoding.sholehapp.domain.repository.SholehRepository
import javax.inject.Inject

class ZakatDashboardUseCase @Inject constructor(private val repository: SholehRepository) {
    suspend fun sumJumlahJiwa(): Int {
        return repository.getSumJumlahJiwa()
    }

    suspend fun sumBerasZakat(): Double {
        return repository.getSumBerasZakat()
    }

    suspend fun sumUangZakat(): Int {
        return repository.getSumUangZakat()
    }

    suspend fun sumInfakZakat(): Int {
        return repository.getSumInfakZakat()
    }
}