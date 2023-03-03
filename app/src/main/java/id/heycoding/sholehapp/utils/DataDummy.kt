package id.heycoding.sholehapp.utils

import id.heycoding.sholehapp.R
import id.heycoding.sholehapp.data.entity.model.MainMenu

object DataDummy {

    fun generateDummyOnMainMenu(): ArrayList<MainMenu> {
        val mainMenu = ArrayList<MainMenu>()

        mainMenu.add(
            MainMenu(
                imageMenu = R.drawable.ic_launcher_background,
                titleMenu = "Al-Qur'an"
            )
        )
        mainMenu.add(
            MainMenu(
                imageMenu = R.drawable.ic_launcher_background,
                titleMenu = "Jadwal Sholat"
            )
        )
        return mainMenu
    }
}