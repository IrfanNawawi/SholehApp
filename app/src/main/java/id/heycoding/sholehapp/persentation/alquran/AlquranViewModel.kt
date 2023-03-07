package id.heycoding.sholehapp.persentation.alquran

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.heycoding.sholehapp.domain.usecase.SurahUseCase
import id.heycoding.sholehapp.utils.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlquranViewModel @Inject constructor(
    private val surahUseCase: SurahUseCase
) : ViewModel() {
    private val listSurahData = MutableStateFlow(AlquranState())
    var _listSurahData: StateFlow<AlquranState> = listSurahData

    fun getAllSurah() = viewModelScope.launch(Dispatchers.IO) {
        surahUseCase().collect {
            when (it) {
                is ResultState.Success -> {
                    listSurahData.value = AlquranState(alquranList = it.data ?: emptyList())
                }
                is ResultState.Loading -> {
                    listSurahData.value = AlquranState(isLoading = true)
                }
                is ResultState.Error -> {
                    listSurahData.value =
                        AlquranState(error = it.message ?: "An Unexpected Error")
                }
            }
        }
    }
}