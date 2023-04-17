package id.heycoding.sholehapp.persentation.sholat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.heycoding.sholehapp.domain.usecase.sholat.JadwalSholatUseCase
import id.heycoding.sholehapp.domain.usecase.sholat.KotaUseCase
import id.heycoding.sholehapp.utils.Constants
import id.heycoding.sholehapp.utils.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SholatViewModel @Inject constructor(
    private val kotaUseCase: KotaUseCase,
    private val jadwalSholatUseCase: JadwalSholatUseCase
) : ViewModel() {
    private val _listJadwalSholatData = MutableStateFlow(SholatState())
    var listJadwalSholatData: StateFlow<SholatState> = _listJadwalSholatData

    fun getAllKota() = viewModelScope.launch(Dispatchers.IO) {
        kotaUseCase(Constants.URL_KOTA_SHOLAT).collect {
            when (it) {
                is ResultState.Success -> {
                    _listJadwalSholatData.value =
                        SholatState(cityPrayerList = it.data ?: emptyList())
                }
                is ResultState.Loading -> {
                    _listJadwalSholatData.value = SholatState(isLoading = true)
                }
                is ResultState.Error -> {
                    _listJadwalSholatData.value =
                        SholatState(error = it.message ?: "An Unexpected Error")
                }
            }
        }
    }

    fun getAllJadwalSholat(idKota: String, tanggal: String) =
        viewModelScope.launch(Dispatchers.IO) {
            jadwalSholatUseCase(
                Constants.URL_JADWAL_SHOLAT + "$idKota/tanggal/$tanggal",
                idKota,
                tanggal
            ).collect {
                when (it) {
                    is ResultState.Success -> {
                        _listJadwalSholatData.value =
                            SholatState(prayerScheduleList = it.data ?: emptyList())
                    }
                    is ResultState.Loading -> {
                        _listJadwalSholatData.value = SholatState(isLoading = true)
                    }
                    is ResultState.Error -> {
                        _listJadwalSholatData.value =
                            SholatState(error = it.message ?: "An Unexpected Error")
                    }
                }
            }
        }
}