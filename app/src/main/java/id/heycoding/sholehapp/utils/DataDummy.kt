package id.heycoding.sholehapp.utils

import id.heycoding.sholehapp.R
import id.heycoding.sholehapp.domain.model.dummy.MainMenu

object DataDummy {

    fun generateDummyOnMainMenu(): ArrayList<MainMenu> {
        val mainMenu = ArrayList<MainMenu>()

        mainMenu.add(
            MainMenu(
                idMenu = 1,
                imageMenu = R.drawable.ic_quran,
                titleMenu = "Al-Qur'an",
                titleMenuEng = "Al-Qur'an"
            )
        )
        mainMenu.add(
            MainMenu(
                idMenu = 2,
                imageMenu = R.drawable.ic_mosque,
                titleMenu = "Jadwal Sholat",
                titleMenuEng = "Pray Schedule"
            )
        )
        mainMenu.add(
            MainMenu(
                idMenu = 3,
                imageMenu = R.drawable.ic_pray,
                titleMenu = "Arah Kiblat",
                titleMenuEng = "Qibla Route"
            )
        )
        mainMenu.add(
            MainMenu(
                idMenu = 4,
                imageMenu = R.drawable.ic_ramadan,
                titleMenu = "Jadwal Puasa",
                titleMenuEng = "Fasting Schedule"
            )
        )
        mainMenu.add(
            MainMenu(
                idMenu = 5,
                imageMenu = R.drawable.ic_zakat,
                titleMenu = "Zakat",
                titleMenuEng = "Zakat"
            )
        )
        mainMenu.add(
            MainMenu(
                idMenu = 6,
                imageMenu = R.drawable.ic_qurban,
                titleMenu = "Qurban",
                titleMenuEng = "Qurban"
            )
        )
        return mainMenu
    }
}