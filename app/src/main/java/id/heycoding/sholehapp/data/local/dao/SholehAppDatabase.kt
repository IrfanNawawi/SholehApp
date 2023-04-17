package id.heycoding.sholehapp.data.local.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import id.heycoding.sholehapp.data.local.entity.*

@Database(
    entities = [AyatEntity::class, SurahEntity::class, CityPrayerEntity::class, PrayerScheduleEntity::class, ZakatEntity::class],
    version = 1,
    exportSchema = false
)
abstract class SholehAppDatabase : RoomDatabase() {
    abstract fun sholehDao(): SholehDao
}