package id.heycoding.sholehapp.persentation.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.heycoding.sholehapp.domain.model.dummy.MainMenu
import id.heycoding.sholehapp.utils.DataDummy
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    fun onMainMenu(): List<MainMenu> = DataDummy.generateDummyOnMainMenu()
}