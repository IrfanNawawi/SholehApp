package id.heycoding.sholehapp.persentation.zakat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.heycoding.sholehapp.domain.model.zakat.Zakat
import id.heycoding.sholehapp.domain.usecase.zakat.ZakatDashboardUseCase
import id.heycoding.sholehapp.domain.usecase.zakat.ZakatInsertUseCase
import id.heycoding.sholehapp.domain.usecase.zakat.ZakatPaymentListUseCase
import id.heycoding.sholehapp.utils.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ZakatViewModel @Inject constructor(
    private val zakatInsertUseCase: ZakatInsertUseCase,
    private val zakatPaymentListUseCase: ZakatPaymentListUseCase,
    private val zakatDashboardUseCase: ZakatDashboardUseCase
) : ViewModel() {
    private val _inputZakatData = MutableStateFlow(ZakatState())
    var inputZakatData: StateFlow<ZakatState> = _inputZakatData

    private val _paymentZakatData = MutableStateFlow(ZakatState())
    var paymentZakatData: StateFlow<ZakatState> = _paymentZakatData

    private val _sumJumlahJiwaData = MutableStateFlow(ZakatState())
    var sumJumlahJiwaData: StateFlow<ZakatState> = _sumJumlahJiwaData

    private val _sumBerasZakatData = MutableStateFlow(ZakatState())
    var sumBerasZakatData: StateFlow<ZakatState> = _sumBerasZakatData

    private val _sumUangZakatData = MutableStateFlow(ZakatState())
    var sumUangZakatData: StateFlow<ZakatState> = _sumUangZakatData

    private val _sumInfakZakatData = MutableStateFlow(ZakatState())
    var sumInfakZakatData: StateFlow<ZakatState> = _sumInfakZakatData

    fun insertPaymentZakat(dataZakat: List<Zakat>) =
        viewModelScope.launch(Dispatchers.IO) {
            zakatInsertUseCase(dataZakat).collect {
                when (it) {
                    is ResultState.Loading -> {
                        _inputZakatData.value = ZakatState(isLoading = true)
                    }
                    is ResultState.Error -> {
                        _inputZakatData.value =
                            ZakatState(error = it.message ?: "An Unexpected Error")
                    }
                    else -> {
                        _inputZakatData.value = ZakatState(zakatPaymentInsert = "Success Bro")
                    }
                }
            }
        }

    fun getAllPaymentZakat() = viewModelScope.launch(Dispatchers.IO) {
        zakatPaymentListUseCase().collect {
            when (it) {
                is ResultState.Success -> {
                    _paymentZakatData.value = ZakatState(zakatPaymentList = it.data ?: emptyList())
                }
                is ResultState.Loading -> {
                    _paymentZakatData.value = ZakatState(isLoading = true)
                }
                is ResultState.Error -> {
                    _paymentZakatData.value =
                        ZakatState(error = it.message ?: "An Unexpected Error")
                }
            }
        }
    }

    fun getSumJumlahJiwa() =
        viewModelScope.launch(Dispatchers.IO) {
            _sumJumlahJiwaData.value = ZakatState(isLoading = true)
            viewModelScope.launch(Dispatchers.IO) {

                try {
                    val sumJumlahJiwa = zakatDashboardUseCase.sumJumlahJiwa()
                    _sumJumlahJiwaData.value =
                        ZakatState(zakatSumJumlahJiwa = sumJumlahJiwa.toString())
                } catch (error: Exception) {
                    _sumJumlahJiwaData.value = ZakatState(error = error.message.toString())
                }
            }
        }

    fun getSumBerasZakat() =
        viewModelScope.launch(Dispatchers.IO) {
            _sumBerasZakatData.value = ZakatState(isLoading = true)
            viewModelScope.launch(Dispatchers.IO) {

                try {
                    val sumBerasZakat = zakatDashboardUseCase.sumBerasZakat()
                    _sumBerasZakatData.value =
                        ZakatState(zakatSumBerasZakat = sumBerasZakat.toString())
                } catch (error: Exception) {
                    _sumBerasZakatData.value = ZakatState(error = error.message.toString())
                }
            }
        }

    fun getSumUangZakat() =
        viewModelScope.launch(Dispatchers.IO) {
            _sumUangZakatData.value = ZakatState(isLoading = true)
            viewModelScope.launch(Dispatchers.IO) {

                try {
                    val sumUangZakat = zakatDashboardUseCase.sumUangZakat()
                    _sumUangZakatData.value =
                        ZakatState(zakatSumUangZakat = sumUangZakat.toString())
                } catch (error: Exception) {
                    _sumUangZakatData.value = ZakatState(error = error.message.toString())
                }
            }
        }

    fun getSumInfakZakat() =
        viewModelScope.launch(Dispatchers.IO) {
            _sumInfakZakatData.value = ZakatState(isLoading = true)
            viewModelScope.launch(Dispatchers.IO) {

                try {
                    val sumInfakZakat = zakatDashboardUseCase.sumInfakZakat()
                    _sumInfakZakatData.value =
                        ZakatState(zakatSumInfakZakat = sumInfakZakat.toString())
                } catch (error: Exception) {
                    _sumInfakZakatData.value = ZakatState(error = error.message.toString())
                }
            }
        }
}