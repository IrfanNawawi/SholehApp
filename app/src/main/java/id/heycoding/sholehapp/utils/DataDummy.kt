package id.heycoding.sholehapp.utils

import id.heycoding.sholehapp.R
import id.heycoding.sholehapp.data.source.entity.model.KajianNew
import id.heycoding.sholehapp.data.source.entity.model.MainMenu

object DataDummy {

    fun generateDummyOnMainMenu(): ArrayList<MainMenu> {
        val mainMenu = ArrayList<MainMenu>()

        mainMenu.add(
            MainMenu(
                idMenu = 1,
                imageMenu = R.drawable.ic_launcher_background,
                titleMenu = "Al-Qur'an"
            )
        )
        mainMenu.add(
            MainMenu(
                idMenu = 2,
                imageMenu = R.drawable.ic_launcher_background,
                titleMenu = "Jadwal Sholat"
            )
        )
        mainMenu.add(
            MainMenu(
                idMenu = 3,
                imageMenu = R.drawable.ic_launcher_background,
                titleMenu = "Arah Kiblat"
            )
        )
        mainMenu.add(
            MainMenu(
                idMenu = 4,
                imageMenu = R.drawable.ic_launcher_background,
                titleMenu = "Jadwal Puasa"
            )
        )
        mainMenu.add(
            MainMenu(
                idMenu = 5,
                imageMenu = R.drawable.ic_launcher_background,
                titleMenu = "Zakat"
            )
        )
        mainMenu.add(
            MainMenu(
                idMenu = 6,
                imageMenu = R.drawable.ic_launcher_background,
                titleMenu = "Qurban"
            )
        )
        return mainMenu
    }

    fun generateDummyOnKajian(): ArrayList<KajianNew> {
        val kajianNew = ArrayList<KajianNew>()

        kajianNew.add(
            KajianNew(
                titleKajian = "One Minute Booster - Ge'er",
                imageKajian = R.drawable.ic_launcher_background,
                linkKajian = "https://www.youtube.com/watch?v=5uiuuOK3Crw&list=PLPVaZBOpYqfD5unBybO5A3zgk43H-Cr-t&index=6&ab_channel=HananAttaki",
                timeKajian = "2 days ago",
                sourceKajian = "Youtube"
            )
        )
        kajianNew.add(
            KajianNew(
                titleKajian = "One Minute Booster - Ge'er",
                imageKajian = R.drawable.ic_launcher_background,
                linkKajian = "https://www.youtube.com/watch?v=5uiuuOK3Crw&list=PLPVaZBOpYqfD5unBybO5A3zgk43H-Cr-t&index=6&ab_channel=HananAttaki",
                timeKajian = "2 days ago",
                sourceKajian = "Youtube"
            )
        )
        kajianNew.add(
            KajianNew(
                titleKajian = "One Minute Booster - Ge'er",
                imageKajian = R.drawable.ic_launcher_background,
                linkKajian = "https://www.youtube.com/watch?v=5uiuuOK3Crw&list=PLPVaZBOpYqfD5unBybO5A3zgk43H-Cr-t&index=6&ab_channel=HananAttaki",
                timeKajian = "2 days ago",
                sourceKajian = "Youtube"
            )
        )
        kajianNew.add(
            KajianNew(
                titleKajian = "One Minute Booster - Ge'er",
                imageKajian = R.drawable.ic_launcher_background,
                linkKajian = "https://www.youtube.com/watch?v=5uiuuOK3Crw&list=PLPVaZBOpYqfD5unBybO5A3zgk43H-Cr-t&index=6&ab_channel=HananAttaki",
                timeKajian = "2 days ago",
                sourceKajian = "Youtube"
            )
        )
        kajianNew.add(
            KajianNew(
                titleKajian = "One Minute Booster - Ge'er",
                imageKajian = R.drawable.ic_launcher_background,
                linkKajian = "https://www.youtube.com/watch?v=5uiuuOK3Crw&list=PLPVaZBOpYqfD5unBybO5A3zgk43H-Cr-t&index=6&ab_channel=HananAttaki",
                timeKajian = "2 days ago",
                sourceKajian = "Youtube"
            )
        )
        return kajianNew
    }
}